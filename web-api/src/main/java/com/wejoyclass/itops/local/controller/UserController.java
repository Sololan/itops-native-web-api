package com.wejoyclass.itops.local.controller;

import com.wejoyclass.core.dto.security.UserDto;
import com.wejoyclass.core.params.Request;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import com.wejoyclass.itops.local.dto.User4SaveDto;
import com.wejoyclass.itops.local.feign.UcService;
import com.wejoyclass.uc.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lixu
 * @Date 2020/1/13 21:06
 * @Version 1.0
 */

@RestController
@RequestMapping("/users")
@Api(tags = "用户管理")
public class UserController {


    @Autowired
    private UcService ucService;


    @ApiOperation("添加用户")
    @PostMapping("/save")
    public RespEntity saveUser(@RequestBody User4SaveDto userDto){

        //调用uc
        return ucService.saveUser(userDto);

        //同步到云端
        //cloudService.synchroAddUser()

    }

    @ApiOperation("根据用户id更新用户信息")
    @PostMapping("/{id}")
    public RespEntity updateUser(@PathVariable("id") Long id, @RequestBody User4SaveDto userDto){

        //调用uc更新
        return ucService.updateUser(id, userDto);

        //同步到云端-更新
        //cloudService.synchroUpdateUser(id,user);

    }

    @ApiOperation("根据用户id删除用户")
    @PostMapping("/{id}/delete")
    public RespEntity deleteUser(@PathVariable("id") Long id){

        //调用uc删除
        return ucService.deleteUser(id);

        //云端删除
        //cloudService.synchroDeleteUser(id);

    }

    @ApiOperation("用户重置密码")
    @PostMapping("/{id}/resetPwd")
    public RespEntity resetPassword(@PathVariable("id") Long id){

        //调用uc重置密码
        return ucService.resetPassword(id);

        //云端重置密码
        //cloudService.synchroResetPassword(id);
    }



}
