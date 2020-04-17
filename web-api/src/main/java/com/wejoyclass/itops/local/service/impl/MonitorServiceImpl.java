package com.wejoyclass.itops.local.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.util.CacheUtil;
import com.wejoyclass.core.util.ExceptionUtil;
import com.wejoyclass.core.util.MapperUtil;
import com.wejoyclass.core.util.PageUtil;
import com.wejoyclass.itops.local.adapter.HostParam;
import com.wejoyclass.itops.local.adapter.Interfaces;
import com.wejoyclass.itops.local.adapter.Macros;
import com.wejoyclass.itops.local.dto.*;
import com.wejoyclass.itops.local.entity.*;
import com.wejoyclass.itops.local.feign.AdapterService;
import com.wejoyclass.itops.local.mapper.*;
import com.wejoyclass.itops.local.service.MonitorService;
import com.wejoyclass.service.impl.BaseCURDServiceImpl;
import com.wejoyclass.service.util.EntityUtil;
import com.wejoyclass.service.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = false)
public class MonitorServiceImpl extends BaseCURDServiceImpl<Monitor,Long> implements MonitorService {
    @Autowired
    MonitorMapper monitorMapper;

    @Autowired
    MonitorTypeMapper monitorTypeMapper;

    @Autowired
    MonitorSubTypeMapper monitorSubTypeMapper;

    @Autowired
    MonitorSubMethodMapper monitorSubMethodMapper;

    @Autowired
    MonitorMethodParamMapper monitorMethodParamMapper;

    @Autowired
    MonitorDetailTypeMapper monitorDetailTypeMapper;

    @Autowired
    MonitorParamMapper monitorParamMapper;

    @Autowired
    MonitorShowSettingMapper monitorShowSettingMapper;

    @Autowired
    MonitorShowDataMapper monitorShowDataMapper;

    @Autowired
    MonitorTypeSubMapper monitorTypeSubMapper;

    @Autowired
    AdapterService adapterService;

    @Autowired
    MonitorLicenseMapper monitorLicenseMapper;

    @Override
    public Page<Monitor> findMonitorPage(QueryParameter queryParameter, MonitorQueryParam params) {
        return PageUtil.process(queryParameter,() -> {
            return this.findMonitorList(params);
        });
    }

    @Override
    public List<Monitor> findMonitorList(MonitorQueryParam params) {
        return monitorMapper.findMonitorList(params);
    }

    @Override
    public List<MonitorType> getMonitorTypeByEquipmentSubTypeId(Long id) {
        return monitorTypeMapper.getMonitorTypeByEquipmentSubTypeId(id);
    }

    @Override
    public List<MonitorSubType> getMonitorTypeSubByMonitorTypeId(Long equipmentSubTypeId,Long monitorTypeId) {
        return monitorSubTypeMapper.getMonitorTypeSubByMonitorTypeId(equipmentSubTypeId,monitorTypeId);
    }

    @Override
    public List<MonitorSubMethod> getMonitorSubMethodByMonitorTypeSubId(Long id) {
        return monitorSubMethodMapper.getMonitorSubMethodByMonitorTypeSubId(id);
    }

    @Override
    public List<MonitorMethodParam> getMonitorMethodParamByMonitorSubMethodId(Long id) {
        return monitorMethodParamMapper.getMonitorMethodParamByMonitorSubMethodId(id);
    }

    @Override
    public MonitorDetailDto getMonitorDetailById(Long id) {
        return monitorMapper.getMonitorDetailById(id);
    }

    @Override
    public List<MonitorCount> getMonitorCountByMonitorType() {
        return monitorTypeMapper.getMonitorTypeCount();
    }

    @Override
    public void saveMonitorShowSetting(MonitorShowSetting monitorShowSetting) {
        EntityUtil.setCreate(monitorShowSetting);
        monitorShowSettingMapper.saveMonitorShowSetting(monitorShowSetting);
        monitorShowDataMapper.saveMonitorShowData(monitorShowSetting);
    }

    @Override
    public void updateMonitorShowSetting(Long id,MonitorShowSetting monitorShowSetting) {
        EntityUtil.setCreate(monitorShowSetting);
        monitorShowSettingMapper.updateById(id,monitorShowSetting);
        monitorShowDataMapper.deleteByShowSettingId(id);
        monitorShowDataMapper.saveMonitorShowData(monitorShowSetting);
    }

    @Override
    public void deleteMonitorShowSetting(Long id) {
        monitorShowSettingMapper.deleteById(id);
        monitorShowDataMapper.deleteByShowSettingId(id);
    }

    // todo 虽然功能赶出来了，但是现在回头看，代码像坨屎，大量的集合处理没有用stream，等我有时间要把这块重构   sign:liuzt  time: 2020/03/21
    @Override
    public void saveMonitor(MonitorDetailDto monitorDetailDto) {
        // 判断是否超出授权码限制，超出则抛出异常终止，没有超出，则更新授权码
        // 首先判断是否是启用状态，如果不是，则不进行如下判断
        if(monitorDetailDto.getIsValid() == 1){
            // 查出当前授权表
            MonitorLicense license = monitorLicenseMapper.findActiveEntity();
            // 假设添加成功，授权码实体变为什么样
            for(MonitorDetailTypeDto a:monitorDetailDto.getMonitorDetailType()){
                if(a.getMonitorTypeCode().equals("HARDWARE")) license.setUsedHardwareQuantity(license.getUsedHardwareQuantity() + 1);
                if(a.getMonitorTypeCode().equals("OS")) license.setUsedOsQuantity(license.getUsedOsQuantity() + 1);
                if(a.getMonitorTypeCode().equals("DATABASE")) license.setUsedDbQuantity(license.getUsedDbQuantity() + 1);
                if(a.getMonitorTypeCode().equals("MIDDLEWARE")) license.setUsedMiddlewareQuantity(license.getUsedMiddlewareQuantity() + 1);
                if(a.getMonitorTypeCode().equals("web")) license.setUsedWebQuantity(license.getUsedWebQuantity() + 1);
            }
            // 如果超出范围，抛出异常，如果没有超出范围，则再保存监控后，更新新的授权码实体
            if(license.getHardwareQuantity() < license.getUsedHardwareQuantity()) {
                throw ExceptionUtil.msg("硬件监控数量超过授权限制，请在授权数量范围内设置监控");
            }
            if(license.getOsQuantity() < license.getUsedOsQuantity()) {
                throw ExceptionUtil.msg("操作系统监控数量超过授权限制，请在授权数量范围内设置监控");
            }
            if(license.getDbQuantity() < license.getUsedDbQuantity()) {
                throw ExceptionUtil.msg("数据库监控数量超过授权限制，请在授权数量范围内设置监控");
            }
            if(license.getMiddlewareQuantity() < license.getUsedMiddlewareQuantity()) {
                throw ExceptionUtil.msg("中间件监控数量超过授权限制，请在授权数量范围内设置监控");
            }
            if(license.getWebQuantity() < license.getUsedWebQuantity()) {
                throw ExceptionUtil.msg("Web监控数量超过授权限制，请在授权数量范围内设置监控");
            }
        }
        // 构建适配器主机实体
        HostParam hostParam = new HostParam();
        // 真正添加进去的模板list
        List<String> realTempList = new ArrayList<>();
        // 适配器主机实体配置别名
        hostParam.setName(monitorDetailDto.getMonitorName());
        // 拿到顶级组织id
        Long topOrgId = SecurityUtil.getTopOrgId();
        // 构建Monitor实体并储存
        Monitor monitor;
        monitor = MapperUtil.copy(monitorDetailDto,Monitor.class);
        monitor.setOrgId(topOrgId);
        EntityUtil.setCreate(monitor);
        monitorMapper.saveMonitor(monitor);
        // 获取zabbix后台所有的模板
        List<Map<String, String>> templates = adapterService.getTemplates();
        // 获取监控子类型和监控方式map
        List<MonitorTypeSub> monitorTypeSubList = monitorTypeSubMapper.findAll();
        List<MonitorSubMethod> monitorSubMethodList = monitorSubMethodMapper.findAll();
        // 构建MonitorParam实体list并填充部分数据
        List<MonitorParam> list = new ArrayList<>();
        // 因为agent只能有一个，所以当有一个agent的时候后面再出现的agent就自动忽略
        Boolean agentFlag = true;
        for (MonitorDetailTypeDto monitorDetailTypeDto:
             monitorDetailDto.getMonitorDetailType()) {
            MonitorDetailType monitorDetailType = new MonitorDetailType();
            // 从监控子类型map里寻找对应的name并且赋值
            for (MonitorTypeSub mtsTemp:
                    monitorTypeSubList){
                if(mtsTemp.getId() == monitorDetailTypeDto.getMonitorTypeSubId()){
                    monitorDetailTypeDto.setMonitorTypeSubName(mtsTemp.getSubName());
                }
            }
            // 从监控方式map里寻找对应的name并且赋值
            for (MonitorSubMethod msmTemp:
                    monitorSubMethodList){
                if(msmTemp.getId() == monitorDetailTypeDto.getMonitorSubMethodId()){
                    monitorDetailTypeDto.setMonitorSubMethodName(msmTemp.getMethodName());
                }
            }
            // 拼接模板名
            String template = monitorDetailTypeDto.getMonitorTypeCode() + "_" + monitorDetailTypeDto.getMonitorTypeSubName() + "_" + monitorDetailTypeDto.getMonitorSubMethodName();
            // 硬件模板名需要拼接品牌名和设备code
            if(monitorDetailTypeDto.getMonitorTypeId() == 1){
                template = (monitorDetailDto.getBrandName() == null || monitorDetailDto.getBrandName().equals(""))? template:template + "_" + monitorDetailDto.getBrandName();
                template = (monitorDetailDto.getEquipmentMode() == null || monitorDetailDto.getEquipmentMode().equals(""))? template:template + "_" + monitorDetailDto.getEquipmentMode();
            }
            // 看有无匹配的模板，有的话就赋值
            for (Map<String,String> templateMap:
                    templates){
                if(templateMap.get("name").equals(template)){
                    monitorDetailType.setTemplateName(template);
                    monitorDetailType.setTemplateId(templateMap.get("templateid"));
                    realTempList.add(templateMap.get("templateid"));
                }
            }
            // 循环完成，没有匹配的模板就异常退出
            if(monitorDetailType.getTemplateId() == null || monitorDetailType.getTemplateId() == ""){
                throw ExceptionUtil.msg(String.format("没有与 %s 相匹配的模板.", template));
            }
            // 构建主机参数里的接口，每一种监控类型对应一个或零个，判断依据是interfaces里有没有ip
            Interfaces interfaces = new Interfaces();
            // 判断类型
            if(monitorDetailTypeDto.getMonitorSubMethodName().equals("AGENT"))interfaces.setType(1);
            if(monitorDetailTypeDto.getMonitorSubMethodName().equals("SNMP"))interfaces.setType(2);
            if(monitorDetailTypeDto.getMonitorSubMethodName().equals("JMX"))interfaces.setType(3);
            if(monitorDetailTypeDto.getMonitorSubMethodName().equals("IPMI"))interfaces.setType(4);
            monitorDetailType.setOrgId(topOrgId);
            monitorDetailType.setMonitorId(monitor.getId());
            monitorDetailType.setMonitorTypeId(monitorDetailTypeDto.getMonitorTypeId());
            monitorDetailType.setMonitorTypeSubId(monitorDetailTypeDto.getMonitorTypeSubId());
            monitorDetailType.setMonitorSubMethodId(monitorDetailTypeDto.getMonitorSubMethodId());
            if(monitorDetailDto.getBrand() != null){
                monitorDetailType.setBrand(monitorDetailDto.getBrand());
            }
            if(monitorDetailDto.getEquipmentMode() != null){
                monitorDetailType.setEquipmentMode(monitorDetailDto.getEquipmentMode());
            }
            monitorDetailType.setTemplateName("test_template");
            monitorDetailType.setSortNum(1L);
            EntityUtil.setCreate(monitorDetailType);
            monitorDetailTypeMapper.saveMonitorDetailType(monitorDetailType);
            if(monitorDetailTypeDto.getMonitorParam() != null){
                for (MonitorParamDto monitorParamDto:
                        monitorDetailTypeDto.getMonitorParam()) {
                    // 判断如果ParamCode为usernamae和password，则添加Implusername和Implpassword
                    if(monitorParamDto.getParamCode().equals("username")){
                        hostParam.setIpmiUsername(monitorParamDto.getParamValue());
                    }
                    if(monitorParamDto.getParamCode().equals("password")){
                        hostParam.setIpmiPassword(monitorParamDto.getParamValue());
                    }
                    // 因为主机名约定为ip地址，所以插入ip地址参数
                    if(monitorParamDto.getParamCode().equals("ip_address")){
                        hostParam.setHostName(monitorParamDto.getParamValue());
                        interfaces.setIp(monitorParamDto.getParamValue());
                        monitor.setIpAddress1(monitorParamDto.getParamValue());
                    }
                    // 插入interface里的port
                    if(monitorParamDto.getParamCode().equals("port")){
                        interfaces.setPort(monitorParamDto.getParamValue());
                    }
                    // 插入宏
                    if(monitorParamDto.getParamCode().equals("{$SNMP_COMMUNITY}")){
                        Macros macros = new Macros();
                        macros.setMacro("{$SNMP_COMMUNITY}");
                        macros.setValue(monitorParamDto.getParamValue());
                        hostParam.getMacrosList().add(macros);
                    }
                    if(monitorParamDto.getParamCode().equals("{$NGINX.STUB_STATUS.HOST}")){
                        Macros macros = new Macros();
                        macros.setMacro("{$NGINX.STUB_STATUS.HOST}");
                        macros.setValue(monitorParamDto.getParamValue());
                        hostParam.getMacrosList().add(macros);
                    }
                    if(monitorParamDto.getParamCode().equals("{$NGINX.STUB_STATUS.PORT}")){
                        Macros macros = new Macros();
                        macros.setMacro("{$NGINX.STUB_STATUS.PORT}");
                        macros.setValue(monitorParamDto.getParamValue());
                        hostParam.getMacrosList().add(macros);
                    }
                    if(monitorParamDto.getParamCode().equals("{$NGINX.STUB_STATUS.PATH}")){
                        Macros macros = new Macros();
                        macros.setMacro("{$NGINX.STUB_STATUS.PATH}");
                        macros.setValue(monitorParamDto.getParamValue());
                        hostParam.getMacrosList().add(macros);
                    }
                    MonitorParam monitorParam = new MonitorParam();
                    monitorParam.setOrgId(topOrgId);
                    monitorParam.setMonitorId(monitor.getId());
                    monitorParam.setParamId(monitorParamDto.getParamId());
                    monitorParam.setParamValue(monitorParamDto.getParamValue());
                    EntityUtil.setCreate(monitorParam);
                    monitorParam.setEquipmentMonitorType(monitorDetailType.getId());
                    list.add(monitorParam);
                }
            }

            // 如果interface里有ip 说明是添加interface，并且判断是否之前已有agent被添加进去了。
            if(interfaces.getIp() != null && interfaces.getIp() != "" && agentFlag){
                hostParam.getInterfacesList().add(interfaces);
                agentFlag = false;
            }
        }
        // 去重以后，添加真正的templist
        realTempList = realTempList.stream().distinct().collect(Collectors.toList());
        hostParam.setTemplateList(realTempList);
        // 调用适配器添加主机接口
        log.info(JSONObject.toJSON(hostParam).toString());
        Map map = adapterService.saveHost(JSONObject.toJSON(hostParam));
        log.info(map.toString());
        if(Integer.valueOf(map.get("code").toString()) != 0){
            throw ExceptionUtil.msg(map.get("message").toString());
        }
        monitor.setHostid(map.get("data").toString());
        monitorMapper.updateMonitor(monitor);
        monitorParamMapper.saveMonitorParam(list);
        // 最后，判断主机是否在禁用，如果禁用，则再发一个请求禁用监控
        if(monitor.getIsValid() == 0) {
            adapterService.forbidHost(monitor.getHostid());
        }
        updateLicense();
    }

    @Override
    public void updateMonitor(MonitorDetailDto monitorDetailDto) {
        //————————————————————老方法别删————————————
        // 拿到顶级组织id
//        Long topOrgId = SecurityUtil.getTopOrgId();
//        String username = SecurityUtil.getUsername();
//        Long userId = SecurityUtil.getUserId();
//        // 构建Monitor实体并储存
//        Monitor monitor;
//        monitor = MapperUtil.copy(monitorDetailDto,Monitor.class);
//        System.out.println(monitor.getIsValid());
//        monitor.setOrgId(topOrgId);
//        EntityUtil.setUpdate(monitor);
//        monitorMapper.updateMonitor(monitor);
//        // 获取该监控id下所有的监控类型id
//        List<Long> oldList = monitorDetailTypeMapper.getIdListByMonitorId(monitor.getId());
//        List<Long> idList = new ArrayList<>();
//        // 新增监控参数list
//        List<MonitorParam> newMonitorParamList = new ArrayList<>();
//        // 比较MonitorDetailType
//        for (MonitorDetailTypeDto monitorDetailTypeDto:
//             monitorDetailDto.getMonitorDetailType()) {
//            MonitorDetailType monitorDetailType = new MonitorDetailType();
//            monitorDetailType.setOrgId(topOrgId);
//            monitorDetailType.setMonitorId(monitor.getId());
//            monitorDetailType.setMonitorTypeId(monitorDetailTypeDto.getMonitorTypeId());
//            monitorDetailType.setMonitorTypeSubId(monitorDetailTypeDto.getMonitorTypeSubId());
//            monitorDetailType.setMonitorSubMethodId(monitorDetailTypeDto.getMonitorSubMethodId());
//            if(monitorDetailDto.getBrand() != null){
//                monitorDetailType.setBrand(monitorDetailDto.getBrand());
//            }
//            if(monitorDetailDto.getEquipmentMode() != null){
//                monitorDetailType.setEquipmentMode(monitorDetailDto.getEquipmentMode());
//            }
//            monitorDetailType.setTemplateName("test_template");
//            monitorDetailType.setSortNum(1L);
//            monitorDetailType.setHostId(10001L);
//            // 如果没有id则增加有id则更新
//            if(monitorDetailTypeDto.getId() == null){
//                EntityUtil.setCreate(monitorDetailType);
//                monitorDetailTypeMapper.saveMonitorDetailType(monitorDetailType);
//                if(monitorDetailTypeDto.getMonitorParam() != null){
//                    for (MonitorParamDto monitorParamDto:
//                            monitorDetailTypeDto.getMonitorParam()) {
//                        MonitorParam monitorParam = new MonitorParam();
//                        monitorParam.setOrgId(topOrgId);
//                        monitorParam.setMonitorId(monitor.getId());
//                        monitorParam.setParamId(monitorParamDto.getParamId());
//                        monitorParam.setParamValue(monitorParamDto.getParamValue());
//                        EntityUtil.setCreate(monitorParam);
//                        monitorParam.setEquipmentMonitorType(monitorDetailType.getId());
//                        newMonitorParamList.add(monitorParam);
//                    }
//                }
//            }else {
//                idList.add(monitorDetailTypeDto.getId());
//                monitorDetailType.setId(monitorDetailTypeDto.getId());
//                EntityUtil.setUpdate(monitorDetailType);
//                monitorDetailTypeMapper.updateMonitorDetailType(monitorDetailType);
//                // 更新MonitorDetailType后再判断MonitorParam有没有
//                // 如果没有，则删除原有的MonitorParam
//                if(monitorDetailTypeDto.getMonitorParam() == null || monitorDetailTypeDto.getMonitorParam().size() == 0){
//                    List<Long> tempList = new ArrayList<>();
//                    tempList.add(monitorDetailType.getId());
//                    monitorParamMapper.deleteByMonitorDetailTypeId(tempList,userId,username);
//                }else {
//                    // 如果有，再判断没有有id
//                    for (MonitorParamDto monitorParamDto :
//                            monitorDetailTypeDto.getMonitorParam()) {
//                        // 如果有id，则更新值，如果没有则删除原有值并且把新值加到newMonitorParamList
//                        if(monitorParamDto.getId() != null){
//                            monitorParamMapper.updateMonitorParam(monitorParamDto.getId(),monitorParamDto.getParamValue());
//                        }else {
//                            List<Long> tempList = new ArrayList<>();
//                            tempList.add(monitorDetailType.getId());
//                            monitorParamMapper.deleteByMonitorDetailTypeId(tempList,userId,username);
//                            MonitorParam monitorParam = new MonitorParam();
//                            monitorParam.setOrgId(topOrgId);
//                            monitorParam.setMonitorId(monitor.getId());
//                            monitorParam.setParamId(monitorParamDto.getParamId());
//                            monitorParam.setParamValue(monitorParamDto.getParamValue());
//                            EntityUtil.setCreate(monitorParam);
//                            monitorParam.setEquipmentMonitorType(monitorDetailType.getId());
//                            newMonitorParamList.add(monitorParam);
//                        }
//                    }
//                }
//            }
//        }
//        // 处理新增的monitorParam
//        if(newMonitorParamList != null & newMonitorParamList.size() != 0){
//            monitorParamMapper.saveMonitorParam(newMonitorParamList);
//        }
//        // 比较新旧idlist新的没有而旧的有说明要删除
//        List<Long> deleteList = new ArrayList<>();
//        for (Long id:
//             oldList) {
//            if(!idList.contains(id)){
//                deleteList.add(id);
//            }
//        }
//        if (deleteList != null && deleteList.size() != 0){
//            monitorDetailTypeMapper.deleteById(deleteList,userId,username);
//            monitorParamMapper.deleteByMonitorDetailTypeId(deleteList,userId,username);
//        }
        //——————————————————老方法别删——————————————————

        // ——————————————重新保存————————————————
        // 判断是否超出授权码限制，超出则抛出异常终止，没有超出，则更新授权码
        // 首先判断是否是启用状态，如果不是，则不进行如下判断
        if(monitorDetailDto.getIsValid() == 1){
            // 查出当前授权表
            MonitorLicense license = monitorLicenseMapper.findActiveEntity();
            // 减去原来的授权数，加上新增的，就是最终的
            List<MonitorCount> monitorTypeCountById = monitorTypeMapper.getMonitorTypeCountById(monitorDetailDto.getId());
            for(MonitorCount monitorCount:monitorTypeCountById){
                if(monitorCount.getMonitorTypeName().equals("硬件")){
                    license.setUsedHardwareQuantity(license.getUsedHardwareQuantity() - monitorCount.getCount());
                }
                if(monitorCount.getMonitorTypeName().equals("操作系统")){
                    license.setUsedOsQuantity(license.getUsedOsQuantity() - monitorCount.getCount());
                }
                if(monitorCount.getMonitorTypeName().equals("数据库")){
                    license.setUsedDbQuantity(license.getUsedDbQuantity() - monitorCount.getCount());
                }
                if(monitorCount.getMonitorTypeName().equals("中间件")){
                    license.setUsedMiddlewareQuantity(license.getUsedMiddlewareQuantity() - monitorCount.getCount());
                }
                if(monitorCount.getMonitorTypeName().equals("web")){
                    license.setUsedWebQuantity(license.getUsedWebQuantity() - 1);
                }
            }
            // 假设添加成功，授权码实体变为什么样
            for(MonitorDetailTypeDto a:monitorDetailDto.getMonitorDetailType()){
                if(a.getMonitorTypeCode().equals("HARDWARE")) license.setUsedHardwareQuantity(license.getUsedHardwareQuantity() + 1);
                if(a.getMonitorTypeCode().equals("OS")) license.setUsedOsQuantity(license.getUsedOsQuantity() + 1);
                if(a.getMonitorTypeCode().equals("DATABASE")) license.setUsedDbQuantity(license.getUsedDbQuantity() + 1);
                if(a.getMonitorTypeCode().equals("MIDDLEWARE")) license.setUsedMiddlewareQuantity(license.getUsedMiddlewareQuantity() + 1);
                if(a.getMonitorTypeCode().equals("web")) license.setUsedWebQuantity(license.getUsedWebQuantity() + 1);
            }
            // 如果超出范围，抛出异常，如果没有超出范围，则再保存监控后，更新新的授权码实体
            if(license.getHardwareQuantity() < license.getUsedHardwareQuantity()) {
                throw ExceptionUtil.msg("硬件监控数量超过授权限制，请在授权数量范围内设置监控");
            }
            if(license.getOsQuantity() < license.getUsedOsQuantity()) {
                throw ExceptionUtil.msg("操作系统监控数量超过授权限制，请在授权数量范围内设置监控");
            }
            if(license.getDbQuantity() < license.getUsedDbQuantity()) {
                throw ExceptionUtil.msg("数据库监控数量超过授权限制，请在授权数量范围内设置监控");
            }
            if(license.getMiddlewareQuantity() < license.getUsedMiddlewareQuantity()) {
                throw ExceptionUtil.msg("中间件监控数量超过授权限制，请在授权数量范围内设置监控");
            }
            if(license.getWebQuantity() < license.getUsedWebQuantity()) {
                throw ExceptionUtil.msg("Web监控数量超过授权限制，请在授权数量范围内设置监控");
            }
        }
        // 构建适配器主机实体
        HostParam hostParam = new HostParam();
        // 获取之前的hostid放入适配器主机实体
        Monitor preMonitor = monitorMapper.getById(monitorDetailDto.getId());
        String oldHostId = preMonitor.getHostid();
        // 获取之前的templateIdList
        hostParam.setTemplateOldList(monitorDetailTypeMapper.getTemplateIdListByMonitorId(monitorDetailDto.getId()));
        // 真正添加进去的模板list
        List<String> realTempList = new ArrayList<>();
        // 适配器主机实体配置别名
        hostParam.setName(monitorDetailDto.getMonitorName());
        // 拿到顶级组织id
        Long topOrgId = SecurityUtil.getTopOrgId();
        // 构建Monitor实体并储存
        Monitor monitor;
        monitor = MapperUtil.copy(monitorDetailDto,Monitor.class);
        monitor.setOrgId(topOrgId);
        EntityUtil.setCreate(monitor);
        monitorMapper.saveMonitor(monitor);
        // 获取zabbix后台所有的模板
        List<Map<String, String>> templates = adapterService.getTemplates();
        // 获取监控子类型和监控方式map
        List<MonitorTypeSub> monitorTypeSubList = monitorTypeSubMapper.findAll();
        List<MonitorSubMethod> monitorSubMethodList = monitorSubMethodMapper.findAll();
        // 构建MonitorParam实体list并填充部分数据
        List<MonitorParam> list = new ArrayList<>();
        // 因为agent只能有一个，所以当有一个agent的时候后面再出现的agent就自动忽略,并且判断是否之前已有agent被添加进去了。
        Boolean agentFlag = true;
        for (MonitorDetailTypeDto monitorDetailTypeDto:
                monitorDetailDto.getMonitorDetailType()) {
            MonitorDetailType monitorDetailType = new MonitorDetailType();
            // 从监控子类型map里寻找对应的name并且赋值
            for (MonitorTypeSub mtsTemp:
                    monitorTypeSubList){
                if(mtsTemp.getId() == monitorDetailTypeDto.getMonitorTypeSubId()){
                    monitorDetailTypeDto.setMonitorTypeSubName(mtsTemp.getSubName());
                }
            }
            // 从监控方式map里寻找对应的name并且赋值
            for (MonitorSubMethod msmTemp:
                    monitorSubMethodList){
                if(msmTemp.getId() == monitorDetailTypeDto.getMonitorSubMethodId()){
                    monitorDetailTypeDto.setMonitorSubMethodName(msmTemp.getMethodName());
                }
            }
            // 拼接模板名
            String template = monitorDetailTypeDto.getMonitorTypeCode() + "_" + monitorDetailTypeDto.getMonitorTypeSubName() + "_" + monitorDetailTypeDto.getMonitorSubMethodName();
            // 硬件模板名需要拼接品牌名和设备code
            if(monitorDetailTypeDto.getMonitorTypeId() == 1){
                template = (monitorDetailDto.getBrandName() == null || monitorDetailDto.getBrandName().equals(""))? template:template + "_" + monitorDetailDto.getBrandName();
                template = (monitorDetailDto.getEquipmentMode() == null || monitorDetailDto.getEquipmentMode().equals(""))? template:template + "_" + monitorDetailDto.getEquipmentMode();
            }
            log.info(templates.toString());
            // 看有无匹配的模板，有的话就赋值
            for (Map<String,String> templateMap:
                    templates){
                if(templateMap.get("name").equals(template)){
                    monitorDetailType.setTemplateName(template);
                    monitorDetailType.setTemplateId(templateMap.get("templateid"));
                    realTempList.add(templateMap.get("templateid"));
                }
            }
            // 循环完成，没有匹配的模板就异常退出
            if(monitorDetailType.getTemplateId() == null || monitorDetailType.getTemplateId() == ""){
                throw ExceptionUtil.msg(String.format("没有与 %s 相匹配的模板.", template));
            }
            // 构建主机参数里的接口，每一种监控类型对应一个或零个，判断依据是interfaces里有没有ip
            Interfaces interfaces = new Interfaces();
            // 判断类型
            if(monitorDetailTypeDto.getMonitorSubMethodName().equals("AGENT"))interfaces.setType(1);
            if(monitorDetailTypeDto.getMonitorSubMethodName().equals("SNMP"))interfaces.setType(2);
            if(monitorDetailTypeDto.getMonitorSubMethodName().equals("JMX"))interfaces.setType(3);
            if(monitorDetailTypeDto.getMonitorSubMethodName().equals("IPMI"))interfaces.setType(4);
            monitorDetailType.setOrgId(topOrgId);
            monitorDetailType.setMonitorId(monitor.getId());
            monitorDetailType.setMonitorTypeId(monitorDetailTypeDto.getMonitorTypeId());
            monitorDetailType.setMonitorTypeSubId(monitorDetailTypeDto.getMonitorTypeSubId());
            monitorDetailType.setMonitorSubMethodId(monitorDetailTypeDto.getMonitorSubMethodId());
            if(monitorDetailDto.getBrand() != null){
                monitorDetailType.setBrand(monitorDetailDto.getBrand());
            }
            if(monitorDetailDto.getEquipmentMode() != null){
                monitorDetailType.setEquipmentMode(monitorDetailDto.getEquipmentMode());
            }
            monitorDetailType.setTemplateName("test_template");
            monitorDetailType.setSortNum(1L);
            EntityUtil.setCreate(monitorDetailType);
            monitorDetailTypeMapper.saveMonitorDetailType(monitorDetailType);
            if(monitorDetailTypeDto.getMonitorParam() != null){
                for (MonitorParamDto monitorParamDto:
                        monitorDetailTypeDto.getMonitorParam()) {
                    // 判断如果ParamCode为usernamae和password，则添加Implusername和Implpassword
                    if(monitorParamDto.getParamCode().equals("username")){
                        hostParam.setIpmiUsername(monitorParamDto.getParamValue());
                    }
                    if(monitorParamDto.getParamCode().equals("password")){
                        hostParam.setIpmiPassword(monitorParamDto.getParamValue());
                    }
                    // 因为主机名约定为ip地址，所以插入ip地址参数
                    if(monitorParamDto.getParamCode().equals("ip_address")){
                        hostParam.setHostName(monitorParamDto.getParamValue());
                        interfaces.setIp(monitorParamDto.getParamValue());
                        monitor.setIpAddress1(monitorParamDto.getParamValue());
                    }
                    // 插入interface里的port
                    if(monitorParamDto.getParamCode().equals("port")){
                        interfaces.setPort(monitorParamDto.getParamValue());
                    }
                    // 插入宏
                    if(monitorParamDto.getParamCode().equals("{$SNMP_COMMUNITY}")){
                        Macros macros = new Macros();
                        macros.setMacro("{$SNMP_COMMUNITY}");
                        macros.setValue(monitorParamDto.getParamValue());
                        hostParam.getMacrosList().add(macros);
                    }
                    if(monitorParamDto.getParamCode().equals("{$NGINX.STUB_STATUS.HOST}")){
                        Macros macros = new Macros();
                        macros.setMacro("{$NGINX.STUB_STATUS.HOST}");
                        macros.setValue(monitorParamDto.getParamValue());
                        hostParam.getMacrosList().add(macros);
                    }
                    if(monitorParamDto.getParamCode().equals("{$NGINX.STUB_STATUS.PORT}")){
                        Macros macros = new Macros();
                        macros.setMacro("{$NGINX.STUB_STATUS.PORT}");
                        macros.setValue(monitorParamDto.getParamValue());
                        hostParam.getMacrosList().add(macros);
                    }
                    if(monitorParamDto.getParamCode().equals("{$NGINX.STUB_STATUS.PATH}")){
                        Macros macros = new Macros();
                        macros.setMacro("{$NGINX.STUB_STATUS.PATH}");
                        macros.setValue(monitorParamDto.getParamValue());
                        hostParam.getMacrosList().add(macros);
                    }
                    MonitorParam monitorParam = new MonitorParam();
                    monitorParam.setOrgId(topOrgId);
                    monitorParam.setMonitorId(monitor.getId());
                    monitorParam.setParamId(monitorParamDto.getParamId());
                    monitorParam.setParamValue(monitorParamDto.getParamValue());
                    EntityUtil.setCreate(monitorParam);
                    monitorParam.setEquipmentMonitorType(monitorDetailType.getId());
                    list.add(monitorParam);
                }
            }

            // 如果interface里有ip 说明是添加interface，那么假如hostParam中
            if(interfaces.getIp() != null && interfaces.getIp() != "" && agentFlag){
                hostParam.getInterfacesList().add(interfaces);
                agentFlag = false;
            }
        }
        // 去重以后，添加真正的templist
        realTempList = realTempList.stream().distinct().collect(Collectors.toList());
        hostParam.setTemplateList(realTempList);
        // 调用适配器添加主机接口
        log.info(JSONObject.toJSON(hostParam).toString());
        Map map = adapterService.updateHost(Integer.valueOf(oldHostId),JSONObject.toJSON(hostParam));
        log.info(map.toString());
        if(Integer.valueOf(map.get("code").toString()) != 0){
            throw ExceptionUtil.msg(map.get("message").toString());
        }
        monitor.setHostid(oldHostId);
        monitorMapper.updateMonitor(monitor);
        monitorParamMapper.saveMonitorParam(list);
        // ——————————————重新保存————————————————
        // 最后，判断主机是否在禁用，如果禁用，则再发一个请求禁用监控
        if(monitor.getIsValid() == 0) {
            adapterService.forbidHost(monitor.getHostid());
        }
        if(monitor.getIsValid() == 1) {
            adapterService.unforbidHost(monitor.getHostid());
        }
        // 更新showSetting表和showData表的monitorid为新的
        monitorShowSettingMapper.updateMonitorId(monitorDetailDto.getId(),monitor.getId());
        monitorShowDataMapper.updateMonitorId(monitorDetailDto.getId(),monitor.getId());
        // 删除之前的monitor和monitorDetailType
        monitorMapper.deleteById(monitorDetailDto.getId());
        monitorDetailTypeMapper.delelteByMonitorId(monitorDetailDto.getId());
    }

    @Override
    public void forbidMonitor(Long id) {
        // 对适配器发送对应请求
        Map map = adapterService.forbidHost(monitorMapper.getById(id).getHostid());
        // 根据返回的code是否为0判断是否禁用成功，如果失败，则抛出适配器异常
        if(Integer.valueOf(map.get("code").toString()) != 0){
            throw ExceptionUtil.msg(map.get("message").toString());
        }
        monitorMapper.forbidMonitor(id);
        // 更新授权码表
        updateLicense();
    }

    @Override
    public void unforbidMonitor(Long id) {
        // 查出当前授权表
        MonitorLicense license = monitorLicenseMapper.findActiveEntity();
        // 加上当前监控的数量用于检测数量是否超出范围
        List<MonitorCount> monitorTypeCountById = monitorTypeMapper.getMonitorTypeCountById(id);
        for(MonitorCount monitorCount:monitorTypeCountById){
            if(monitorCount.getMonitorTypeName().equals("硬件")){
                license.setUsedHardwareQuantity(license.getUsedHardwareQuantity() + monitorCount.getCount());
            }
            if(monitorCount.getMonitorTypeName().equals("操作系统")){
                license.setUsedOsQuantity(license.getUsedOsQuantity() + monitorCount.getCount());
            }
            if(monitorCount.getMonitorTypeName().equals("数据库")){
                license.setUsedDbQuantity(license.getUsedDbQuantity() + monitorCount.getCount());
            }
            if(monitorCount.getMonitorTypeName().equals("中间件")){
                license.setUsedMiddlewareQuantity(license.getUsedMiddlewareQuantity() + monitorCount.getCount());
            }
            if(monitorCount.getMonitorTypeName().equals("web")){
                license.setUsedWebQuantity(license.getUsedWebQuantity() + monitorCount.getCount());
            }
        }
        // 如果超出范围，抛出异常，如果没有超出范围，则再保存监控后，更新新的授权码实体
        if(license.getHardwareQuantity() < license.getUsedHardwareQuantity()) {
            throw ExceptionUtil.msg("硬件监控数量超过授权限制，请在授权数量范围内设置监控");
        }
        if(license.getOsQuantity() < license.getUsedOsQuantity()) {
            throw ExceptionUtil.msg("操作系统监控数量超过授权限制，请在授权数量范围内设置监控");
        }
        if(license.getDbQuantity() < license.getUsedDbQuantity()) {
            throw ExceptionUtil.msg("数据库监控数量超过授权限制，请在授权数量范围内设置监控");
        }
        if(license.getMiddlewareQuantity() < license.getUsedMiddlewareQuantity()) {
            throw ExceptionUtil.msg("中间件监控数量超过授权限制，请在授权数量范围内设置监控");
        }
        if(license.getWebQuantity() < license.getUsedWebQuantity()) {
            throw ExceptionUtil.msg("Web监控数量超过授权限制，请在授权数量范围内设置监控");
        }
        // 对适配器发送对应请求
        Map map = adapterService.unforbidHost(monitorMapper.getById(id).getHostid());
        // 根据返回的code是否为0判断是否禁用成功，如果失败，则抛出适配器异常
        if(Integer.valueOf(map.get("code").toString()) != 0){
            throw ExceptionUtil.msg(map.get("message").toString());
        }
        monitorMapper.unforbidMonitor(id);
        // 更新授权码表
        updateLicense();
    }

    @Override
    public void deleteMonitor(Long id) {
        // 对适配器发送对应请求
        Map map = adapterService.deleteHost(monitorMapper.getById(id).getHostid());
        // 根据返回的code是否为0判断是否禁用成功，如果失败，则抛出适配器异常
        if(Integer.valueOf(map.get("code").toString()) != 0){
            throw ExceptionUtil.msg(map.get("message").toString());
        }
        Monitor monitor = new Monitor();
        monitor.setId(id);
        EntityUtil.setDelete(monitor);
        monitorMapper.deleteMonitor(monitor);
        monitorDetailTypeMapper.delelteByMonitorId(id);
        monitorParamMapper.delelteByMonitorId(id);
        monitorShowSettingMapper.delelteByMonitorId(id);
        monitorShowDataMapper.delelteByMonitorId(id);
        // 更新授权码表
        updateLicense();
    }

    @Override
    public MonitorShowSetting getMonitorShowSettingById(Long id) {
        return monitorShowSettingMapper.getMonitorShowSettingById(id);
    }

    @Override
    public List<MonitorTypeMaster> getMonitorTypeByMonitorId(Long id) {
        return monitorMapper.getMonitorTypeByMonitorId(id);
    }

    @Override
    public List<MonitorShowSetting> getMonitorShowSetting(Long id, Long typeId) {
        return monitorShowSettingMapper.getMonitorShowSetting(id,typeId);
    }

    @Override
    public MonitorDetailSettingPageDto getMonitorDetailSettingPageById(Long id) {
        //获取信息
        MonitorDetailSettingPageDto monitorDetail = monitorMapper.getMonitorDetailSettingPageById(id);
        //根据hostid获取itemlist
        Map itemsResp = adapterService.getItemsByKey(monitorDetail.getHostid());
        //处理itemlist
        List<Map<String,String>> data = (List<Map<String,String>>) itemsResp.get("data");
        List<ItemDto> itemList = new ArrayList<>();
        for (Map<String,String> map :
                data) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemId(map.get("itemid"));
            itemDto.setItemName(map.get("name"));
            itemList.add(itemDto);
        }
        monitorDetail.setItemList(itemList);
        return monitorDetail;
    }

    private void updateLicense(){
        // 获取当前授权码
        MonitorLicense license = monitorLicenseMapper.findActiveEntity();
        // 更新监控授权码
        // 统计现在的个数
        List<MonitorCount> monitorTypeCount = monitorTypeMapper.getMonitorTypeCount();
        // 构建更新实体
        MonitorLicense licenseCount = new MonitorLicense();
        licenseCount.setId(license.getId());
        licenseCount.setUsedHardwareQuantity(0L);
        licenseCount.setUsedOsQuantity(0L);
        licenseCount.setUsedDbQuantity(0L);
        licenseCount.setUsedMiddlewareQuantity(0L);
        licenseCount.setUsedWebQuantity(0L);
        // 放入新的个数
        for(MonitorCount monitorCount:monitorTypeCount){
            if(monitorCount.getMonitorTypeName().equals("硬件")){
                licenseCount.setUsedHardwareQuantity(Long.valueOf(monitorCount.getCount()));
            }
            if(monitorCount.getMonitorTypeName().equals("操作系统")){
                licenseCount.setUsedOsQuantity(Long.valueOf(monitorCount.getCount()));
            }
            if(monitorCount.getMonitorTypeName().equals("数据库")){
                licenseCount.setUsedDbQuantity(Long.valueOf(monitorCount.getCount()));
            }
            if(monitorCount.getMonitorTypeName().equals("中间件")){
                licenseCount.setUsedMiddlewareQuantity(Long.valueOf(monitorCount.getCount()));
            }
            if(monitorCount.getMonitorTypeName().equals("web")){
                licenseCount.setUsedWebQuantity(Long.valueOf(monitorCount.getCount()));
            }
        }
        // 更新授权码表
        monitorLicenseMapper.updateMonitorCount(licenseCount);
    }
}
