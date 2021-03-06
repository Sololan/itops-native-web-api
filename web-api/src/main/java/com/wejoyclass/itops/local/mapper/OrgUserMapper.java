package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.OrgUser;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 10:49 2020/2/22
 * @Modified By:
 **/
@Mapper
public interface OrgUserMapper extends CURDMapper<OrgUser, Long> {
}
