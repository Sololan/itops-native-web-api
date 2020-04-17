package com.wejoyclass.itops.local.service.impl;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.util.PageUtil;
import com.wejoyclass.itops.local.dto.MonitorLicenseDto;
import com.wejoyclass.itops.local.dto.MonitorWarningQueryParam;
import com.wejoyclass.itops.local.entity.MonitorLicense;
import com.wejoyclass.itops.local.mapper.MonitorLicenseMapper;
import com.wejoyclass.itops.local.service.MonitorLicenseService;
import com.wejoyclass.service.impl.BaseCURDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 监控平台授权service
 * @Author shixs
 * @CreateTime 2020/02/19
 **/

@Service
@Transactional
public class MonitorLicenseServiceImpl extends BaseCURDServiceImpl<MonitorLicense, Long> implements MonitorLicenseService {
    @Autowired
    private MonitorLicenseMapper monitorLicenseMapper;


    @Override
    public List<MonitorLicenseDto> findMonitorLincenseList(MonitorWarningQueryParam params) {
        return monitorLicenseMapper.findMonitorLincenseList(params);
    }

    @Override
    public Page<MonitorLicenseDto> findMonitorLicesePage(QueryParameter queryParameter, MonitorWarningQueryParam params) {
        return PageUtil.process(queryParameter, () -> findMonitorLincenseList(params));
    }
}
