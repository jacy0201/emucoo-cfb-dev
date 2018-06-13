package com.emucoo.service.task.imp;

import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.task.TaskRepairService;
import com.emucoo.utils.ConstantsUtil;
import com.emucoo.utils.DateUtil;
import com.emucoo.service.task.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Shayne.Wang
 * @date 2018-06-07
 */

@Service
public class TaskRepairServiceImpl implements TaskRepairService {

    @Autowired
    private TRepairWorkMapper repairWorkMapper;

    @Autowired
    private TDeviceTypeMapper deviceTypeMapper;

    @Autowired
    private TDeviceProblemMapper deviceProblemMapper;

    @Autowired
    private TBusinessMsgMapper businessMsgMapper;

    @Autowired
    private MessageBuilder messageBuilder;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public TRepairWork detail(long workId) {
        return repairWorkMapper.selectByPrimaryKey(workId);
    }

    @Override
    public List<TRepairWork> listRepairWorksByShopId(long shopId, String date, long userId) {
        Date beginDt = DateUtil.strToSimpleYYMMDDDate(date + "01");
        Date endDt = DateUtil.timeBackward(DateUtil.dateAddMonth(beginDt, 1), 0, 0, 1);
        List<TRepairWork> works = repairWorkMapper.fetchWorksListByDate(shopId, userId, beginDt, endDt);
        return works;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void createRepairWork(TRepairWork work) {
        work.setWorkStatus(ConstantsUtil.RepairWork.STATUS_1);
        repairWorkMapper.insert(work);

        // 推送并保存消息
        SysUser user = userMapper.selectByPrimaryKey(work.getRepairManId());
        TBusinessMsg businessMsg = messageBuilder.buildRepairWorkCreationBusinessMessage(work, user, 1);
        messageBuilder.pushMessage(businessMsg, user, 1);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void modifyRepairWork(TRepairWork work) {
        repairWorkMapper.updateByPrimaryKeySelective(work);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void finishRepairWork(TRepairWork work) {
        work.setWorkStatus(ConstantsUtil.RepairWork.STATUS_4);
        repairWorkMapper.updateByPrimaryKeySelective(work);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void expectRepairDate(TRepairWork work) {
        work.setWorkStatus(ConstantsUtil.RepairWork.STATUS_2);
        repairWorkMapper.updateByPrimaryKeySelective(work);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void auditRepairWork(TRepairWork work) {
        work.setWorkStatus(ConstantsUtil.RepairWork.STATUS_5);
        repairWorkMapper.updateByPrimaryKeySelective(work);
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
        TDeviceType deviceType = deviceTypeMapper.selectByPrimaryKey(deviceTypeId);
        List<TDeviceType> children = deviceTypeMapper.findChildren(deviceTypeId);
        deviceType.setChildren(children);
        for (TDeviceType child : children) {
            child.setChildren(deviceTypeMapper.findChildren(child.getId()));
        }
        List<TDeviceProblem> problems = deviceProblemMapper.findDeviceTypeProblems(deviceTypeId);
        deviceType.setProblems(problems);
        return deviceType;
    }

    @Override
    public List<TDeviceType> listDeviceTypes(TDeviceType deviceType, int pageNm, int pageSz) {
        return deviceTypeMapper.filterDeviceTypesByKeyword(deviceType, (pageNm - 1) * pageSz, pageSz);
    }

    @Override
    public int countDeviceTypes(TDeviceType deviceType) {
        return deviceTypeMapper.countDeviceTypesByKeyword(deviceType);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveDeviceType(TDeviceType dvc) {
        dvc.setTier(2);
        dvc.setCreateTime(DateUtil.currentDate());
        dvc.setModifyTime(DateUtil.currentDate());
        if (dvc.getId() == 0) {
            deviceTypeMapper.insert(dvc);
        } else {
            deviceTypeMapper.updateByPrimaryKeySelective(dvc);
        }
    }

    /**
     * 递归删除子设备
     *
     * @param dvcId
     */
    private void recurseDeleteChildrenCategory(long dvcId) {
        List<TDeviceType> list = deviceTypeMapper.findChildren(dvcId);
        for (TDeviceType deviceType : list) {
            deviceTypeMapper.removeDeviceType(deviceType.getId());
            recurseDeleteChildrenCategory(deviceType.getId());
        }
    }

    /**
     * 递归配置子设备
     *
     * @param dvc
     */
    private void configChildrenCategory(TDeviceType dvc) {
        List<TDeviceType> children = dvc.getChildren();
        if (children == null) {
            return;
        }
        List<TDeviceType> childTypes = deviceTypeMapper.findChildren(dvc.getId());
        for (TDeviceType tdt : children) {
            tdt.setParentTypeId(dvc.getId());
            tdt.setTier(dvc.getTier() + 1);
            if (tdt.getId() == 0) {
                deviceTypeMapper.insertUseGeneratedKeys(tdt);
            } else {
                deviceTypeMapper.updateByPrimaryKeySelective(tdt);
                Iterator<TDeviceType> it = childTypes.iterator();
                while (it.hasNext()) {
                    if (it.next().getId() == tdt.getId()) {
                        it.remove();
                        break;
                    }
                }
            }
            configChildrenCategory(tdt);
        }
        for (TDeviceType old : childTypes) {
            deviceTypeMapper.removeDeviceType(old.getId());
            recurseDeleteChildrenCategory(old.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void configDeviceCategory(TDeviceType dvc) {
        configChildrenCategory(dvc);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void attachDeviceProblem(TDeviceType dvc) {
        List<TDeviceProblem> oldProblems = deviceProblemMapper.findDeviceTypeProblems(dvc.getId());
        List<TDeviceProblem> problems = dvc.getProblems();
        for (TDeviceProblem problem : problems) {
            if (problem.getId() == 0) {
                problem.setCreateTime(DateUtil.currentDate());
                problem.setModifyTime(DateUtil.currentDate());
                deviceProblemMapper.insert(problem);
            } else {
                problem.setModifyTime(DateUtil.currentDate());
                deviceProblemMapper.updateByPrimaryKeySelective(problem);
                Iterator<TDeviceProblem> it = oldProblems.iterator();
                while (it.hasNext()) {
                    if (it.next().getId() == problem.getId()) {
                        it.remove();
                        break;
                    }
                }
            }
        }
        for (TDeviceProblem oldproblem : oldProblems) {
            deviceProblemMapper.deleteByPrimaryKey(oldproblem.getId());
        }
    }

    @Override
    public List<TDeviceType> listTopDeviceTypes() {
        return deviceTypeMapper.listTopDeviceTypes();
    }

    @Override
    public List<TDeviceType> listChildrenDeviceType(long parentId) {
        return deviceTypeMapper.findChildren(parentId);
    }

    @Override
    public List<TDeviceProblem> listDeviceProblems(long deviceId) {
        return deviceProblemMapper.findDeviceTypeProblems(deviceId);
    }
}
