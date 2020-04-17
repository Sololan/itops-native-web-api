package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.wejoyclass.uc.entity.Org;

import java.util.List;

@Mapper
public interface OrgMapper extends CURDMapper<Org, Long> {

    String TABLE_NAME = "t_base_org";

    @Select({"select id, FULL_NAME from ", TABLE_NAME, "where DELETE_FLAG = 0"})
    List<Org> getOrgList();
}
