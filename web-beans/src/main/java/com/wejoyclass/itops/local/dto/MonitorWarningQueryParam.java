package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/13 18:25
 **/
@Getter
@Setter
public class MonitorWarningQueryParam {

    @ApiModelProperty(notes = "开始时间的时间范围")
    private String startTime;

    @ApiModelProperty(notes = "开始时间的时间范围")
    private String endTime;

    @ApiModelProperty(notes = "结束时间的时间范围")
    private String endStartTime;

    @ApiModelProperty(notes = "结束时间的时间范围")
    private String endEndTime;

    @ApiModelProperty(notes = "状态，是否过期")
    private String status;

    private Long orgId;
}
