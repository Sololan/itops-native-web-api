package com.wejoyclass.itops.local.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 20:18 2020/2/18
 * @Modified By:
 **/
@Table(name="t_ops_monitor_type_sub")
@Getter
@Setter
public class MonitorTypeSub {

    private Long id;
    private Long equipmentSubType;
    private Long monitorTypeId;
    private String subName;
    private Long sortNum;
}
