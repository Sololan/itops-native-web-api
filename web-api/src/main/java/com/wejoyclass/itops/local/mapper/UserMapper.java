package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.service.mapper.CURDMapper;
import com.wejoyclass.uc.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 11:27 2020/2/22
 * @Modified By:
 **/
@Mapper
public interface UserMapper extends CURDMapper<User, Long> {
}
