package com.wejoyclass.itops.local.service;

import com.wejoyclass.itops.local.dto.InitDataDto;
import com.wejoyclass.itops.local.dto.MonitorWarningDto;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/14 16:36
 **/
public interface CloudService {
    void nativeAuthorize();

    void verifyLicenseCode();

    void initData(InitDataDto initDataDto);

}
