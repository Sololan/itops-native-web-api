package com.wejoyclass.itops.local.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 19:40 2020/2/18
 * @Modified By:
 **/
@Table(name="t_ops_equipment_monitor_type")
@Getter
@Setter
public class EquipmentMonitorType {
    private Long id;
    private Long equipmentSubType;
    private Long monitorTypeId;
}
