package com.wejoyclass.itops.local.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "T_OPS_EQUIPMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Equipment extends BaseMysqlEntity {

    @ApiModelProperty(notes = "组织id")
    private Long orgId;

    @ApiModelProperty(notes = "设备编号")
    private String  equipmentCode;

    @ApiModelProperty(notes = "设备类型（字典项)")
    private Long equipmentType;

    @ApiModelProperty(notes = "设备子类型（字典项)")
    private Long equipmentSubType;

    @ApiModelProperty(notes = "设备品牌（字典项)")
    private Long brand;

    @ApiModelProperty(notes = "设备型号")
    private String equipmentMode;

    @ApiModelProperty(notes = "服务编号")
    private String serviceNumber;

    @ApiModelProperty(notes = "购入日期")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date purchaseDate;

    @ApiModelProperty(notes = "质保截止日期")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiration;

    @ApiModelProperty(notes = "供应商")
    private Long supplierId;

    @Transient
    @ApiModelProperty(notes = "设备子类型（字典项key）")
    private String equipmentSubTypeKey;

    @Transient
    @ApiModelProperty(notes = "供应商名称")
    private String supplierName;

    @Transient
    @ApiModelProperty(notes = "设备类型名称")
    private String equipmentTypeName;

    @Transient
    @ApiModelProperty(notes = "设备子类型名称")
    private String equipmentSubTypeName;

    @Transient
    @ApiModelProperty(notes = "品牌名称")
    private String brandName;
}
