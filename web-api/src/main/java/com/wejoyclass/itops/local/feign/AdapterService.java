package com.wejoyclass.itops.local.feign;

import com.alibaba.fastjson.JSONObject;
import com.wejoyclass.itops.local.adapter.HostParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "monitor-adapter")
public interface AdapterService {
    //根据字典组key获取字典项列表
    @GetMapping("/adapter/host/{id}/items")
    Map getItemsByKey(@PathVariable("id") String id);

    //根据字典组key获取字典项列表
    @GetMapping("/adapter/templates")
    List<Map<String,String>> getTemplates();

    //保存主机
    @PostMapping("/adapter/host")
    Map saveHost(Object object);

    //禁用主机
    @GetMapping("/adapter/host/{id}/forbid")
    Map forbidHost(@PathVariable("id") String id);

    //禁用主机
    @GetMapping("/adapter/host/{id}/unforbid")
    Map unforbidHost(@PathVariable("id") String id);

    //删除主机
    @PostMapping("/adapter/host/{id}/delete")
    Map deleteHost(@PathVariable("id") String id);

    //更新主机
    @PostMapping("/adapter/host/{id}")
    Map updateHost(@PathVariable("id") Integer id,Object object);
}
