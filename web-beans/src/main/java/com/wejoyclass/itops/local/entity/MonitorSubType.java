package com.wejoyclass.itops.local.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorSubType {
    @ApiModelProperty(notes = "id")
    private Long id;

    @ApiModelProperty(notes = "设备子类型字典项id")
    private Long equipmentSubType;

    @ApiModelProperty(notes = "监控类型id")
    private Long monitorTypeId;

    @ApiModelProperty(notes = "监控子类型名")
    private String subName;

    @ApiModelProperty(notes = "排序")
    private Integer sortNum;
}
