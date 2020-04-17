package com.wejoyclass.itops.local.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 20:25 2020/2/18
 * @Modified By:
 **/
@Table(name="t_ops_monitor_type_master")
@Getter
@Setter
public class MonitorTypeMaster {
    private Long id;
    private String monitorTypeName;
    private String monitorTypeCode;
    private Long sortNum;
}
