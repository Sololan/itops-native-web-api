package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.dto.MonitorDetailDto;
import com.wejoyclass.itops.local.dto.MonitorDetailSettingPageDto;
import com.wejoyclass.itops.local.dto.MonitorQueryParam;
import com.wejoyclass.itops.local.entity.Monitor;
import com.wejoyclass.itops.local.entity.MonitorTypeMaster;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MonitorMapper extends CURDMapper<Monitor,Long> {

    String TABLE_NAME = "t_ops_monitor";

    List<Monitor> findMonitorList(MonitorQueryParam params);

    MonitorDetailDto getMonitorDetailById(Long id);

    void saveMonitor(Monitor monitor);

    void updateMonitor(Monitor monitor);

    void forbidMonitor(Long id);

    void unforbidMonitor(Long id);

    void deleteMonitor(Monitor monitor);

    List<MonitorTypeMaster> getMonitorTypeByMonitorId(Long id);

    MonitorDetailSettingPageDto getMonitorDetailSettingPageById(Long id);

    //    @Select({"select * from", TABLE_NAME, "where MONITOR_NAME = #{monitorName} and DELETE_FLAG = 0"})
    Monitor getByMonitorName(String monitorName);

    @Select({"select * from", TABLE_NAME, "where ID = #{id} and DELETE_FLAG = 0"})
    Monitor getById(Long id);

    @Update({"update ", TABLE_NAME, " set DELETE_FLAG = #{id} where ID = #{id}"})
    void deleteById(Long id);

    @Select({"select count(id) from", TABLE_NAME, "where EQUIPMENT_ID = #{equipmentId} and DELETE_FLAG = 0"})
    Integer countMonitorByEquipmentId(Long equipmentId);
}
