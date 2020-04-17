package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.service.mapper.CURDMapper;
import com.wejoyclass.uc.entity.DictItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/15 13:40
 **/
@Mapper
public interface DictItemMapper extends CURDMapper<DictItem,Long> {
}
