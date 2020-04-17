package com.wejoyclass.itops.local.controller;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.params.Request;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.IdentifyUtil;
import com.wejoyclass.core.util.RespEntity;
import com.wejoyclass.itops.local.dto.MonitorLicenseDto;
import com.wejoyclass.itops.local.dto.MonitorWarningQueryParam;
import com.wejoyclass.itops.local.service.MonitorLicenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  监控平台授权 Controller
 * @Author shixs
 * @Date 2020/02/19
 **/
@RestController
@Api(tags = "监控授权相关接口")
public class MonitorLicenseController {

    @Autowired
    private MonitorLicenseService monitorLicenseService;

    @ApiOperation("监控授权分页信息")
    @PostMapping("/monitorLicenses/page")
    public RespEntity<Page<MonitorLicenseDto>> getMonitorLicensePage(@RequestBody Request<MonitorWarningQueryParam> request) {
        return CtrlUtil.exe(result -> result.setVal(monitorLicenseService.findMonitorLicesePage(QueryParameter.getInstance(request), request.getQuery())));
    }

}
