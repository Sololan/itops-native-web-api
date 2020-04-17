package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorQueryParam {
    @ApiModelProperty(notes = "监控名称")
    String monitorName;

    @ApiModelProperty(notes = "设备用途")
    Integer monitorUse;

    @ApiModelProperty(notes = "设备类型")
    Integer monitorType;

    @ApiModelProperty(notes = "启用状态 0：不启用 1：启用")
    Integer isValid;
}
