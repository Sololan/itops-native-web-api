package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.MonitorShowSetting;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorShowSettingMapper extends CURDMapper<MonitorShowSetting,Long> {
    void saveMonitorShowSetting(MonitorShowSetting monitorShowSetting);

    void delelteByMonitorId(Long id);

    List<MonitorShowSetting> getMonitorShowSetting(Long id, Long typeId);

    MonitorShowSetting getMonitorShowSettingById(Long id);

    void deleteById(Long id);

    void updateMonitorId(Long oldId,Long newId);

    void updateById(Long id,MonitorShowSetting monitorShowSetting);
}
