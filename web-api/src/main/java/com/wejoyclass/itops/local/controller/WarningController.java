package com.wejoyclass.itops.local.controller;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.params.Request;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import com.wejoyclass.itops.local.dto.*;
import com.wejoyclass.itops.local.entity.Warning;
import com.wejoyclass.itops.local.entity.WarningStatistic;
import com.wejoyclass.itops.local.service.WarningService;
import com.wejoyclass.service.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author lixu
 * @Date 2020/1/9 19:06
 * @Version 1.0
 */

@RestController
@Api(tags = "告警信息管理")
@RequestMapping("/warning")
public class WarningController {

    @Autowired
    private WarningService warningService;

    @ApiOperation("按告警级别统计今天告警数量，和今天关闭的告警数")
    @GetMapping("/count/today")
    public  RespEntity<TodayWarningStatisticDto> getWarningStatisticInToday(){
        return CtrlUtil.exe(result -> result.setVal(warningService.getWarningStatisticInToday(SecurityUtil.getOrgId())));
    }


    /*报表查看*/

    @ApiOperation("查询某时间段内每天的告警总数量")
    @PostMapping("/count/eachDay")
    public RespEntity<List<WarningCountEveryDayDto>> getWarningStatisticSInPeriodTime(@RequestBody WarningParameterDto warningParameterDto){
        warningParameterDto.setOrgId(SecurityUtil.getOrgId());
        return CtrlUtil.exe(result -> result.setVal(warningService.getWarningCountEveryDay(warningParameterDto)));
    }


    @ApiOperation("统计某时间段内告警内容数量最多的TopN ")
    @PostMapping("/info/top")
    public RespEntity<List<WarningInfoDto>> getTopNByWarningInfoCountInPeriodTime(@RequestBody WarningParameterDto warningParameterDto){
        warningParameterDto.setOrgId(SecurityUtil.getOrgId());
        return CtrlUtil.exe(result -> result.setVal(warningService.getTopNByWarningInfoCountInPeriodTime(warningParameterDto)));
    }


    /*告警查看*/

    @ApiOperation("根据告警id查询告警详细信息")
    @GetMapping("warnings/{warningId}")
    public RespEntity<Warning> getWarningById(@PathVariable Long warningId){
        return CtrlUtil.exe(result -> result.setVal(warningService.getWarningById(warningId)));
    }

    @ApiOperation("告警信息分页查询")
    @PostMapping("/page")
    public RespEntity<Page<Warning>> findPages(@RequestBody Request<WarningQueryParam> request){
        request.getQuery().setOrgId(SecurityUtil.getOrgId());
        return CtrlUtil.exe(result -> result.setVal(warningService.findWarningPage(request.getQueryParameter(), request.getQuery())) );
    }
}
