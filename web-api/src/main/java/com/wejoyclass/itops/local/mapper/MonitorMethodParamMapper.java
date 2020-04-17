package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.MonitorMethodParam;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorMethodParamMapper extends CURDMapper<MonitorMethodParam, Long> {
    List<MonitorMethodParam> getMonitorMethodParamByMonitorSubMethodId(Long id);
}
