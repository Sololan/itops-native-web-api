package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.MonitorSubType;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorSubTypeMapper extends CURDMapper<MonitorSubType, Long> {
    List<MonitorSubType> getMonitorTypeSubByMonitorTypeId(Long equipmentSubTypeId,Long monitorTypeId);
}
