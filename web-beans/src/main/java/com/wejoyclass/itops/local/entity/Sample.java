package com.wejoyclass.itops.local.entity;

import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 示例实体
 */
@Table(name = "t_sample")
@Getter
@Setter
public class Sample extends BaseMysqlEntity {


}
