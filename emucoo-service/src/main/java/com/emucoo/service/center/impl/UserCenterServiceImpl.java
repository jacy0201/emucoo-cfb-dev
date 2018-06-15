package com.emucoo.service.center.impl;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.center.FrontPlanVO;
import com.emucoo.dto.modules.center.RepairWorkVO;
import com.emucoo.dto.modules.center.ReportVO;
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
    @Resource
    private TReportMapper tReportMapper;
    @Resource
    private TFormCheckResultMapper formCheckResultMapper;
    @Resource
    private TRepairWorkMapper tRepairWorkMapper;


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
    public List<FrontPlanVO> frontPlanList(String month, Long userId){
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
        List<FrontPlanVO> list=new ArrayList<>();
        if(null!=frontPlanList && frontPlanList.size()>0) {
            FrontPlanVO frontPlanVO=null;
            for (TFrontPlan frontPlan : frontPlanList) {
                frontPlanVO= new FrontPlanVO();
                frontPlanVO.setID(frontPlan.getId());
                frontPlanVO.setSubID(frontPlan.getSubPlanId().toString());
                frontPlanVO.setWorkType(4);
                frontPlanVO.setShopId(frontPlan.getShopId());
                if(null!=frontPlan.getActualExecuteTime()) frontPlanVO.setActualExecuteTime(frontPlan.getActualExecuteTime().getTime());
                TShopInfo shopInfo = tShopInfoMapper.selectByPrimaryKey(frontPlan.getShopId());
                String shopName = shopInfo == null ? "" : shopInfo.getShopName();
                frontPlanVO.setShopName(shopName);
                frontPlanVO.setTaskStatus(frontPlan.getStatus());
                if(null!=frontPlan.getActualRemindTime()) frontPlanVO.setActualRemindTime(frontPlan.getActualRemindTime().getTime());
                if(null!=frontPlan.getPlanPreciseTime()) frontPlanVO.setPlanPreciseTime(frontPlan.getPlanPreciseTime().getTime());
                Example exampleForm = new Example(TFrontPlanForm.class);
                exampleForm.createCriteria().andEqualTo("frontPlanId", frontPlan.getId()).andEqualTo("isDel", false);
                List<TFrontPlanForm> formList = tFrontPlanFormMapper.selectByExample(exampleForm);
                if (null != formList && formList.size() > 0) {
                    TFormMain tFormMain = tFormMainMapper.selectByPrimaryKey(formList.get(0).getFormMainId());
                    String formName=  tFormMain == null ? "" : tFormMain.getName();
                    frontPlanVO.setTaskTitle(shopName + formName + "检查");
                }
                list.add(frontPlanVO);
            }
        }
        return list;

    }

    //我接收的报告
    @Override
    public List<ReportVO> getReportList(String month, Long userId){
        List<TReport> list =tReportMapper.fetchReceiveReport(userId,month);
        List<ReportVO> reportList=new ArrayList<>();
        if(null!=list && list.size()>0){
            ReportVO reportVO=null;
            for(TReport report:list){
                reportVO=new ReportVO();
                reportVO.setReportId(report.getId());
                reportVO.setCheckFormTime(report.getCreateTime().getTime());
                reportVO.setFormType(report.getFormType());
                reportVO.setReporterName(report.getReporterName());
                reportVO.setShopName(report.getShopName());
                Long formResultId=report.getFormResultId();
                TFormCheckResult tfr=formCheckResultMapper.selectByPrimaryKey(formResultId);
                if(null!=tfr){
                    reportVO.setReportName(report.getShopName()+tfr.getFormMainName());
                }
                reportList.add(reportVO);
            }
        }
        return  reportList;
    }


    //维修任务
    @Override
    public List<TRepairWork> repairWorkList(String month, Long userId){
        List<TRepairWork> list=tRepairWorkMapper.getRepairWorkList(month,userId);
        return list;
    }


    private List<TaskVO> getTaskVOS(List<TLoopWork> loopWorkList) {
        TaskVO taskVO=null;
        List<TaskVO> list= new ArrayList<>();
        if (null != loopWorkList && loopWorkList.size() > 0) {
            for (TLoopWork loopWork : loopWorkList) {
                taskVO=new TaskVO();
                taskVO.setID(loopWork.getId());
                taskVO.setWorkID(loopWork.getWorkId());
                taskVO.setSubID(loopWork.getSubWorkId());
                taskVO.setTaskTitle(loopWork.getTaskTitle());
                taskVO.setWorkType(loopWork.getType());
                //workStatus=5 系统待审核
                taskVO.setTaskStatus(loopWork.getWorkStatus());
                taskVO.setTaskResult(loopWork.getWorkResult());
                if(null!=loopWork.getExecuteDeadline()) taskVO.setExecuteDeadline(loopWork.getExecuteDeadline().getTime());
                if(null!=loopWork.getExecuteRemindTime()) taskVO.setExecuteRemindTime(loopWork.getExecuteRemindTime().getTime());
                if(null!=loopWork.getAuditDeadline()) taskVO.setAuditDeadline(loopWork.getAuditDeadline().getTime());
                taskVO.setReporterName(loopWork.getExcuteUserName());
                list.add(taskVO);
            }
        }
        return list;
    }


}
