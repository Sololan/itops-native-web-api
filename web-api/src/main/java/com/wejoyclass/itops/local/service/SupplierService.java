package com.wejoyclass.itops.local.service;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import com.wejoyclass.core.service.service.CURDService;
import com.wejoyclass.itops.local.dto.SupplierQueryParam;
import com.wejoyclass.itops.local.dto.SupplierDto;
import com.wejoyclass.itops.local.entity.Supplier;

import java.util.List;

public interface SupplierService extends CURDService<Supplier, Long>
{

    List<SupplierDto> getNames();

    void isValidName(String name, Long excludedId);

    void saveSupplier(SupplierDto newSupplierDto);

    SupplierDto getById(Long id);

    void updateSupplier(SupplierDto newSupplierDto);

    void deleteById(Long id);

    List<Supplier> findSupplierList(SupplierQueryParam request);

    Page<Supplier> findSupplierPage(QueryParameter queryParameter, SupplierQueryParam params);
}
