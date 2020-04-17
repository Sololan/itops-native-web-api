package com.wejoyclass.itops.local.entity;

import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorShowData extends BaseMysqlEntity {
    @ApiModelProperty(notes = "监控数据展现设置表id")
    private Long monitorShowSettingId;

    @ApiModelProperty(notes = "监控信息id")
    private String monitorId;

    @ApiModelProperty(notes = "数据项名称")
    private String dataItemName;

    @ApiModelProperty(notes = "数据源的key")
    private String dataSourceKey;

    @ApiModelProperty(notes = "排序")
    private Long sortNum;
}
