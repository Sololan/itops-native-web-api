package com.wejoyclass.itops.local.entity;

import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import lombok.*;

import javax.persistence.Table;

@Table(name = "t_ops_supplier")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supplier extends BaseMysqlEntity {

    private Long orgId; //根机构(T_BASE_ORG.ID)

    private String supplierName;   //供应商名称

    private String contacts;    //联系人

    private String contactTel; //联系电话

    private String email;   //邮箱
}
