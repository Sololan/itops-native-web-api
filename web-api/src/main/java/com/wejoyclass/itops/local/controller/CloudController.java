package com.wejoyclass.itops.local.controller;

import com.alibaba.fastjson.JSONObject;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.MapperUtil;
import com.wejoyclass.core.util.OauthUtil;
import com.wejoyclass.core.util.RespEntity;
import com.wejoyclass.itops.local.dto.InitDataDto;
import com.wejoyclass.itops.local.service.CloudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/14 15:36
 **/
@RestController
@Api(tags = "数据同步")
@RequestMapping("/cloud")
public class CloudController {

    @Autowired
    private CloudService cloudService;

    @Value("${oauth2.account}")
    private String account;

    @Value("${oauth2.key}")
    private String password;

    @Value("${cloud.url}")
    private String url;
    /*todo 与云平台交互*/

    @ApiOperation("验证授权码是否正确")
    @GetMapping("/nativeAuthorize/{code}")
    RespEntity nativeAuthorize(@PathVariable("code") String code) {
        return CtrlUtil.exe(result -> {

            InitDataDto initDataDto;

            String accessToken = OauthUtil.getClientToken(url, account, password);

            JSONObject pass = OauthUtil.getJSON(url+"/web-cloud/verifyLicenseCode/"+code, new HashMap<>(), accessToken);
//            JSONObject pass = OauthUtil.getJSON("http://localhost:7010/verifyLicenseCode/" + code, new HashMap<>(), accessToken);
            if (pass.get("code").equals(0)) {
                JSONObject initData = OauthUtil.getJSON(url+"/web-cloud/initData/" + code, new HashMap<>(), accessToken);
//                JSONObject initData = OauthUtil.getJSON("http://localhost:7010/initData/" + code, new HashMap<>(), accessToken);
                initDataDto = MapperUtil.converToObject(MapperUtil.convertToJson(initData.get("data")),InitDataDto.class);
                cloudService.initData(initDataDto);
            } else if(pass.get("code").equals(1)) {
                result.setCode(1);
                result.setMessage("授权码已过期");
            } else {
                result.setCode(-1);
                result.setMessage("授权码错误");
            }
        });
    }
}
