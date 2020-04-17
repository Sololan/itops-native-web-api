package com.wejoyclass.itops.local.service;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.service.service.CURDService;
import com.wejoyclass.itops.local.dto.EquipmentDto;
import com.wejoyclass.itops.local.dto.EquipmentQueryParam;
import com.wejoyclass.itops.local.entity.Equipment;
import com.wejoyclass.itops.local.entity.Supplier;

import java.util.List;

public interface EquipmentService extends CURDService<Equipment, Long> {

    void isValidCode(String code, Long excludedId);

    void saveEquipment(EquipmentDto newEquipmentDto);

    Equipment getById(Long id);

    void updateEquipment(EquipmentDto newEquipmentDto);

    void deleteById(Long id);

    List<Equipment> findEquipmentList(EquipmentQueryParam request);

    Page<Equipment> findEquipmentPage(QueryParameter queryParameter, EquipmentQueryParam params);
}
