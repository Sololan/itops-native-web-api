package com.wejoyclass.itops.local.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Table(name="t_ops_monitor_method_param")
@Getter
@Setter
public class MonitorMethodParam {
    @ApiModelProperty(notes = "id")
    private Long id;

    @ApiModelProperty(notes = "监控分类监控方式id")
    private Long monitorMethodId;

    @ApiModelProperty(notes = "参数code")
    private String paramCode;

    @ApiModelProperty(notes = "参数名称")
    private String paramName;

    @ApiModelProperty(notes = "默认值")
    private String defaultValue;

    @ApiModelProperty(notes = "排序")
    private Integer sortNum;

    @ApiModelProperty(notes = "是否为必须")
    private Integer isRequire;
}
