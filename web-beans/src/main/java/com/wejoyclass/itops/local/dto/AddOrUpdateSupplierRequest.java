package com.wejoyclass.itops.local.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddOrUpdateSupplierRequest {
    @NotBlank
    @Size(max=100)
    private String supplierName;   //供应商名称
    @NotBlank
    @Size(max=50)
    private String contacts;    //联系人
    @NotBlank
    @Size(max=20)
//    @PhoneNumber
    private String contactTel; //联系电话
    @Size(max=50)
    @Email
    private String email;   //邮箱
}
