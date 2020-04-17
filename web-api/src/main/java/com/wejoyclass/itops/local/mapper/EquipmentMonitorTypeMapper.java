package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.EquipmentMonitorType;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 20:54 2020/2/18
 * @Modified By:
 **/
@Mapper
public interface EquipmentMonitorTypeMapper extends CURDMapper<EquipmentMonitorType, Long> {

    List<EquipmentMonitorType> findAll();
}
