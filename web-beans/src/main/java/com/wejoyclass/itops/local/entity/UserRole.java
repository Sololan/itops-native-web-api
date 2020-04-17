package com.wejoyclass.itops.local.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 10:15 2020/2/22
 * @Modified By:
 **/
@Getter
@Setter
@Table(name = "t_base_user_role")
public class UserRole {
    private Long userId;
    private Long roleId;
}
