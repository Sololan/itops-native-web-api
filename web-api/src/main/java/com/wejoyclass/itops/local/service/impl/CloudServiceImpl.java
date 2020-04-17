package com.wejoyclass.itops.local.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wejoyclass.core.util.DateUtil;
import com.wejoyclass.core.util.MapperUtil;
import com.wejoyclass.core.util.OauthUtil;
import com.wejoyclass.itops.local.dto.ActiveLicenseDto;
import com.wejoyclass.itops.local.dto.InitDataDto;
import com.wejoyclass.itops.local.entity.*;
import com.wejoyclass.itops.local.feign.UcService;
import com.wejoyclass.itops.local.mapper.*;
import com.wejoyclass.itops.local.service.CloudService;
import com.wejoyclass.itops.local.service.MonitorLicenseService;
import com.wejoyclass.itops.local.service.WarningMsgLicenseService;
import com.wejoyclass.uc.entity.DictGroup;
import com.wejoyclass.uc.entity.DictItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/14 16:36
 **/
@Transactional
@Service
@Slf4j
public class CloudServiceImpl implements CloudService {

    @Autowired
    private UcService ucService;
    @Autowired
    private DictGroupMapper dictGroupMapper;
    @Autowired
    private DictItemMapper dictItemMapper;
    @Autowired
    private MonitorLicenseMapper monitorLicenseMapper;
    @Autowired
    private WarningMsgLicenseMapper warningMsgLicenseMapper;
    @Autowired
    private ActiveCodeMapper activeCodeMapper;
    @Autowired
    private EquipmentMonitorTypeMapper equipmentMonitorTypeMapper;
    @Autowired
    private MonitorTypeMasterMapper monitorTypeMasterMapper;
    @Autowired
    private MonitorTypeSubMapper monitorTypeSubMapper;
    @Autowired
    private MonitorSubMethodMapper monitorSubMethodMapper;
    @Autowired
    private MonitorMethodParamMapper monitorMethodParamMapper;
    @Autowired
    private WarningMsgLicenseService warningMsgLicenseService;
    @Autowired
    private MonitorLicenseService monitorLicenseService;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private OrgUserMapper orgUserMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private OrgMapper orgMapper;

    @Value("${oauth2.account}")
    private String account;

    @Value("${oauth2.key}")
    private String password;

    @Value("${cloud.url}")
    private String url;

    @Scheduled(cron = "0 0 2 * * ?")
    public void nativeAuthorize() {
//        Long orgId = activeCodeMapper.findOne().getOrgId();
        /*获取组织id*/
        List<MonitorLicense> monitorLicenses = monitorLicenseMapper.selectAll();
        if (monitorLicenses.size() > 0) {
            Long orgId = monitorLicenses.get(0).getOrgId();
            String accessToken = OauthUtil.getClientToken(url, account, password);
            JSONObject activeLicense = OauthUtil.getJSON(url + "/web-cloud/authorization/" + orgId, new HashMap<>(), accessToken);
//            JSONObject activeLicense = OauthUtil.getJSON("http://localhost:7010/web-cloud/authorization/" + orgId, new HashMap<>(), accessToken);
            ActiveLicenseDto activeLicenseDto = MapperUtil.converToObject(MapperUtil.convertToJson(activeLicense.get("data")), ActiveLicenseDto.class);

            //处理今日更新的数据，并修改当前生效的数据
            if (activeLicenseDto.getMonitorLicense().size() > 0) {
                activeLicenseDto.getMonitorLicense().forEach(x -> monitorLicenseMapper.insert(x));
                Date date = activeLicenseDto.getMonitorLicense().get(0).getStartDate();
                for (MonitorLicense monitorLicense:activeLicenseDto.getMonitorLicense()) {
                    if(date.after( monitorLicense.getStartDate())){
                        date = monitorLicense.getStartDate();
                    }
                }
                MonitorLicense monitorLicense = monitorLicenseMapper.findActiveEntity();
                if(monitorLicense.getExpireTime().after(date)){
                    monitorLicense.setExpireTime(DateUtil.addDays(date, 1));
                    monitorLicenseService.update(monitorLicense);
                }
            }
            if (activeLicenseDto.getWarningMsgLicense().size() > 0) {
                activeLicenseDto.getWarningMsgLicense().forEach(x -> warningMsgLicenseMapper.insert(x));
                Date date = activeLicenseDto.getWarningMsgLicense().get(0).getStartDate();
                for (WarningMsgLicense warningMsgLicense:activeLicenseDto.getWarningMsgLicense()) {
                    if(date.after( warningMsgLicense.getStartDate())){
                        date = warningMsgLicense.getStartDate();
                    }
                }
                WarningMsgLicense warningMsgLicense = warningMsgLicenseMapper.findActiveEntity();
                if(warningMsgLicense.getExpireTime().after(date)){
                    warningMsgLicense.setExpireTime(DateUtil.addDays(date, 1));
                    warningMsgLicenseService.update(warningMsgLicense);
                }
            }
        } else {
            log.info("尚未初始化");
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void verifyLicenseCode() {
//        Long orgId = activeCodeMapper.findOne().getOrgId();
        /*获取组织id*/
        List<MonitorLicense> monitorLicenses = monitorLicenseMapper.selectAll();
        if (monitorLicenses.size() > 0) {
            Long orgId = monitorLicenses.get(0).getOrgId();
            String accessToken = OauthUtil.getClientToken(url, account, password);
            JSONObject data = OauthUtil.postJSON(url + "/web-cloud/connect/" + orgId,
                    MapperUtil.convertToMap(MapperUtil.convertToJson(monitorLicenseMapper.findActiveEntity())), accessToken);
//            JSONObject activeLicense = OauthUtil.postJSON("http://localhost:7011/connect/" + orgId,
//                    MapperUtil.convertToMap(MapperUtil.convertToJson(monitorLicenseMapper.findActiveEntity())), accessToken);
            WarningMsgLicense warningMsgLicense = MapperUtil.converToObject(MapperUtil.convertToJson(data.get("data")), WarningMsgLicense.class);
            warningMsgLicenseService.update(warningMsgLicense);
        } else {
            log.info("尚未初始化");

        }

    }

    public void initData(InitDataDto initDataDto) {

        /*清除数据，防止初始化失败产生脏数据*/
//        truncateData();

        /*声明需要的数据类型*/
        List<UserRole> userRoles = new LinkedList<>();
        Map<String, Long> role = new HashMap<>();
        Long orgId = initDataDto.getOrg().getId();
        List<OrgUser> orgUsers = new LinkedList<>();
        List<DictItem> dictItems = new LinkedList<>();
        List<DictGroup> dictGroups = new LinkedList<>();



        /*分离数据*/
        initDataDto.getRoles().forEach(x -> {
            if ("ROLE_MANAGER".equals(x.getCode())) {
                role.put("ROLE_MANAGER", x.getId());
            } else if ("ROLE_USER".equals(x.getCode())) {
                role.put("ROLE_USER", x.getId());
            }
        });

        initDataDto.getDictGroupDtos().forEach(group -> {
            dictGroups.add(MapperUtil.copy(group, DictGroup.class));
            dictItems.addAll(group.getDictItemList());
        });

        initDataDto.getUser().forEach(user -> {
            user.getRoleList().forEach(roleEntity -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleEntity.getId());
                userRoles.add(userRole);
            });
            OrgUser orgUser = new OrgUser();
            orgUser.setOrgId(orgId);
            orgUser.setUserId(user.getId());
            orgUsers.add(orgUser);
        });

        /*插入数据*/
        /*初始化uc*/
        resourceMapper.initResource();
        orgMapper.insert(initDataDto.getOrg());
        initDataDto.getUser().forEach(x -> userMapper.insert(x));
        initDataDto.getRoles().forEach(x -> roleMapper.insert(x));
        dictGroups.forEach(x -> dictGroupMapper.insert(x));
        dictItems.forEach(x -> dictItemMapper.insert(x));
        userRoleMapper.insertList(userRoles);
        orgUserMapper.insertList(orgUsers);

        List<Resource> resources = resourceMapper.getAllResources();
        resources.forEach(resource -> {
            resourceMapper.initResourceRoles(role.get("ROLE_MANAGER"), resource.getId());
            if (!("auth".equals(resource.getCode()) || "user".equals(resource.getCode())
                    || "equipment".equals(resource.getCode()) || "supplier".equals(resource.getCode())
                    || "setting".equals(resource.getCode()) || "dict".equals(resource.getCode()))) {
                resourceMapper.initResourceRoles(role.get("ROLE_USER"), resource.getId());
            }
        });

        /*初始化bs*/
        initDataDto.getMonitorLicense().forEach(x -> monitorLicenseMapper.insert(x));
        initDataDto.getWarningMsgLicense().forEach(x -> warningMsgLicenseMapper.insert(x));
        initDataDto.getEquipmentMonitorTypes().forEach(x -> equipmentMonitorTypeMapper.insert(x));
        initDataDto.getMonitorTypeMasters().forEach(x -> monitorTypeMasterMapper.insert(x));
        initDataDto.getMonitorTypeSubs().forEach(x -> monitorTypeSubMapper.insert(x));
        initDataDto.getMonitorSubMethods().forEach(x -> monitorSubMethodMapper.insert(x));
        initDataDto.getMonitorMethodParams().forEach(x -> monitorMethodParamMapper.insert(x));
    }

    //清空要同步的表
    private void truncateData() {
        resourceMapper.truncatet_base_org_user();
        resourceMapper.truncatet_base_resource();
        resourceMapper.truncatet_base_role();
        resourceMapper.truncatet_base_role_resource();
        resourceMapper.truncatet_base_user();
        resourceMapper.truncatet_base_user_role();
        resourceMapper.truncatet_base_dict_group();
        resourceMapper.truncatet_base_dict_item();
        resourceMapper.truncatet_base_org();
    }
}
