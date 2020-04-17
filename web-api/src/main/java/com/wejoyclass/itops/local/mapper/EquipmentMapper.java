package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.dto.EquipmentQueryParam;
import com.wejoyclass.itops.local.entity.Equipment;

import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EquipmentMapper extends CURDMapper<Equipment, Long> {

    String TABLE_NAME = "T_OPS_EQUIPMENT";

    String INSERT_FIELDS = "ORG_ID, EQUIPMENT_CODE, EQUIPMENT_TYPE, EQUIPMENT_SUB_TYPE, BRAND, EQUIPMENT_MODE, SERVICE_NUMBER, PURCHASE_DATE, EXPIRATION, SUPPLIER_ID, " +
            "CREATE_TIME, CREATE_USER_NAME, CREATE_USER";

    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Select({"select id from ", TABLE_NAME, "where EQUIPMENT_CODE = #{equipmentCode}",
            "and ORG_ID = #{orgId} and ID != #{excludedId} and DELETE_FLAG = 0"})
    Equipment getEquipmentByCode(String equipmentCode, Long orgId, Long excludedId);

    Equipment getById(Long id);

    List<Equipment> findEquipmentList(EquipmentQueryParam params);
}
