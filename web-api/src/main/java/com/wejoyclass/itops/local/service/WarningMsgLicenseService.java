package com.wejoyclass.itops.local.service;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.service.service.CURDService;
import com.wejoyclass.itops.local.dto.MonitorWarningQueryParam;
import com.wejoyclass.itops.local.dto.WarningMsgLicenseDto;
import com.wejoyclass.itops.local.entity.WarningMsgLicense;

import java.util.List;

/**
 * 告警消息平台授权service实现
 * @Author shixs
 * @CreateTime 2020/02/19
 **/
public interface WarningMsgLicenseService extends CURDService<WarningMsgLicense, Long> {


    /**
     * 根据条件获取告警消息平台授权列表
     * @param params
     * @return
     */
    List<WarningMsgLicenseDto> findWarningMsgList(MonitorWarningQueryParam params);

    /**
     * 根据条件分页查询
     * @param queryParameter
     * @param params
     * @return
     */
    Page<WarningMsgLicenseDto> findWarningMsgPage(QueryParameter queryParameter, MonitorWarningQueryParam params);
}
