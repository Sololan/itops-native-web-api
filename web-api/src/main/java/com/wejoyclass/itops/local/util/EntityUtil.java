package com.wejoyclass.itops.local.util;


import com.wejoyclass.core.service.entity.BaseMysqlEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class EntityUtil {

    //todo current user info
    public static void setCreate(BaseMysqlEntity entity){
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = userDetails.getUsername();
        entity.setCreateUser(new Long(1));
        entity.setCreateUsername("create username");
        entity.setCreateTime(new Date());
    }
    public static void setUpdate(BaseMysqlEntity entity){
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = userDetails.getUsername();
        entity.setUpdateUser(new Long(1));
        entity.setUpdateUsername("update username");
        entity.setUpdateTime(new Date());
    }
    public static void setDelete(BaseMysqlEntity entity){
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = userDetails.getUsername();
        entity.setDeleteUser(new Long(1));
        entity.setDeleteUsername("delete username");
        entity.setDeleteTime(new Date());
        entity.setDeleteFlag(entity.getId().intValue());
    }
}
