package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.MonitorTypeMaster;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 20:56 2020/2/18
 * @Modified By:
 **/
@Mapper
public interface MonitorTypeMasterMapper extends CURDMapper<MonitorTypeMaster, Long> {
    List<MonitorTypeMaster> findAll();
}
