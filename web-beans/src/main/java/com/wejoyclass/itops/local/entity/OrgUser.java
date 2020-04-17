package com.wejoyclass.itops.local.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 10:46 2020/2/22
 * @Modified By:
 **/
@Getter
@Setter
@Table(name = "t_base_org_user")
public class OrgUser {
    private Long orgId;
    private Long userId;
}
