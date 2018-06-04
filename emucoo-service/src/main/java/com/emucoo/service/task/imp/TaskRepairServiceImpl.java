package com.emucoo.service.task.imp;

import com.emucoo.mapper.TRepairWorkMapper;
import com.emucoo.model.TDeviceType;
import com.emucoo.model.TRepairWork;
import com.emucoo.service.task.TaskRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    @Override
    public void createRepairWork(TRepairWork work) {

    }

    @Override
    public void modifyRepairWork(TRepairWork work) {

    }

    @Override
    public void finishRepairWork(TRepairWork work) {

    }

    @Override
    public void expectRepairDate(TRepairWork work) {

    }

    @Override
    public void auditRepairWork(TRepairWork work) {

    }

    @Override
    public List<TDeviceType> listDeviceTypes(String keyword, long typeId, int pageNm, int pageSz) {
        return null;
    }

    @Override
    public int countDeviceTypes(String keyword, long typeId) {
        return 0;
    }

    @Override
    public void saveDeviceType(TDeviceType dvc) {

    }

    @Override
    public void configDeviceCategory(TDeviceType dvc) {

    }

    @Override
    public void attachDeviceProblem(TDeviceType dvc) {

    }
}
