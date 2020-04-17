package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.AssertTrue;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierQueryParam {

    @ApiModelProperty(notes = "组织id")
    private Long orgId;

    @ApiModelProperty(notes = "供应商名称")
    private String supplierName;

    @ApiModelProperty(notes = "联系人")
    private String contacts;

    @ApiModelProperty(notes = "from添加日期")
    private Date fromCreateTime;

    @ApiModelProperty(notes = "to添加日期")
    private Date toCreateTime;

}
