package com.wejoyclass.itops.local.service;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.service.service.CURDService;
import com.wejoyclass.itops.local.dto.MonitorLicenseDto;
import com.wejoyclass.itops.local.dto.MonitorWarningQueryParam;
import com.wejoyclass.itops.local.entity.MonitorLicense;

import java.util.List;

/**
 * 监控平台授权Service
 * @Author shixs
 * @CreateTime 2020/02/19
 **/
public interface MonitorLicenseService extends CURDService<MonitorLicense, Long> {


    /**
     * 根据条件查询监控平台授权表列表
     * @param params
     * @return
     */
    List<MonitorLicenseDto> findMonitorLincenseList(MonitorWarningQueryParam params);

    /**
     * 根据条件分页查询
     * @param queryParameter
     * @param params
     * @return
     */
    Page<MonitorLicenseDto> findMonitorLicesePage(QueryParameter queryParameter, MonitorWarningQueryParam params);


}
