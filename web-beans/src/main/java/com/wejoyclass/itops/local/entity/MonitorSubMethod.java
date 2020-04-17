package com.wejoyclass.itops.local.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Table(name="t_ops_monitor_sub_method")
@Getter
@Setter
public class MonitorSubMethod {
    @ApiModelProperty(notes = "id")
    private Long id;

    @ApiModelProperty(notes = "监控子类型id")
    private Long monitorTypeSubId;

    @ApiModelProperty(notes = "监控方式")
    private String methodName;

    @ApiModelProperty(notes = "排序")
    private Integer sortNum;
}
