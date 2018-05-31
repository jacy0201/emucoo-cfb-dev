package com.emucoo.service.task.imp;

import com.emucoo.mapper.TRepairWorkMapper;
import com.emucoo.model.TRepairWork;
import com.emucoo.service.task.TaskRepairService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TaskRepairServiceImpl implements TaskRepairService {

    @Autowired
    private TRepairWorkMapper repairWorkMapper;

    @Override
    public TRepairWork detail(long workId) {
        TRepairWork work = repairWorkMapper.selectByPrimaryKey(workId);

        return work;
    }

    @Override
    public List<TRepairWork> listRepairWorksByShopId(Long shopId) {
        List<TRepairWork> works = repairWorkMapper.fetchWorksByShopId();
        return works;
    }
}
