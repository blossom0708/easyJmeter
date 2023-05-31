package io.github.guojiaxing1995.easyJmeter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.guojiaxing1995.easyJmeter.common.mybatis.Page;
import io.github.guojiaxing1995.easyJmeter.dto.machine.CreateOrUpdateMachineDTO;
import io.github.guojiaxing1995.easyJmeter.mapper.MachineMapper;
import io.github.guojiaxing1995.easyJmeter.model.MachineDO;
import io.github.guojiaxing1995.easyJmeter.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    private MachineMapper machineMapper;

    @Override
    public IPage<MachineDO> getMachineByName(Integer current, String name) {
        Page page = new Page(current, 10);
        IPage<MachineDO> machines = machineMapper.selectByName(page, name);
        return machines;
    }

    @Override
    public boolean createMachine(CreateOrUpdateMachineDTO validator) {
        MachineDO machineDO = new MachineDO();
        machineDO.setName(validator.getName());
        machineDO.setAddress(validator.getAddress());
        return machineMapper.insert(machineDO) > 0;
    }

    @Override
    public boolean updateMachine(MachineDO machine, CreateOrUpdateMachineDTO validator) {
        machine.setName(validator.getName());
        machine.setAddress(validator.getAddress());
        return machineMapper.updateById(machine) > 0;
    }

    @Override
    public MachineDO getById(Integer id) {
        return machineMapper.selectById(id);
    }

    @Override
    public boolean deleteMachine(Integer id) {
        return machineMapper.deleteById(id) > 0;
    }
}