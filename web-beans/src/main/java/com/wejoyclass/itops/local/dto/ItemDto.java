package com.wejoyclass.itops.local.dto;

import com.wejoyclass.uc.entity.Org;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

// 用于接收适配器返回的监控项列表
@Getter
@Setter
public class ItemDto {
    @ApiModelProperty(notes = "监控项id")
    private String itemId;

    @ApiModelProperty(notes = "监控项名")
    private String itemName;
}
