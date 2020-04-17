package com.wejoyclass.itops.local.feign;

import com.wejoyclass.core.dto.security.UserDto;
import com.wejoyclass.core.util.RespEntity;
import com.wejoyclass.itops.local.dto.DictGroupDto;
import com.wejoyclass.itops.local.dto.User4SaveDto;
import com.wejoyclass.uc.entity.DictItem;
import com.wejoyclass.uc.entity.Org;
import com.wejoyclass.uc.entity.Role;
import com.wejoyclass.uc.entity.User;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "uc", configuration = {UcService.MultipartSupportConfig.class})
public interface UcService {

    //根据字典组key获取字典项列表
    @GetMapping("/dicts/groups/keys/{key}/items")
    RespEntity<List<DictItem>> getItemsByKey(@PathVariable("key") String key);

    //插入组织
    @PostMapping("/orgs")
    RespEntity<Org> saveOrg(@RequestBody Org org);

    //保存用户
    @PostMapping("/users")
    RespEntity saveUser(@RequestBody User4SaveDto userDto);

    //保存用户
    @PostMapping("/users")
    RespEntity saveUser(@RequestBody UserDto userDto);

    //根据用户id更新用户
    @PostMapping("/users/{id}")
    RespEntity updateUser(@PathVariable("id") Long id, @RequestBody User4SaveDto userDto);

    //根据用户id删除用户
    @PostMapping("/users/{id}/delete")
    RespEntity deleteUser(@PathVariable("id") Long id);

    //用户重置密码
    @PostMapping("/users/{id}/resetPwd")
    RespEntity resetPassword(@PathVariable("id") Long id);

    //添加角色
    @PostMapping("/roles")
    RespEntity saveRoles(@RequestBody Role role);

    //插入字典组
    @PostMapping("/dicts/groups")
    RespEntity saveDictsGroups(@RequestBody DictGroupDto dictGroupDto);




    /**
     * 引用配置类MultipartSupportConfig.并且实例化
     */
    @Configuration
    public class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        @Primary
        @Scope("prototype")
        public Encoder feignFormEncoder() {

            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }
}
