package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorCount {
    @ApiModelProperty(notes = "监控类型")
    private String monitorTypeName;

    @ApiModelProperty(notes = "数量")
    private Integer count;

}
