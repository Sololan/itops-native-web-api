package com.wejoyclass.itops.local.controller;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.params.Request;
import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import com.wejoyclass.itops.local.dto.SupplierQueryParam;
import com.wejoyclass.itops.local.dto.SupplierDto;
import com.wejoyclass.itops.local.entity.Supplier;
import com.wejoyclass.itops.local.service.SupplierService;
import com.wejoyclass.itops.local.service.impl.SupplierServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Api(tags = "供应商管理")
@RestController
@RequestMapping("/suppliers")
@Validated
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @ApiOperation("获取供应商名称列表")
    @GetMapping("/names")
    public RespEntity<List<SupplierDto>> getSupplierNames(){
        return CtrlUtil.exe(r -> r.setVal(supplierService.getNames()));
    }

    @ApiOperation("分页条件查询供应商")
    @PostMapping("/page")
    public RespEntity<Page<Supplier>> querySuppliers(@RequestBody @Valid Request<SupplierQueryParam> request){
        Page<Supplier> supplierPage = supplierService.findSupplierPage(QueryParameter.getInstance(request), request.getQuery());
        return  CtrlUtil.exe(r -> r.setVal(supplierPage));
    }

    @ApiOperation("供应商名称验重")
    @GetMapping("/name")
    public RespEntity isValidName(@RequestParam @NotBlank String supplierName,
                                  @RequestParam(defaultValue = "0") Long excludedId) {
        return CtrlUtil.exe(result -> {
            supplierService.isValidName(supplierName, excludedId);
        });
    }

    @ApiOperation("添加供应商")
    @PostMapping
    public RespEntity saveSupplier(@RequestBody @Valid SupplierDto supplierDto){
        return  CtrlUtil.exe(result -> {
            supplierService.saveSupplier(supplierDto);
        });
    }

    @ApiOperation("根据id查询供应商(未删除)")
    @GetMapping("/{id}")
    public RespEntity<SupplierDto> getSupplierById(@PathVariable @NotNull Long id){
        return  CtrlUtil.exe(r -> r.setVal(supplierService.getById(id)));
    }

    @ApiOperation("修改供应商")
    @PostMapping("/{id}")
    public RespEntity updateSupplier(@PathVariable @NotNull Long id, @RequestBody @Valid SupplierDto supplierDto){
        supplierDto.setId(id);
        return  CtrlUtil.exe(result -> {
            supplierService.updateSupplier(supplierDto);
        });
    }

    @ApiOperation("根据id删除供应商")
    @PostMapping("/{id}/delete")
    public RespEntity deleteSupplierById(@PathVariable @NotNull Long id){
        return  CtrlUtil.exe(result -> {
            supplierService.deleteById(id);
        });
    }
}
