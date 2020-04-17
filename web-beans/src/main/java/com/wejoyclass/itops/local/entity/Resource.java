package com.wejoyclass.itops.local.entity;

import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 16:42 2020/2/25
 * @Modified By:
 **/
@Getter
@Setter
public class Resource extends BaseMysqlEntity {

    //资源名称
    private String name;

    //pid
    private Long pid;

    //资源类型
    private String type;

    //资源code
    private String code;

    //uri路径
    private String uri;

    //资源权限字符串
    private String permission;

    //是否启用
    private Long enabled;

    //排序
    private Long sort;

    //资源备注
    private String remarks;

    //图标名称
    private String icon;

    //项目编号
    private String projectNo;
}
