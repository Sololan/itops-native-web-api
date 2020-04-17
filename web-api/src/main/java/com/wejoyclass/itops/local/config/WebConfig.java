package com.wejoyclass.itops.local.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wejoyclass.core.util.DefaultLifecycle;
import com.wejoyclass.core.util.OauthUtil;
import com.wejoyclass.core.util.RedisUtil;
import com.wejoyclass.itops.local.entity.Monitor;
import com.wejoyclass.itops.local.entity.Warning;
import com.wejoyclass.itops.local.mapper.MonitorMapper;
import com.wejoyclass.itops.local.mapper.OrgMapper;
import com.wejoyclass.itops.local.service.WarningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import com.wejoyclass.uc.entity.Org;

import java.util.List;

@Slf4j
@Configuration
public class WebConfig {

    @Autowired
    WarningService warningService;

    @Autowired
    OrgMapper orgMapper;

    @Autowired
    MonitorMapper monitorMapper;

    @Value("${cloud.url}")
    private String cloudHost;

    @Value("${oauth2.account}")
    private String account;

    @Value("${oauth2.key}")
    private String password;

    /**
     * 定义Spring容器加载完成后执行的任务
     */
    @Bean
    public DefaultLifecycle defaultLifecycle() {
        return  new DefaultLifecycle(this::subscribe);
    }

    private void subscribe() {

        String warningKey = "warning";
        RedisUtil.subscribe((Message message, byte[] pattern) -> {
            log.info("接收到新的告警---"+message.toString());
            String warningJSON = new String(message.getBody());
            Warning warning = JSON.parseObject(warningJSON, Warning.class);

            String accessToken = OauthUtil.getClientToken(cloudHost, account, password);

            if (warning.getWarningStatus().equals("0")) {   //告警未关闭, 添加告警
                Monitor monitor = monitorMapper.getByMonitorName(warning.getHostName());
                if (monitor == null) {
                    throw new IllegalStateException("找不到产生告警的监控");
                }
                warning.setEquipmentId(monitor.getEquipmentId());
                warning.setEquipmentCode(monitor.getEquipmentCode());
                warning.setMonitorId(monitor.getId());
                warning.setMonitorName(monitor.getMonitorName());
                List<Org> orgList = orgMapper.getOrgList(); //todo get current orgId
                if(orgList.size() > 0){
                    warning.setOrgId(orgList.get(0).getId());
                } else {
                    throw new IllegalStateException("组织未初始化");
                }

                warningService.saveWarning(warning);
                JSONObject response = OauthUtil.postJSON(cloudHost+"/web-cloud/warning/syncAdd", (JSONObject) JSONObject.toJSON(warning), accessToken);
                if ((Integer) response.get("code") != 0) {
                    throw new IllegalStateException("同步添加告警信息失败: " + response.get("message"));
                }
            } else if (warning.getWarningStatus().equals("1")) {    //告警已关闭. 修改告警
                warningService.updateWarningByCode(warning);
                JSONObject response = OauthUtil.postJSON(cloudHost+"/web-cloud/warning/syncUpdate", (JSONObject) JSONObject.toJSON(warning), accessToken);
                if ((Integer) response.get("code") != 0) {
                    throw new IllegalStateException("同步修改告警信息失败: " + response.get("message"));
                }
            }

        }, warningKey);
    }
}
