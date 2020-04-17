package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentQueryParam {

    @ApiModelProperty(notes = "组织id")
    private Long  orgId;

    @ApiModelProperty(notes = "设备编号")
    private String  equipmentCode;

    @ApiModelProperty(notes = "设备类型（字典项)")
    private Long equipmentType;

    @ApiModelProperty(notes = "设置子类型（字典项)")
    private Long equipmentSubType;

    @ApiModelProperty(notes = "设备品牌（字典项)")
    private Long brand;

    @ApiModelProperty(notes = "供应商")
    private Long supplierId;

    @ApiModelProperty(notes = "from购入日期")
    private Date fromPurchaseDate;

    @ApiModelProperty(notes = "to购入日期")
    private Date toPurchaseDate;

}
