package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorParamDto {
    @ApiModelProperty(notes = "监控参数id")
    private Long id;

    @ApiModelProperty(notes = "参数id")
    private Long paramId;

    @ApiModelProperty(notes = "参数值")
    private String paramValue;

    @ApiModelProperty(notes = "参数code")
    private String paramCode;
}
