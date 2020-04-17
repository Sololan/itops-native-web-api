package com.wejoyclass.itops.local.service.impl;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.util.ExceptionUtil;
import com.wejoyclass.core.util.MapperUtil;
import com.wejoyclass.core.util.PageUtil;
import com.wejoyclass.core.util.StringUtil;
import com.wejoyclass.itops.local.dto.SupplierQueryParam;
import com.wejoyclass.itops.local.dto.SupplierDto;
import com.wejoyclass.itops.local.entity.Supplier;
import com.wejoyclass.itops.local.mapper.SupplierMapper;
import com.wejoyclass.itops.local.service.SupplierService;
import com.wejoyclass.service.impl.BaseCURDServiceImpl;
import com.wejoyclass.service.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SupplierServiceImpl extends BaseCURDServiceImpl<Supplier, Long> implements SupplierService {

    @Autowired
    SupplierMapper supplierMapper;

    @Override
    public List<SupplierDto> getNames() {
        Long orgId = SecurityUtil.getOrgId();
        return supplierMapper.findNames(orgId);
    }

    @Override
    public void isValidName(String supplierName, Long excludedId) {
        if (StringUtils.hasText(supplierName)) {
            Long orgId = SecurityUtil.getTopOrgId();
            Supplier foundSupplier = supplierMapper.getSupplierByName(supplierName, orgId, excludedId);
            if (foundSupplier != null) {
                throw ExceptionUtil.msg("供应商名称已经存在！");
            }
        }
    }

    @Override
    public void saveSupplier(SupplierDto newSupplierDto) {
        Supplier newSupplier = this.convertToModel(newSupplierDto);

        this.isValidName(newSupplier.getSupplierName(), 0L);

        try{
            this.insert(newSupplier);
        }catch (Exception ex){
            String errMsg = "添加供应商失败！";
            throw ExceptionUtil.unchecked(errMsg, ex);
        }

    }

    @Override
    public SupplierDto getById(Long id){
        return this.convertToDto(supplierMapper.getById(id));
    }

    @Override
    public void updateSupplier(SupplierDto newSupplierDto) {
        Supplier newSupplier = this.convertToModel(newSupplierDto);

        Supplier exitingSupplier = this.get(newSupplier.getId());
        if(exitingSupplier == null){
            throw ExceptionUtil.msg(String.format("id为[%s]的供应商没有找到！", newSupplier.getId()));
        }

        if(StringUtils.hasText(newSupplier.getSupplierName()) && !newSupplier.getSupplierName().equals(exitingSupplier.getSupplierName())){
            Long orgId = SecurityUtil.getTopOrgId();
            Supplier foundSupplier = supplierMapper.getSupplierByName(newSupplier.getSupplierName(), orgId, 0L);
            if (foundSupplier != null) {
                throw ExceptionUtil.msg("供应商名称已经存在！");
            }
        }

        MapperUtil.copy(newSupplier, exitingSupplier);

        try {
            this.update(exitingSupplier);
        } catch (Exception ex) {
            String errMsg = "修改供应商失败！";
            throw ExceptionUtil.unchecked(errMsg, ex);
        }

    }


    @Override
    public void deleteById(Long id) {
        Supplier exitingSupplier = this.get(id);
        Integer integer = supplierMapper.canDelete(id);
        if(integer > 0){
            throw ExceptionUtil.msg("该供应商已绑定至少一个设备，无法删除");
        }
        if(exitingSupplier == null){
            throw ExceptionUtil.msg(String.format("id为[%s]的供应商没有找到！", id));
        }

        try{
          this.deleteLogic(exitingSupplier);
        } catch (Exception ex){
            String errMsg = "删除供应商失败！";
            throw ExceptionUtil.unchecked(errMsg, ex);
        }

    }

    @Override
    public List<Supplier> findSupplierList(SupplierQueryParam params) {
        StringUtil.encodeSqlLike(params, "supplierName", "contacts");
        params.setOrgId(SecurityUtil.getTopOrgId());
        return supplierMapper.findSupplierList(params);
    }

    @Override
    public Page<Supplier> findSupplierPage(QueryParameter queryParameter, SupplierQueryParam params) {
        return PageUtil.process(queryParameter, () -> {
            return this.findSupplierList(params);
        });
    }

    private SupplierDto convertToDto(Supplier supplier) {
        return MapperUtil.map(supplier, SupplierDto.class);
    }

    private Supplier convertToModel(SupplierDto supplierDto) {
        return MapperUtil.map(supplierDto, Supplier.class);
    }



}
