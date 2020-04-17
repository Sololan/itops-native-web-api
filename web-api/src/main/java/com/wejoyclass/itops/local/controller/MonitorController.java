package com.wejoyclass.itops.local.controller;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.params.Request;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import com.wejoyclass.itops.local.dto.MonitorCount;
import com.wejoyclass.itops.local.dto.MonitorDetailDto;
import com.wejoyclass.itops.local.dto.MonitorDetailSettingPageDto;
import com.wejoyclass.itops.local.dto.MonitorQueryParam;
import com.wejoyclass.itops.local.entity.*;
import com.wejoyclass.itops.local.service.MonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "监控管理")
@RestController
@RequestMapping("/monitors")
public class MonitorController {

    @Autowired
    MonitorService monitorService;

    @ApiOperation("分页条件查询监控")
    @PostMapping("/page")
    public RespEntity<Page<Monitor>> queryMonitor(@RequestBody @Valid Request<MonitorQueryParam> request){
        Page<Monitor> monitorPage = monitorService.findMonitorPage(QueryParameter.getInstance(request), request.getQuery());
        return  CtrlUtil.exe(r -> r.setVal(monitorPage));
    }

    @ApiOperation("根据设备子类型查询监控类型")
    @GetMapping("/equipmentsSubType/{id}/monitorTypes")
    public RespEntity<List<MonitorType>> getMonitorTypeByEquipmentSubTypeId(@PathVariable Long id){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorTypeByEquipmentSubTypeId(id)));
    }

    @ApiOperation("根据设备子类型和监控类型id获取监控子类型")
    @GetMapping("/equipmentsSubType/{equipmentSubTypeId}/monitorTypes/{monitorTypeId}/monitorTypeSubs")
    public RespEntity<List<MonitorSubType>> getMonitorTypeSubByMonitorTypeId(@PathVariable Long equipmentSubTypeId,@PathVariable Long monitorTypeId){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorTypeSubByMonitorTypeId(equipmentSubTypeId,monitorTypeId)));
    }

    @ApiOperation("根据监控子类型id获取监控方式")
    @GetMapping("/monitorTypeSubs/{id}/monitorSubMethods")
    public RespEntity<List<MonitorSubMethod>> getMonitorSubMethodByMonitorTypeSubId(@PathVariable Long id){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorSubMethodByMonitorTypeSubId(id)));
    }

    @ApiOperation("根据监控方式id获取监控方式参数")
    @GetMapping("/monitorSubMethods/{id}/monitorMethodParam")
    public RespEntity<List<MonitorMethodParam>> getMonitorMethodParamByMonitorSubMethodId(@PathVariable Long id){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorMethodParamByMonitorSubMethodId(id)));
    }

    @ApiOperation("根据监控信息id获取监控详细参数")
    @GetMapping("/{id}")
    public RespEntity<MonitorDetailDto> getMonitorDetailById(@PathVariable Long id){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorDetailById(id)));
    }

    @ApiOperation("保存监控")
    @PostMapping()
    public RespEntity saveMonitor(@RequestBody MonitorDetailDto monitorDetailDto){
        return  CtrlUtil.exe(r -> {
            monitorService.saveMonitor(monitorDetailDto);
        });
    }

    @ApiOperation("修改监控")
    @PostMapping("/{id}")
    public RespEntity updateMonitor(@PathVariable("id") Long id,@RequestBody MonitorDetailDto monitorDetailDto){
        return  CtrlUtil.exe(r -> {
            monitorDetailDto.setId(id);
            monitorService.updateMonitor(monitorDetailDto);
        });
    }

    @ApiOperation("禁用监控")
    @PostMapping("/{id}/forbid")
    public RespEntity forbidMonitor(@PathVariable("id") Long id){
        return  CtrlUtil.exe(r -> {
            monitorService.forbidMonitor(id);
        });
    }

    @ApiOperation("启用监控")
    @PostMapping("/{id}/unforbid")
    public RespEntity unforbidMonitor(@PathVariable("id") Long id){
        return  CtrlUtil.exe(r -> {
            monitorService.unforbidMonitor(id);
        });
    }

    @ApiOperation("删除监控")
    @PostMapping("/{id}/delete")
    public RespEntity deleteMonitor(@PathVariable("id") Long id){
        return  CtrlUtil.exe(r -> {
            monitorService.deleteMonitor(id);
        });
    }

    @ApiOperation("首页按类型获取监控对象数量")
    @GetMapping("/monitorType")
    public RespEntity<List<MonitorCount>> getMonitorCountByMonitorType(){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorCountByMonitorType()));
    }

    @ApiOperation("根据id获取监控数据展现设置")
    @GetMapping("/monitorShowSetting/{id}")
    public RespEntity<MonitorShowSetting> getMonitorShowSettingById(@PathVariable("id") Long id){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorShowSettingById(id)));
    }

    @ApiOperation("保存监控数据展现设置")
    @PostMapping("/monitorShowSetting")
    public RespEntity saveMonitorShowSetting(@RequestBody MonitorShowSetting monitorShowSetting){
        return  CtrlUtil.exe(r -> {
            monitorService.saveMonitorShowSetting(monitorShowSetting);
        });
    }

    @ApiOperation("更新监控数据展现设置")
    @PostMapping("/monitorShowSetting/{id}")
    public RespEntity updateMonitorShowSetting(@PathVariable("id") Long id,@RequestBody MonitorShowSetting monitorShowSetting){
        return  CtrlUtil.exe(r -> {
            monitorService.updateMonitorShowSetting(id,monitorShowSetting);
        });
    }

    @ApiOperation("删除监控数据展现设置")
    @PostMapping("/monitorShowSetting/{id}/delete")
    public RespEntity updateMonitorShowSetting(@PathVariable("id") Long id){
        return  CtrlUtil.exe(r -> {
            monitorService.deleteMonitorShowSetting(id);
        });
    }

    @ApiOperation("根据监控信息id获取监控类型")
    @GetMapping("/monitorType/monitor/{id}")
    public RespEntity<List<MonitorTypeMaster>> getMonitorTypeByMonitorId(@PathVariable("id") Long id){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorTypeByMonitorId(id)));
    }

    @ApiOperation("根据监控信息id和监控类型id获取监控数据展现")
    @GetMapping("/monitorShowSetting/monitor/{id}/monitorType/{typeId}")
    public RespEntity<List<MonitorShowSetting>> getMonitorShowSetting(@PathVariable("id") Long id,@PathVariable("typeId") Long typeId){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorShowSetting(id,typeId)));
    }

    @ApiOperation("根据监控信息id获取监控详细设定信息")
    @GetMapping("/monitorShowSettingPage/{id}")
    public RespEntity<MonitorDetailSettingPageDto> getMonitorShowSetting(@PathVariable("id") Long id){
        return  CtrlUtil.exe(r -> r.setVal(monitorService.getMonitorDetailSettingPageById(id)));
    }
}
