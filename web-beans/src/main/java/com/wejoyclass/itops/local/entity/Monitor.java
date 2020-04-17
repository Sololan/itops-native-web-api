package com.wejoyclass.itops.local.entity;

import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "T_OPS_MONITOR")
@Getter
@Setter
@ToString
public class Monitor extends BaseMysqlEntity {

    @ApiModelProperty(notes = "组织id")
    private Long orgId;

    @ApiModelProperty(notes = "监控名称")
    private String monitorName;

    @ApiModelProperty(notes = "设备用途字典项id")
    private Long businessSystem;

    @ApiModelProperty(notes = "设备id")
    private Long equipmentId;

    @ApiModelProperty(notes = "是否启用")
    private Integer isValid;

    @ApiModelProperty(notes = "ip地址1")
    private String ipAddress1;

    @ApiModelProperty(notes = "ip地址2")
    private String ipAddress2;

    @ApiModelProperty(notes = "hostid")
    private String hostid;

    @Transient
    @ApiModelProperty(notes = "设备用途名称")
    private String businessSystemName;

    @Transient
    @ApiModelProperty(notes = "设备类型")
    private String equipmentType;

    @Transient
    @ApiModelProperty(notes = "设备code")
    private String equipmentCode;

}
