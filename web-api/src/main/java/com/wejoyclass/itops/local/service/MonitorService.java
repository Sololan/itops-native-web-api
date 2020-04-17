package com.wejoyclass.itops.local.service;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.service.service.CURDService;
import com.wejoyclass.itops.local.dto.MonitorCount;
import com.wejoyclass.itops.local.dto.MonitorDetailDto;
import com.wejoyclass.itops.local.dto.MonitorDetailSettingPageDto;
import com.wejoyclass.itops.local.dto.MonitorQueryParam;
import com.wejoyclass.itops.local.entity.*;

import java.util.List;

public interface MonitorService extends CURDService<Monitor, Long> {

    Page<Monitor> findMonitorPage(QueryParameter queryParameter, MonitorQueryParam params);

    List<Monitor> findMonitorList(MonitorQueryParam params);

    List<MonitorType> getMonitorTypeByEquipmentSubTypeId(Long id);

    List<MonitorSubType> getMonitorTypeSubByMonitorTypeId(Long equipmentSubTypeId,Long monitorTypeId);

    List<MonitorSubMethod> getMonitorSubMethodByMonitorTypeSubId(Long id);

    List<MonitorMethodParam> getMonitorMethodParamByMonitorSubMethodId(Long id);

    MonitorDetailDto getMonitorDetailById(Long id);

    List<MonitorCount> getMonitorCountByMonitorType();

    void saveMonitorShowSetting(MonitorShowSetting monitorShowSetting);

    void updateMonitorShowSetting(Long id,MonitorShowSetting monitorShowSetting);

    void deleteMonitorShowSetting(Long id);

    void saveMonitor(MonitorDetailDto monitorDetailDto);

    void updateMonitor(MonitorDetailDto monitorDetailDto);

    void forbidMonitor(Long id);

    void unforbidMonitor(Long id);

    void deleteMonitor(Long id);

    MonitorShowSetting getMonitorShowSettingById(Long id);

    List<MonitorTypeMaster> getMonitorTypeByMonitorId(Long id);

    List<MonitorShowSetting> getMonitorShowSetting(Long id, Long typeId);

    MonitorDetailSettingPageDto getMonitorDetailSettingPageById(Long id);
}
