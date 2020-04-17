package com.wejoyclass.itops.local.controller;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.params.Request;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import com.wejoyclass.itops.local.dto.EquipmentDto;
import com.wejoyclass.itops.local.dto.EquipmentQueryParam;
import com.wejoyclass.itops.local.entity.Equipment;
import com.wejoyclass.itops.local.service.EquipmentService;
import com.wejoyclass.service.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@Api(tags = "设备管理")
@RestController
@RequestMapping("/equipments")
@Validated
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    @ApiOperation("分页条件查询设备")
    @PostMapping("/page")   //todo return dto
    public RespEntity<Page<Equipment>> queryEquipments(@RequestBody @Valid Request<EquipmentQueryParam> request){
        Page<Equipment> equipmentPage = equipmentService.findEquipmentPage(QueryParameter.getInstance(request), request.getQuery());
        return CtrlUtil.exe(result -> result.setVal(equipmentPage));
    }


    @ApiOperation("设备编号验重")
    @GetMapping("/code")
    public RespEntity isValidCode(@RequestParam @NotBlank String equipmentCode,
                                  @RequestParam(defaultValue = "0") Long excludedId) {
        return CtrlUtil.exe(result -> {
            equipmentService.isValidCode(equipmentCode, excludedId);
        });
    }

    @ApiOperation("添加设备")
    @PostMapping
    public RespEntity saveEquipment(@RequestBody @Valid EquipmentDto equipmentDto){
        return CtrlUtil.exe(result -> {
            equipmentDto.setOrgId(SecurityUtil.getTopOrgId());
            equipmentService.saveEquipment(equipmentDto);
        });
    }

    @ApiOperation("根据id查询设备(未删除)")
    @GetMapping("/{id}")
    public RespEntity<Equipment> getEquipmentById(@PathVariable @NotNull Long id){
        return CtrlUtil.exe(result -> result.setVal(equipmentService.getById(id)));
    }

    @ApiOperation("修改设备")
    @PostMapping("/{id}")
    public RespEntity updateEquipment(@PathVariable @NotNull Long id, @RequestBody @Valid EquipmentDto equipmentDto){
        equipmentDto.setId(id);
        return CtrlUtil.exe(result -> {
            equipmentService.updateEquipment(equipmentDto);
        });
    }

    @ApiOperation("根据id删除设备")
    @PostMapping("/{id}/delete")
    public RespEntity deleteEquipmentById(@PathVariable @NotNull Long id){
        return CtrlUtil.exe(result -> {
            equipmentService.deleteById(id);
        });
    }
}
