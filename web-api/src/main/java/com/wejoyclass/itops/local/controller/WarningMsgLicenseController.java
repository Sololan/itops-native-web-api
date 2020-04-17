package com.wejoyclass.itops.local.controller;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.params.Request;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import com.wejoyclass.itops.local.dto.MonitorWarningQueryParam;
import com.wejoyclass.itops.local.dto.WarningMsgLicenseDto;
import com.wejoyclass.itops.local.service.WarningMsgLicenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 告警消息平台授权 Controller
 * @Author shixs
 * @Date 2020/02/19
 **/
@RestController
@Api(tags = "通知授权相关接口")
@RequestMapping("/warningMsgLicense")
public class WarningMsgLicenseController {

    @Autowired
    private WarningMsgLicenseService warningMsgLicenseService;

    @ApiOperation("监控授权分页信息")
    @PostMapping("/warningMsgLicense/page")
    public RespEntity<Page<WarningMsgLicenseDto>> getWarningMsgLicensePage(@RequestBody Request<MonitorWarningQueryParam> request) {
        return CtrlUtil.exe(result -> result.setVal(warningMsgLicenseService.findWarningMsgPage(QueryParameter.getInstance(request), request.getQuery())));
    }

}
