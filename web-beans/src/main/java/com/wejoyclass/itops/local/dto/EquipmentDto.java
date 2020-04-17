package com.wejoyclass.itops.local.dto;

import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentDto{

    private Long id;

    @ApiModelProperty(notes = "根机构(T_BASE_ORG.ID)")
    private Long orgId;

    @NotBlank
    @Size(max=100)
    @ApiModelProperty(notes = "设备编号")
    private String  equipmentCode;

    @NotNull
    @ApiModelProperty(notes = "设备类型（字典项)")
    private Long equipmentType;

    @NotNull
    @ApiModelProperty(notes = "设置子类型（字典项)")
    private Long equipmentSubType;

    @ApiModelProperty(notes = "设备品牌（字典项)")
    private Long brand;

    @Size(max=100)
    @ApiModelProperty(notes = "设备型号")
    private String equipmentMode;

    @Size(max=100)
    @ApiModelProperty(notes = "服务编号")
    private String serviceNumber;

    @ApiModelProperty(notes = "购入日期")
    private Date purchaseDate;

    @ApiModelProperty(notes = "质保截止日期")
    private Date expiration;

    @ApiModelProperty(notes = "供应商")
    private Long supplierId;

    @ApiModelProperty(notes = "设备子类型（字典项key）")
    private String equipmentSubTypeKey;
}
