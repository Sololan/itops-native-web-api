package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.EquipmentMonitorType;
import com.wejoyclass.itops.local.entity.MonitorSubMethod;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorSubMethodMapper extends CURDMapper<MonitorSubMethod, Long> {
    List<MonitorSubMethod> getMonitorSubMethodByMonitorTypeSubId(Long id);
    List<MonitorSubMethod> findAll();
}
