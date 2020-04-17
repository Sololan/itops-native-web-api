package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.dto.MonitorWarningQueryParam;
import com.wejoyclass.itops.local.dto.WarningMsgLicenseDto;
import com.wejoyclass.itops.local.entity.WarningMsgLicense;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 告警消息平台授权Mappper
 * @Author shixs
 * @CreateTime 2020/02/19
 **/
@Mapper
public interface WarningMsgLicenseMapper extends CURDMapper<WarningMsgLicense, Long> {

    /**
     * 根据条件获取告警消息平台授权列表
     * @param params
     * @return
     */
    List<WarningMsgLicenseDto> findWarningMsgList(MonitorWarningQueryParam params);

    /**
     * 查询当前生效的code
     * @param
     * @return WarningMsgLicense
     */
    WarningMsgLicense findActiveEntity();
}
