package com.wejoyclass.itops.local.service.impl;

import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.util.ExceptionUtil;
import com.wejoyclass.core.util.MapperUtil;
import com.wejoyclass.core.util.PageUtil;
import com.wejoyclass.core.util.StringUtil;
import com.wejoyclass.itops.local.dto.EquipmentDto;
import com.wejoyclass.itops.local.dto.EquipmentQueryParam;
import com.wejoyclass.itops.local.entity.Equipment;
import com.wejoyclass.itops.local.mapper.EquipmentMapper;
import com.wejoyclass.itops.local.mapper.MonitorMapper;
import com.wejoyclass.itops.local.service.EquipmentService;
import com.wejoyclass.service.impl.BaseCURDServiceImpl;
import com.wejoyclass.service.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional
public class EquipmentServiceImpl extends BaseCURDServiceImpl<Equipment, Long> implements EquipmentService {

    @Autowired
    EquipmentMapper equipmentMapper;

    @Autowired
    MonitorMapper monitorMapper;

    @Override
    public void isValidCode(String equipmentCode, Long excludedId) {
        if(StringUtils.hasText(equipmentCode)){
            Long orgId = SecurityUtil.getTopOrgId();
            Equipment foundEquipment = equipmentMapper.getEquipmentByCode(equipmentCode, orgId, excludedId);
            if(foundEquipment != null){
                throw ExceptionUtil.msg(String.format("设备编号[%s]已存在，请重新输入！", equipmentCode));
            }
        }
    }

    @Override
    public void saveEquipment(EquipmentDto newEquipmentDto) {
        Equipment newEquipment = this.convertToModel(newEquipmentDto);

        this.isValidCode(newEquipment.getEquipmentCode(), 0L);

        try{
            this.insert(newEquipment);
        }catch (Exception ex){
            String errMsg = "设备添加失败！";
            throw ExceptionUtil.unchecked(errMsg, ex);
        }
    }

    @Override
    public Equipment getById(Long id){
        return equipmentMapper.getById(id);
    }

    @Override
    public void updateEquipment(EquipmentDto newEquipmentDto) {
        Equipment newEquipment = this.convertToModel(newEquipmentDto);

        Equipment exitingEquipment = this.get(newEquipment.getId());
        if (exitingEquipment == null) {
            throw ExceptionUtil.msg(String.format("id为[%s]的设备没有找到！", newEquipment.getId()));
        }

        if (StringUtils.hasText(newEquipment.getEquipmentCode()) && !newEquipment.getEquipmentCode().equals(exitingEquipment.getEquipmentCode())) {
            Long orgId = SecurityUtil.getTopOrgId();
            Equipment foundEquipment = equipmentMapper.getEquipmentByCode(newEquipment.getEquipmentCode(), orgId, 0L);
            if (foundEquipment != null) {
                throw ExceptionUtil.msg(String.format("设备编号[%s]已存在，请重新输入！", newEquipment.getEquipmentCode()));
            }
        }

        MapperUtil.copy(newEquipment, exitingEquipment);

        try {
            this.update(exitingEquipment);
        } catch (Exception ex) {
            String errMsg = "修改设备失败！";
            throw ExceptionUtil.unchecked(errMsg, ex);
        }

    }

    @Override
    public void deleteById(Long id){
        Equipment exitingEquipment = this.get(id);
        if (exitingEquipment == null) {
            throw ExceptionUtil.msg(String.format("id为[%s]的设备没有找到！", id));
        }

        Integer monitorNum = monitorMapper.countMonitorByEquipmentId(id);
        if (monitorNum > 0) {
            throw ExceptionUtil.msg("当前设备正在被监控，请先删除监控！");
        }

        try {
            this.deleteLogic(exitingEquipment);
        } catch (Exception ex) {
            String errMsg = "删除设备失败！";
            throw ExceptionUtil.unchecked(errMsg, ex);
        }

    }

    @Override
    public List<Equipment> findEquipmentList(EquipmentQueryParam params) {
        StringUtil.encodeSqlLike(params, "equipmentCode");
        params.setOrgId(SecurityUtil.getTopOrgId());
        return equipmentMapper.findEquipmentList(params);
    }

    @Override
    public Page<Equipment> findEquipmentPage(QueryParameter queryParameter, EquipmentQueryParam params) {
        return PageUtil.process(queryParameter, () -> {
            return this.findEquipmentList(params);
        });
    }

    private EquipmentDto convertToDto(Equipment equipment) {
        return MapperUtil.map(equipment, EquipmentDto.class);
    }

    private Equipment convertToModel(EquipmentDto equipmentDto) {
        return MapperUtil.map(equipmentDto, Equipment.class);
    }
}
