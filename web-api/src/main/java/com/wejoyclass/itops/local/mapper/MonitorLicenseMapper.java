package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.dto.MonitorLicenseDto;
import com.wejoyclass.itops.local.dto.MonitorWarningQueryParam;
import com.wejoyclass.itops.local.entity.MonitorLicense;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 监控平台授权Mappper
 * @Author shixs
 * @CreateTime 2020/02/19
 **/
@Mapper
public interface MonitorLicenseMapper extends CURDMapper<MonitorLicense, Long> {
    /**
     * 根据条件查询监控平台授权表列表
     * @param params
     * @return
     */
    List<MonitorLicenseDto> findMonitorLincenseList(MonitorWarningQueryParam params);

    /**
     * 查询当前生效的code
     * @param
     * @return MonitorLicense
     */
    MonitorLicense findActiveEntity();

    /**
     * 更新授权码里监控的个数
     * @param
     * @return MonitorLicense
     */
    void updateMonitorCount(MonitorLicense monitorLicense);
}
