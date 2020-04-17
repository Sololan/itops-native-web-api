package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.MonitorShowData;
import com.wejoyclass.itops.local.entity.MonitorShowSetting;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorShowDataMapper extends CURDMapper<MonitorShowData,Long> {
    void delelteByMonitorId(Long id);

    List<MonitorShowData> getMonitorShowDateBySettingId(Long id);

    void saveMonitorShowData(MonitorShowSetting monitorShowSetting);

    void deleteByShowSettingId(Long id);

    void updateMonitorId(Long oldId,Long newId);
}
