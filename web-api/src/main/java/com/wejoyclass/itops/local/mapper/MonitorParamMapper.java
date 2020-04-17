package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.dto.MonitorParamDto;
import com.wejoyclass.itops.local.entity.MonitorParam;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorParamMapper extends CURDMapper<MonitorParam, Long> {
    MonitorParamDto getMonitorParamDtoById(Long id);

    void saveMonitorParam(List<MonitorParam> monitorParamList);

    void deleteByMonitorDetailTypeId(List<Long> list,Long userId,String username);

    void updateMonitorParam(Long id,String paramValue);

    void delelteByMonitorId(Long id);
}
