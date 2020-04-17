package com.wejoyclass.itops.local.entity;

import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
public class MonitorShowSetting extends BaseMysqlEntity {
    @ApiModelProperty(notes = "组织id")
    private Long orgId;

    @ApiModelProperty(notes = "监控信息id")
    private Long monitorId;

    @ApiModelProperty(notes = "监控类型id")
    private Long monitorTypeId;

    @ApiModelProperty(notes = "监控项类型")
    private String monitorItemType;

    @ApiModelProperty(notes = "监控项名称")
    private String monitorItemName;

    @ApiModelProperty(notes = "图标显示类型")
    private Integer chartType;

    @ApiModelProperty(notes = "开关类图表的图表-正常")
    private String icon1;

    @ApiModelProperty(notes = "开关类图表的图表-异常")
    private String icon2;

    @ApiModelProperty(notes = "正常值")
    private String okValue;

    @ApiModelProperty(notes = "排序")
    private Integer sortNum;

    @ApiModelProperty(notes = "排序")
    @Transient
    private List<MonitorShowData> monitorShowDataList;
}
