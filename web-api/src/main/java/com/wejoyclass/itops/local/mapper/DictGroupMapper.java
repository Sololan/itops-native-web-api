package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.service.mapper.CURDMapper;
import com.wejoyclass.uc.entity.DictGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/15 13:39
 **/
@Mapper
public interface DictGroupMapper extends CURDMapper<DictGroup,Long> {
}
