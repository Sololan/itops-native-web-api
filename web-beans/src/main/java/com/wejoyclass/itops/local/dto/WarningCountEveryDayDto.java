package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lixu
 * @Date 2020/1/14 14:31
 * @Version 1.0
 */

@Setter
@Getter
public class WarningCountEveryDayDto {

    //日期
    @ApiModelProperty(value = "日期")
    private String dates;

    //当日的告警总数
    @ApiModelProperty(value = "当日的告警数量")
    private Integer warningNumber;

}
