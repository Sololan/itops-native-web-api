package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.dto.MonitorDetailTypeDto;
import com.wejoyclass.itops.local.entity.MonitorDetailType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorDetailTypeMapper {
    MonitorDetailType getById(Long id);

    MonitorDetailTypeDto getMonitorDetailTypeDtoById(Long id);

    void saveMonitorDetailType(MonitorDetailType monitorDetailType);

    List<Long> getIdListByMonitorId(Long id);

    List<String> getTemplateIdListByMonitorId(Long id);

    void updateMonitorDetailType(MonitorDetailType monitorDetailType);

    void deleteById(List<Long> list,Long userId,String username);

    void delelteByMonitorId(Long id);
}
