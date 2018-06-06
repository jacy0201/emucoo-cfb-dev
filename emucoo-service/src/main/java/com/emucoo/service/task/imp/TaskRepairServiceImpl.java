package com.emucoo.service.task.imp;

import com.emucoo.mapper.TDeviceProblemMapper;
import com.emucoo.mapper.TDeviceTypeMapper;
import com.emucoo.mapper.TRepairWorkMapper;
import com.emucoo.model.TDeviceProblem;
import com.emucoo.model.TDeviceType;
import com.emucoo.model.TRepairWork;
import com.emucoo.service.task.TaskRepairService;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class TaskRepairServiceImpl implements TaskRepairService {

    @Autowired
    private TRepairWorkMapper repairWorkMapper;

    @Autowired
    private TDeviceTypeMapper deviceTypeMapper;

    @Autowired
    private TDeviceProblemMapper deviceProblemMapper;

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
    public void removeDeviceType(long deviceTypeId) {
        deviceTypeMapper.removeDeviceType(deviceTypeId);
    }

    @Override
    public void switchDeviceUsable(long deviceTypeId, boolean b) {
        deviceTypeMapper.switchDeviceTypeUsage(deviceTypeId, b);
    }

    @Override
    public TDeviceType fetchDeviceTypeInfo(long deviceTypeId) {
        TDeviceType deviceType =  deviceTypeMapper.selectByPrimaryKey(deviceTypeId);
        List<TDeviceType> children = deviceTypeMapper.findChildren(deviceTypeId);
        deviceType.setChildren(children);
        for(TDeviceType child : children) {
            child.setChildren(deviceTypeMapper.findChildren(child.getId()));
        }
        List<TDeviceProblem> problems = deviceProblemMapper.findDeviceTypeProblems(deviceTypeId);
        deviceType.setProblems(problems);
        return deviceType;
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
        dvc.setTier(2);
        dvc.setCreateTime(DateUtil.currentDate());
        dvc.setModifyTime(DateUtil.currentDate());
        if(dvc.getId() == 0) {
            deviceTypeMapper.insert(dvc);
        } else {
            deviceTypeMapper.updateByPrimaryKeySelective(dvc);
        }
    }

    private void configChildrenCategory(TDeviceType dvc) {
        List<TDeviceType> children = dvc.getChildren();
        List<TDeviceType> childTypes = deviceTypeMapper.findChildren(dvc.getId());
        for(TDeviceType tdt : children) {
            tdt.setParentTypeId(dvc.getId());
            tdt.setTier(dvc.getTier() + 1);
            if (tdt.getId() == 0){
                deviceTypeMapper.insert(tdt);
            } else {
                deviceTypeMapper.updateByPrimaryKeySelective(tdt);
                Iterator<TDeviceType> it = childTypes.iterator();
                while (it.hasNext()) {
                    if (it.next().getId() == tdt.getId()){
                        it.remove();
                        break;
                    }
                }
            }
            configChildrenCategory(tdt);
        }
        for(TDeviceType old : childTypes) {
            deviceTypeMapper.deleteByPrimaryKey(old.getId());
        }
    }

    @Override
    @Transactional
    public void configDeviceCategory(TDeviceType dvc) {
        configChildrenCategory(dvc);
    }

    @Override
    @Transactional
    public void attachDeviceProblem(TDeviceType dvc) {
        List<TDeviceProblem> oldProblems = deviceProblemMapper.findDeviceTypeProblems(dvc.getId());
        List<TDeviceProblem> problems = dvc.getProblems();
        for(TDeviceProblem problem : problems) {
            if (problem.getId() == 0){
                deviceProblemMapper.insert(problem);
            } else {
                deviceProblemMapper.updateByPrimaryKeySelective(problem);
                Iterator<TDeviceProblem> it =  oldProblems.iterator();
                while(it.hasNext()) {
                    if (it.next().getId() == problem.getId()){
                        it.remove();
                        break;
                    }
                }
            }
        }
        for(TDeviceProblem oldproblem : oldProblems) {
            deviceProblemMapper.deleteByPrimaryKey(oldproblem.getId());
        }
    }

    @Override
    public List<TDeviceType> listTopDeviceTypes() {
        return deviceTypeMapper.listTopDeviceTypes();
    }
}
