package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.dto.MonitorCount;
import com.wejoyclass.itops.local.entity.MonitorType;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorTypeMapper extends CURDMapper<MonitorType, Long> {
    List<MonitorType> getMonitorTypeByEquipmentSubTypeId(Long id);
    List<MonitorCount> getMonitorTypeCount();
    List<MonitorCount> getMonitorTypeCountById(Long id);
}
