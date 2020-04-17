package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author lixu
 * @Date 2020/1/13 15:25
 * @Version 1.0
 */

@Setter
@Getter
public class TodayWarningStatisticDto{

    @ApiModelProperty(value = "今日提醒警告数量")
    private String remindCount;

    @ApiModelProperty(value = "今日预告警告数量")
    private String preCount;

    @ApiModelProperty(value = "今日严重警告数量")
    private String heavyCount;

    @ApiModelProperty(value = "今日关闭警告数量")
    private String closedCount;

    @ApiModelProperty(value = "今日警告总数量")
    private String totalCount;

}
