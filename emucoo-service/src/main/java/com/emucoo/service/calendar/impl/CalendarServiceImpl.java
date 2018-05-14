package com.emucoo.service.calendar.impl;

import com.emucoo.dto.modules.calendar.CalendarListDateIn;
import com.emucoo.dto.modules.calendar.CalendarListDateOut;
import com.emucoo.dto.modules.calendar.CalendarListMonthIn;
import com.emucoo.dto.modules.calendar.CalendarListMonthOut;
import com.emucoo.dto.modules.task.WorkVo_O;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.calendar.CalendarService;
import com.emucoo.utils.ConstantsUtil;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 行事历
 * @author Jacy
 *
 */
@Transactional
@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private TFrontPlanMapper tFrontPlanMapper;

    @Autowired
    private TFrontPlanFormMapper tFrontPlanFormMapper;

    @Autowired
    private TFormMainMapper tFormMainMapper;

    @Autowired
    private TShopInfoMapper tShopInfoMapper;

    @Autowired
    private TLoopWorkMapper tLoopWorkMapper;

    @Autowired
    private TTaskMapper taskMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public CalendarListMonthOut listCalendarMonth(CalendarListMonthIn calendarListIn){
        CalendarListMonthOut calendarListMonthOut =new CalendarListMonthOut();
        WorkVo_O.Work work = null;
        List<WorkVo_O.Work> workArr=new ArrayList<>();
        calendarListMonthOut.setMonth( calendarListIn.getMonth());
        calendarListMonthOut.setUserId(calendarListIn.getUserId());
        String yearStr = calendarListIn.getMonth().substring(0,4);
        String monthStr = calendarListIn.getMonth().substring(4,6);
        Example example=new Example(TFrontPlan.class);
        example.createCriteria().andEqualTo("arrangeeId",calendarListIn.getUserId())
        .andEqualTo("arrangeYear",yearStr).andEqualTo("arrangeMonth",monthStr)
        .andEqualTo("isDel",false);
        List<TFrontPlan> list= tFrontPlanMapper.selectByExample(example);
        //设置巡店安排
        if(null!=list && list.size()>0){
            for (TFrontPlan frontPlan:list){
                work=new WorkVo_O.Work();
                work.setId(frontPlan.getId());
                work.setSubID(frontPlan.getSubPlanId().toString());
                work.setWorkID(frontPlan.getLoopPlanId().toString());
                work.setWorkType(ConstantsUtil.LoopWork.TYPE_FOUR);
                WorkVo_O.Work.Inspection inspection=work.getInspection();
                inspection.setInspStartTime(frontPlan.getPlanPreciseTime());
                inspection.setInspStatus(frontPlan.getStatus().intValue());
                //查询表单
                Example exampleForm=new Example(TFrontPlanForm.class);
                exampleForm.createCriteria().andEqualTo("frontPlanId",frontPlan.getId()).andEqualTo("isDel",false);
                List<TFrontPlanForm> formList=tFrontPlanFormMapper.selectByExample(exampleForm);
                if(null!=formList && formList.size()>0){
                    TShopInfo shopInfo=tShopInfoMapper.selectByPrimaryKey(frontPlan.getShopId());
                    TFormMain tFormMain= tFormMainMapper.selectByPrimaryKey(formList.get(0).getFormMainId());
                    inspection.setInspTitle(shopInfo.getShopName()+tFormMain.getName()+"检查");
                }
                work.setInspection(inspection);
                workArr.add(work);
            }
            //设置 常规任务,指派任务，改善任务
            Example exampleLoopWork=new Example(TLoopWork.class);
            exampleLoopWork.createCriteria().andEqualTo("excuteUserId",calendarListIn.getUserId())
                    .andEqualTo("isDel",false).andEqualTo("");
            List<TLoopWork> loopWorkList= tLoopWorkMapper.calendarList(calendarListIn.getUserId(), DateUtil.strToYYMMDate(calendarListIn.getMonth()));
            if(null!=loopWorkList && loopWorkList.size()>0){
                for (TLoopWork tLoopWork :loopWorkList){
                    work=new WorkVo_O.Work();
                    work.setId(tLoopWork.getId());
                    work.setWorkID(tLoopWork.getWorkId());
                    work.setWorkType(tLoopWork.getType());
                    work.setSubID(tLoopWork.getSubWorkId());
                    TTask tt = taskMapper.selectByPrimaryKey(tLoopWork.getTaskId());
                    work.getTask().setTaskTitle(tt.getName());
                    work.getTask().setTaskStatus(tLoopWork.getWorkStatus());
                    work.getTask().setTaskResult(tLoopWork.getWorkResult());
                    work.getTask().setTaskSourceType(0);
                    work.getTask().setTaskSourceName("");
                    work.getTask().setTaskDeadline(tLoopWork.getExecuteDeadline());
                    SysUser u = sysUserMapper.selectByPrimaryKey(tLoopWork.getExcuteUserId());
                    work.getTask().setTaskSubHeadUrl(u.getHeadImgUrl());
                    work.getTask().setTaskSubName(u.getRealName());
                    workArr.add(work);
                }
            }

        }
        calendarListMonthOut.setWorkArr(workArr);
        return calendarListMonthOut;
    }



    public CalendarListDateOut listCalendarDate(CalendarListDateIn calendarListIn){
        CalendarListDateOut calendarListDateOut =new CalendarListDateOut();
        WorkVo_O.Work work = null;
        List<WorkVo_O.Work> workArr=new ArrayList<>();
        calendarListDateOut.setDate( calendarListIn.getDate());
        calendarListDateOut.setUserId(calendarListIn.getUserId());
        Example example=new Example(TFrontPlan.class);
        example.createCriteria().andEqualTo("arrangeeId",calendarListIn.getUserId())
                .andEqualTo("planDate",calendarListIn.getDate())
                .andEqualTo("isDel",false);
        List<TFrontPlan> list= tFrontPlanMapper.selectByExample(example);
        //设置巡店安排
        if(null!=list && list.size()>0){
            for (TFrontPlan frontPlan:list){
                work=new WorkVo_O.Work();
                work.setId(frontPlan.getId());
                work.setSubID(frontPlan.getSubPlanId().toString());
                work.setWorkID(frontPlan.getLoopPlanId().toString());
                work.setWorkType(ConstantsUtil.LoopWork.TYPE_FOUR);
                WorkVo_O.Work.Inspection inspection=work.getInspection();
                inspection.setInspStartTime(frontPlan.getPlanPreciseTime());
                inspection.setInspStatus(frontPlan.getStatus().intValue());
                //查询表单
                Example exampleForm=new Example(TFrontPlanForm.class);
                exampleForm.createCriteria().andEqualTo("frontPlanId",frontPlan.getId()).andEqualTo("isDel",false);
                List<TFrontPlanForm> formList=tFrontPlanFormMapper.selectByExample(exampleForm);
                if(null!=formList && formList.size()>0){
                    TShopInfo shopInfo=tShopInfoMapper.selectByPrimaryKey(frontPlan.getShopId());
                    TFormMain tFormMain= tFormMainMapper.selectByPrimaryKey(formList.get(0).getFormMainId());
                    inspection.setInspTitle(shopInfo.getShopName()+tFormMain.getName()+"检查");
                }
                work.setInspection(inspection);
                workArr.add(work);
            }
            //设置 常规任务,指派任务，改善任务
            Example exampleLoopWork=new Example(TLoopWork.class);
            exampleLoopWork.createCriteria().andEqualTo("excuteUserId",calendarListIn.getUserId())
                    .andEqualTo("isDel",false).andEqualTo("");
            List<TLoopWork> loopWorkList= tLoopWorkMapper.calendarList(calendarListIn.getUserId(), calendarListIn.getDate());
            if(null!=loopWorkList && loopWorkList.size()>0){
                for (TLoopWork tLoopWork :loopWorkList){
                    work=new WorkVo_O.Work();
                    work.setId(tLoopWork.getId());
                    work.setWorkID(tLoopWork.getWorkId());
                    work.setWorkType(tLoopWork.getType());
                    work.setSubID(tLoopWork.getSubWorkId());
                    TTask tt = taskMapper.selectByPrimaryKey(tLoopWork.getTaskId());
                    work.getTask().setTaskTitle(tt.getName());
                    work.getTask().setTaskStatus(tLoopWork.getWorkStatus());
                    work.getTask().setTaskResult(tLoopWork.getWorkResult());
                    work.getTask().setTaskSourceType(0);
                    work.getTask().setTaskSourceName("");
                    work.getTask().setTaskDeadline(tLoopWork.getExecuteDeadline());
                    SysUser u = sysUserMapper.selectByPrimaryKey(tLoopWork.getExcuteUserId());
                    work.getTask().setTaskSubHeadUrl(u.getHeadImgUrl());
                    work.getTask().setTaskSubName(u.getRealName());
                    workArr.add(work);
                }
            }

        }
        calendarListDateOut.setWorkArr(workArr);
        return calendarListDateOut;
    }

}
