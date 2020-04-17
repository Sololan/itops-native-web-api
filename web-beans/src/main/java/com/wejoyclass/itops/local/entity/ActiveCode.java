package com.wejoyclass.itops.local.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/14 9:06
 **/
@Getter
@Setter
@Table(name = "t_ops_active_code")
public class ActiveCode {

    //id
    private Long id;

    //组织id
    private Long orgId;

    //监控授权码
    private String monitorActiveCode;

    //通知授权码
    private String warningMsgActiveCode;
}
