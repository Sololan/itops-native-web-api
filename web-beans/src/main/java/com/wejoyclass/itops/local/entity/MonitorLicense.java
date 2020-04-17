package com.wejoyclass.itops.local.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/8 14:38
 **/
@Getter
@Setter
@Table(name="t_ops_monitor_license")
public class MonitorLicense extends BaseMysqlEntity {

    private Long orgId;

    //激活码
    private String licenseCode;

    //开始日期
    private Date startDate;

    //结束日期
    private Date endDate;

    //过期时间
    private Date expireTime;

    //硬件数量
    private Long hardwareQuantity;

    //操作系统数量
    private Long osQuantity;

    //网络数量
    private Long networkQuantity;

    //数据库数量
    private Long dbQuantity;

    //中间件数量
    private Long middlewareQuantity;

    //应用数量
    private Long applicationQuantity;

    //web数量
    private Long webQuantity;

    //存储数量
    private Long storageQuantity;

    //已使用的硬件数量
    private Long usedHardwareQuantity;

    //已使用的数据库数量
    private Long usedOsQuantity;

    //已使用的网络数量
    private Long usedNetworkQuantity;

    //已使用的数据库数量
    private Long usedDbQuantity;

    //已使用的中间件数量
    private Long usedMiddlewareQuantity;

    //已使用的应用数量
    private Long usedApplicationQuantity;

    //已使用的web数量
    private Long usedWebQuantity;

    //已使用的存储数量
    private Long usedStorageQuantity;
}
