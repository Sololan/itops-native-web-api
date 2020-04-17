package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MonitorDetailDto {
    @ApiModelProperty(notes = "监控id")
    private Long id;

    @ApiModelProperty(notes = "监控名称")
    private String monitorName;

    @ApiModelProperty(notes = "设备用户（字典id）")
    private Long businessSystem;

    @ApiModelProperty(notes = "设备id")
    private Long equipmentId;

    @ApiModelProperty(notes = "是否启用id")
    private Integer isValid;

    @ApiModelProperty(notes = "品牌（字典项id）")
    private Long brand;

    @ApiModelProperty(notes = "设备型号")
    private String equipmentMode;

    @ApiModelProperty(notes = "品牌")
    private String brandName;

    @ApiModelProperty(notes = "监控参数列表")
    private List<MonitorDetailTypeDto> monitorDetailType;
}
