package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDto {

    private Long id;

    @ApiModelProperty(notes = "根机构(T_BASE_ORG.ID)")
    @NotNull
    @Min(1)
    private Long orgId;

    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(notes = "供应商名称")
    private String supplierName;

    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(notes = "联系人")
    private String contacts;

    @NotBlank
//    @PhoneNumber
    @Size(max = 20)
    @ApiModelProperty(notes = "联系电话")
    private String contactTel;

    @NotBlank
    @Email
    @Size(max = 50)
    @ApiModelProperty(notes = "邮箱")
    private String email;

    @ApiModelProperty(notes = "添加日期")
    private Date createTime;
}
