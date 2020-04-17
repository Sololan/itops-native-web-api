package com.wejoyclass.itops.local.service.impl;



import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.util.MapperUtil;
import com.wejoyclass.core.util.PageUtil;
import com.wejoyclass.core.util.StringUtil;
import com.wejoyclass.itops.local.dto.*;
import com.wejoyclass.itops.local.entity.Warning;
import com.wejoyclass.itops.local.entity.WarningStatistic;
import com.wejoyclass.itops.local.mapper.WarningMapper;
import com.wejoyclass.itops.local.service.WarningService;
import com.wejoyclass.service.impl.BaseCURDServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author lixu
 * @Date 2020/1/9 18:59
 * @Version 1.0
 */

@Service
@Transactional
public class WarningServiceImpl extends BaseCURDServiceImpl<Warning,Long> implements WarningService {

    @Resource
    WarningMapper warningMapper;

    @Override
    public TodayWarningStatisticDto getWarningStatisticInToday(Long orgId) {
        return warningMapper.getWarningStatisticInToday(orgId);
    }

    // 获取某段时间内每天的告警总数量
    @Override
    public List<WarningCountEveryDayDto> getWarningCountEveryDay(WarningParameterDto warningParameterDto) {

        // 获取今天凌晨时间
        String endTime =  new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00";

        if (warningParameterDto.getIdentifier() == 1){

            // 获取本周第一天
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_MONTH, 0);
            cal.set(Calendar.DAY_OF_WEEK, 2);
            Date time=cal.getTime();
            String startTime = new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00";
            warningParameterDto.setStartTime(startTime);
            warningParameterDto.setEndTime(endTime);

            return warningMapper.getWarningCountEveryDay(warningParameterDto);

        }else if (warningParameterDto.getIdentifier() == 2){

            // 获取本月第一天
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.MONTH, 0);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            Date time=cal.getTime();
            String startTime =  new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00";
            warningParameterDto.setStartTime(startTime);
            warningParameterDto.setEndTime(endTime);

            return warningMapper.getWarningCountEveryDay(warningParameterDto);

        }else if (warningParameterDto.getIdentifier() == 3){

            // 获取本年第一天
            String startTime =  new SimpleDateFormat("yyyy").format(new Date())+"-01-01 00:00:00";
            warningParameterDto.setStartTime(startTime);
            warningParameterDto.setEndTime(endTime);

            return warningMapper.getWarningCountEveryDay(warningParameterDto);

        }else {
            return warningMapper.getWarningCountEveryDay(warningParameterDto);
        }

    }


    // 获取某段时间告警内容频率发生的TopN
    @Override
    public List<WarningInfoDto> getTopNByWarningInfoCountInPeriodTime(WarningParameterDto warningParameterDto) {
        // 获取今天凌晨时间
        String endTime =  new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00";

        if (warningParameterDto.getIdentifier() == 1){

            // 获取本周第一天
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_MONTH, 0);
            cal.set(Calendar.DAY_OF_WEEK, 2);
            Date time=cal.getTime();
            String startTime = new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00";
            warningParameterDto.setStartTime(startTime);
            warningParameterDto.setEndTime(endTime);

            return warningMapper.getTopNByWarningInfoCountInPeriodTime(warningParameterDto);

        }else if (warningParameterDto.getIdentifier() == 2){

            // 获取本月第一天
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.MONTH, 0);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            Date time=cal.getTime();
            String startTime =  new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00";
            warningParameterDto.setStartTime(startTime);
            warningParameterDto.setEndTime(endTime);

            return warningMapper.getTopNByWarningInfoCountInPeriodTime(warningParameterDto);

        }else if (warningParameterDto.getIdentifier() == 3){

            // 获取本年第一天
            String startTime =  new SimpleDateFormat("yyyy").format(new Date())+"-01-01 00:00:00";
            warningParameterDto.setStartTime(startTime);
            warningParameterDto.setEndTime(endTime);

            return warningMapper.getTopNByWarningInfoCountInPeriodTime(warningParameterDto);

        }else {
            return warningMapper.getTopNByWarningInfoCountInPeriodTime(warningParameterDto);
        }

    }

    @Override
    public Page<Warning> findWarningPage(QueryParameter queryParameter, WarningQueryParam warningQueryParam) {
        StringUtil.encodeSqlLike(warningQueryParam,"ipAddress");
        StringUtil.encodeSqlLike(warningQueryParam,"warningInfo");
        return PageUtil.process(queryParameter,()->{ return warningMapper.findWarningList(warningQueryParam);});
    }

    @Override
    public Warning getWarningById(Long warningId) {
        return warningMapper.getWarningById(warningId);
    }

    @Override
    public void saveWarning(Warning warning) {
        try{
            this.insert(warning);
        }catch (Exception ex){
            String errMsg = "Could not create warning";
            throw new IllegalStateException(errMsg, ex);
        }
    }

    @Override
    public void updateWarningByCode(Warning newWarning) {
        Warning exitingWarning = this.warningMapper.getByWarningCode(newWarning.getWarningCode());
        if(exitingWarning == null){
            throw new IllegalStateException(String.format("Warning with code %s not found", newWarning.getWarningCode()));
        }

        MapperUtil.copy(newWarning, exitingWarning);

        try {
            this.update(exitingWarning);
        } catch (Exception ex) {
            String errMsg = "Could not update the warning";
            throw new IllegalStateException(errMsg, ex);
        }

    }
}

