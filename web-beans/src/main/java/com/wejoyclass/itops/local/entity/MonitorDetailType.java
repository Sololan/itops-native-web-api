package com.wejoyclass.itops.local.entity;

import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorDetailType extends BaseMysqlEntity {
    @ApiModelProperty(notes = "组织id")
    private Long orgId;

    @ApiModelProperty(notes = "监控信息id")
    private Long monitorId;

    @ApiModelProperty(notes = "监控类型id")
    private Long monitorTypeId;

    @ApiModelProperty(notes = "监控子类型id")
    private Long monitorTypeSubId;

    @ApiModelProperty(notes = "监控方式id")
    private Long monitorSubMethodId;

    @ApiModelProperty(notes = "设备品牌（字典项）")
    private Long brand;

    @ApiModelProperty(notes = "设备型号")
    private String equipmentMode;

    @ApiModelProperty(notes = "zabbix模板名称")
    private String templateName;

    @ApiModelProperty(notes = "zabbix模板id")
    private String templateId;

    @ApiModelProperty(notes = "排序")
    private Long sortNum;

    @ApiModelProperty(notes = "主机id")
    private Long hostId;

}
