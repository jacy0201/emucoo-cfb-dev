package com.emucoo.service.center.impl;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.center.TaskVO;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.center.UserCenterService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心
 * @author Jacy
 *
 */
@Service
public class UserCenterServiceImpl extends BaseServiceImpl<TLoopWork> implements UserCenterService {

    @Resource
    private TLoopWorkMapper tLoopWorkMapper;

    @Resource
    private TFrontPlanMapper tFrontPlanMapper;

    @Resource
    private TFrontPlanFormMapper tFrontPlanFormMapper;

    @Resource
    private TFormMainMapper tFormMainMapper;
    @Resource
    private TShopInfoMapper tShopInfoMapper;

    //我执行的任务
    @Override
   public  List<TaskVO> executeTaskList(String month,Long userId){
        String[] DateStr = month.split("-");
        List<TLoopWork> loopWorkList = tLoopWorkMapper.getTaskList(DateStr[0], DateStr[1], null, userId, null);
        List<TaskVO> list = getTaskVOS(loopWorkList);
        return  list;
     }

    //我审核的任务
    @Override
    public List<TaskVO> auditTaskList(String month,Long userId){
        String[] DateStr = month.split("-");
        List<TLoopWork> loopWorkList = tLoopWorkMapper.getTaskList(DateStr[0], DateStr[1], userId, null, null);
        List<TaskVO> list = getTaskVOS(loopWorkList);
        return  list;
    }

    //我创建的任务
    @Override
    public List<TaskVO> createTaskList(String month,Long userId){
        String[] DateStr = month.split("-");
        List<TLoopWork> loopWorkList = tLoopWorkMapper.getTaskList(DateStr[0], DateStr[1], null, null, userId);
        List<TaskVO> list = getTaskVOS(loopWorkList);
        return  list;

    }

    @Override
    //巡店任务
    public List<TaskVO> frontPlanList(String month,Long userId){
        String[] DateStr = month.split("-");
        Example example=new Example(TFrontPlan.class);
        example.createCriteria() .andEqualTo("arrangeYear", DateStr[0])
                .andEqualTo("arrangeMonth", DateStr[1])
                .andEqualTo("isDel", 0);
        Example.Criteria criteria2=example.createCriteria();
        criteria2.orEqualTo("arrangeeId", userId)
                 .orEqualTo("arrangerId",userId);
        example.setOrderByClause("modify_time desc");
        example.and(criteria2);
        List<TFrontPlan> frontPlanList = tFrontPlanMapper.selectByExample(example);
        List<TaskVO> list=null;
        if(null!=frontPlanList && frontPlanList.size()>0) {
            TaskVO taskVO=null;
            for (TFrontPlan frontPlan : frontPlanList) {
                taskVO= new TaskVO();
                taskVO.setID(frontPlan.getId());
                taskVO.setSubID(frontPlan.getSubPlanId().toString());
                taskVO.setModifyTime(frontPlan.getModifyTime().getTime());
                taskVO.setWorkType(4);
                Example exampleForm = new Example(TFrontPlanForm.class);
                exampleForm.createCriteria().andEqualTo("frontPlanId", frontPlan.getId()).andEqualTo("isDel", false);
                List<TFrontPlanForm> formList = tFrontPlanFormMapper.selectByExample(exampleForm);
                if (null != formList && formList.size() > 0) {
                    TShopInfo shopInfo = tShopInfoMapper.selectByPrimaryKey(frontPlan.getShopId());
                    TFormMain tFormMain = tFormMainMapper.selectByPrimaryKey(formList.get(0).getFormMainId());
                    String formName=  tFormMain == null ? "" : tFormMain.getName();
                    String shopName = shopInfo == null ? "" : shopInfo.getShopName();
                    taskVO.setTaskTitle(shopName + formName + "检查");
                }
                list.add(taskVO);
            }
        }
        return list;

    }

    //我接收的报告
    @Override
    public List<TaskVO> getReportList(String month,Long userId){
        return null;

    }







    private List<TaskVO> getTaskVOS(List<TLoopWork> loopWorkList) {
        TaskVO taskVO=null;
        List<TaskVO> list=null;
        if (null != loopWorkList && loopWorkList.size() > 0) {
            list = new ArrayList<>();
            for (TLoopWork loopWork : loopWorkList) {
                taskVO=new TaskVO();
                taskVO.setID(loopWork.getId());
                taskVO.setWorkID(loopWork.getWorkId());
                taskVO.setSubID(loopWork.getSubWorkId());
                taskVO.setTaskTitle(loopWork.getTaskTitle());
                //workStatus=3 或 5 系统待审核
                taskVO.setTaskStatus(loopWork.getWorkStatus());
                taskVO.setTaskResult(loopWork.getWorkResult());
                taskVO.setModifyTime(loopWork.getModifyTime().getTime());
                list.add(taskVO);
            }
        }
        return list;
    }




}
