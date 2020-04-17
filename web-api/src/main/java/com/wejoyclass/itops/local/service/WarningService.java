package com.wejoyclass.itops.local.service;


import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.service.service.CURDService;
import com.wejoyclass.itops.local.dto.*;
import com.wejoyclass.itops.local.entity.Warning;
import com.wejoyclass.itops.local.entity.WarningStatistic;

import java.util.List;

/**
 * @Author lixu
 * @Date 2020/1/9 18:59
 * @Version 1.0
 */

public interface WarningService extends CURDService<Warning, Long> {


    //按告警级别统计今天告警数量
    public TodayWarningStatisticDto getWarningStatisticInToday(Long orgId);


    /*报表查看*/

    //查询某时间段内每天的告警总数量
    public List<WarningCountEveryDayDto> getWarningCountEveryDay(WarningParameterDto warningParameterDto);

    //统计某时间段内告警内容数量最多的TopN
    public List<WarningInfoDto> getTopNByWarningInfoCountInPeriodTime(WarningParameterDto warningParameterDto);

    /*告警查看*/

    //按条件对告警信息分页查询
    public Page<Warning> findWarningPage(QueryParameter queryParameter, WarningQueryParam warningQueryParam);

    //根据告警id查询告警详细信息
    public Warning getWarningById(Long warningId);

    public void saveWarning(Warning warning);

    void updateWarningByCode(Warning newWarning);
}
