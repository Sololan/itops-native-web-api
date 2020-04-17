package com.wejoyclass.itops.local.dto;

import com.wejoyclass.itops.local.entity.MonitorLicense;
import com.wejoyclass.itops.local.entity.WarningMsgLicense;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/14 11:29
 **/
@Setter
@Getter
public class MonitorWarningDto {

    @ApiModelProperty(notes = "监控授权实体")
    private MonitorLicense monitorLicense;

    @ApiModelProperty(notes = "通知授权实体")
    private WarningMsgLicense warningMsgLicense;
}
