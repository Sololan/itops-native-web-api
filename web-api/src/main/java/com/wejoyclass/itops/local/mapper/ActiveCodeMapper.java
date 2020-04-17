package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.ActiveCode;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Desc
 * @Author zhuzhao
 * @CreateTime 2020/1/15 11:42
 **/
@Mapper
public interface ActiveCodeMapper extends CURDMapper<ActiveCode, Long> {
    ActiveCode findOne();
}
