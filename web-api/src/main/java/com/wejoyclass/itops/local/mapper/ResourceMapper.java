package com.wejoyclass.itops.local.mapper;

import com.wejoyclass.itops.local.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author:Zz
 * @Description:
 * @Data Created in: 10:04 2020/2/20
 * @Modified By:
 **/
@Mapper
public interface ResourceMapper {
    void initResource();

    List<Resource> getAllResources();

    void initResourceRoles(Long roleId, Long resourceId);

    @Update({"truncate TABLE t_base_org_user"})
    void truncatet_base_org_user();

    @Update({"truncate TABLE t_base_resource"})
    void truncatet_base_resource();

    @Update({"truncate TABLE t_base_role"})
    void truncatet_base_role();

    @Update({"truncate TABLE t_base_role_resource"})
    void truncatet_base_role_resource();

    @Update({"truncate TABLE t_base_user"})
    void truncatet_base_user();

    @Update({"truncate TABLE t_base_user_role"})
    void truncatet_base_user_role();

    @Update({"truncate TABLE t_base_dict_group"})
    void truncatet_base_dict_group();

    @Update({"truncate TABLE t_base_dict_item"})
    void truncatet_base_dict_item();

    @Update({"truncate TABLE t_base_org"})
    void truncatet_base_org();
}
