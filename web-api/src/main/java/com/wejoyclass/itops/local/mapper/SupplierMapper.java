package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.dto.SupplierDto;
import com.wejoyclass.itops.local.dto.SupplierQueryParam;
import com.wejoyclass.itops.local.entity.Supplier;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 示例 Mapper
 */
@Mapper
public interface SupplierMapper extends CURDMapper<Supplier, Long> {

    String TABLE_NAME = "t_ops_supplier";

    String INSERT_FIELDS = "ORG_ID, SUPPLIER_NAME, CONTACTS, CONTACT_TEL, EMAIL, " +
            "CREATE_TIME, CREATE_USER_NAME, CREATE_USER";

    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Select({"select id, SUPPLIER_NAME from", TABLE_NAME, "where DELETE_FLAG = 0 and ORG_ID = #{orgId}"})
    List<SupplierDto> findNames(Long orgId);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id = #{id} and DELETE_FLAG = 0"})
    Supplier getById(Long id);

    @Select({"select id from", TABLE_NAME, "where SUPPLIER_NAME = #{name}",
            "and ORG_ID = #{orgId} and ID != #{excludedId} and DELETE_FLAG = 0"})
    Supplier getSupplierByName(String name, Long orgId, Long excludedId);

    List<Supplier> findSupplierList(SupplierQueryParam params);

    Integer canDelete(Long id);
}

