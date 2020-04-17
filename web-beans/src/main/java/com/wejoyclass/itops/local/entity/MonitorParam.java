package com.wejoyclass.itops.local.entity;

import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorParam extends BaseMysqlEntity {
    @ApiModelProperty(notes = "组织id")
    private Long orgId;

    @ApiModelProperty(notes = "监控id")
    private Long monitorId;

    @ApiModelProperty(notes = "设备监控类型表id")
    private Long equipmentMonitorType;

    @ApiModelProperty(notes = "参数id")
    private Long paramId;

    @ApiModelProperty(notes = "参数值")
    private String paramValue;
}
