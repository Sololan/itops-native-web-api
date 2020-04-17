package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MonitorDetailTypeDto {
    @ApiModelProperty(notes = "监控类型id")
    private Long id;

    @ApiModelProperty(notes = "监控类型id")
    private Long monitorTypeId;

    @ApiModelProperty(notes = "监控子类型id")
    private Long monitorTypeSubId;

    @ApiModelProperty(notes = "监控方式id")
    private Long monitorSubMethodId;

    @ApiModelProperty(notes = "监控类型code")
    private String monitorTypeCode;

    @ApiModelProperty(notes = "监控子类型name")
    private String monitorTypeSubName;

    @ApiModelProperty(notes = "监控方式name")
    private String monitorSubMethodName;

    @ApiModelProperty(notes = "监控参数列表")
    private List<MonitorParamDto> monitorParam;
}
