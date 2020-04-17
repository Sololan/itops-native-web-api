package com.wejoyclass.itops.local.service.impl;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.util.DateUtil;
import com.wejoyclass.core.util.MapperUtil;
import com.wejoyclass.core.util.PageUtil;
import com.wejoyclass.itops.local.dto.MonitorWarningQueryParam;
import com.wejoyclass.itops.local.dto.WarningMsgLicenseDto;
import com.wejoyclass.itops.local.entity.WarningMsgLicense;
import com.wejoyclass.itops.local.mapper.WarningMsgLicenseMapper;
import com.wejoyclass.itops.local.service.WarningMsgLicenseService;
import com.wejoyclass.service.impl.BaseCURDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 告警消息平台授权service实现
 * @Author shixs
 * @CreateTime 2020/1/8 18:39
 **/

@Service
@Transactional
public class WarningMsgLicenseSericeImpl extends BaseCURDServiceImpl<WarningMsgLicense, Long> implements WarningMsgLicenseService {

    @Autowired
    private WarningMsgLicenseMapper warningMsgLicenseMapper;


    @Override
    public List<WarningMsgLicenseDto> findWarningMsgList(MonitorWarningQueryParam params) {
        return warningMsgLicenseMapper.findWarningMsgList(params);
    }

    @Override
    public Page<WarningMsgLicenseDto> findWarningMsgPage(QueryParameter queryParameter, MonitorWarningQueryParam params) {
        return PageUtil.process(queryParameter, () -> findWarningMsgList(params));
    }
}
