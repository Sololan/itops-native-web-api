package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.service.mapper.CURDMapper;
import com.wejoyclass.uc.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 14:50 2020/2/22
 * @Modified By:
 **/
@Mapper
public interface RoleMapper extends CURDMapper<Role, Long> {
}
