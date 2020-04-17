package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MonitorDetailSettingPageDto {
    @ApiModelProperty(notes = "监控id")
    private Long id;

    @ApiModelProperty(notes = "监控名称")
    private String monitorName;

    @ApiModelProperty(notes = "用途")
    private String businessSystemName;

    @ApiModelProperty(notes = "是否启用")
    private Integer isValid;

    @ApiModelProperty(notes = "设备id")
    private Long equipmentId;

    @ApiModelProperty(notes = "设备编号")
    private String equipmentCode;

    @ApiModelProperty(notes = "设备类型")
    private String equipmentTypeName;

    @ApiModelProperty(notes = "设备子类型")
    private String equipmentSubTypeName;

    @ApiModelProperty(notes = "设备品牌")
    private String brandName;

    @ApiModelProperty(notes = "设备型号")
    private String equipmentMode;

    @ApiModelProperty(notes = "服务编号")
    private String serviceNumber;

    @ApiModelProperty(notes = "购入日期")
    private Date purchaseDate;

    @ApiModelProperty(notes = "质保截止日期")
    private Date expiration;

    @ApiModelProperty(notes = "供应商名称")
    private String supplierName;

    @ApiModelProperty(notes = "主机id")
    private String hostid;

    @ApiModelProperty(notes = "监控项列表")
    private List<ItemDto> itemList;
}
