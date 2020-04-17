package com.wejoyclass.itops.local.mapper;


import com.wejoyclass.itops.local.dto.*;
import com.wejoyclass.itops.local.entity.Warning;
import com.wejoyclass.itops.local.entity.WarningStatistic;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lixu
 * @Date 2020/1/9 15:45
 * @Version 1.0
 */

@Mapper
public interface WarningMapper extends CURDMapper<Warning,Long> {

    String TABLE_NAME = "t_ops_warning";

    //统计今天告警统计信息
    public TodayWarningStatisticDto getWarningStatisticInToday(Long orgId);


    /*报表查看*/

    //查询某时间段内每天的告警总数量
    public List<WarningCountEveryDayDto> getWarningCountEveryDay(WarningParameterDto warningParameterDto);


    //统计某时间段内告警内容数量最多的TopN
    public List<WarningInfoDto> getTopNByWarningInfoCountInPeriodTime(WarningParameterDto warningParameterDto);


    /*告警查看*/

    //按条件对告警信息分页查询
    List<Warning> findWarningList(WarningQueryParam warningQueryParam);

    //根据告警id查询告警详细信息
    public Warning getWarningById(Long warningId);

    @Select({"select * from", TABLE_NAME, "where WARNING_CODE = #{warningCode} and DELETE_FLAG = 0"})
    public Warning getByWarningCode(String warningCode);
}
