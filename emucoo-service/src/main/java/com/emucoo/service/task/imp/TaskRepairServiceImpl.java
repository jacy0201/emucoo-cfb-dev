package com.emucoo.service.task.imp;

import com.emucoo.mapper.TDeviceTypeMapper;
import com.emucoo.mapper.TRepairWorkMapper;
import com.emucoo.model.TDeviceProblem;
import com.emucoo.model.TDeviceType;
import com.emucoo.model.TRepairWork;
import com.emucoo.service.task.TaskRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskRepairServiceImpl implements TaskRepairService {

    @Autowired
    private TRepairWorkMapper repairWorkMapper;

    @Autowired
    private TDeviceTypeMapper deviceTypeMapper;

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
        return deviceTypeMapper.filterDeviceTypesByKeyword(keyword, typeId, (pageNm - 1) * pageSz, pageSz);
    }

    @Override
    public int countDeviceTypes(String keyword, long typeId) {
        return deviceTypeMapper.countDeviceTypesByKeyword(keyword, typeId);
    }

    @Override
    @Transactional
    public void saveDeviceType(TDeviceType dvc) {
        if(dvc.getId() == 0) {
            deviceTypeMapper.insert(dvc);
        } else {
            deviceTypeMapper.updateByPrimaryKeySelective(dvc);
        }
    }

    private List<TDeviceType> extractChildrenType(TDeviceType dvc) {
        List<TDeviceType> list = new ArrayList<>();
        List<TDeviceType> children = dvc.getChildren();
        list.addAll(children);
        for(TDeviceType tdt : children) {
            list.addAll(extractChildrenType(tdt));
        }
        return list;
    }

    @Override
    @Transactional
    public void configDeviceCategory(TDeviceType dvc) {
        List<TDeviceType> children = extractChildrenType(dvc);
        List<TDeviceType> newList = new ArrayList<>();
        for(TDeviceType tdt : children) {
            if (tdt.getId() == 0) {
                newList.add(tdt);
            } else {
                deviceTypeMapper.updateByPrimaryKeySelective(tdt);
            }
        }
        deviceTypeMapper.insertList(newList);
    }

    @Override
    @Transactional
    public void attachDeviceProblem(TDeviceType dvc) {
        List<TDeviceProblem> problems = dvc.getProblems();
    }
}
