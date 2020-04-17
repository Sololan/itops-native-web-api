package com.wejoyclass.itops.local.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorType {
    @ApiModelProperty(notes = "id")
    private Long id;

    @ApiModelProperty(notes = "监控类型名称")
    private String monitorTypeName;

    @ApiModelProperty(notes = "监控类型code")
    private String monitorTypeCode;

    @ApiModelProperty(notes = "排序")
    private Integer sortNum;
}
