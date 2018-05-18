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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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

    @Resource
    private TFrontPlanMapper tFrontPlanMapper;
    @Resource
    private TFrontPlanFormMapper tFrontPlanFormMapper;
    @Resource
    private TFormMainMapper tFormMainMapper;
    @Resource
    private TShopInfoMapper tShopInfoMapper;
    @Resource
    private TLoopWorkMapper tLoopWorkMapper;
    @Resource
    private TTaskMapper taskMapper;
    @Resource
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
        example.setOrderByClause("plan_precise_time desc");
        List<TFrontPlan> list= tFrontPlanMapper.selectByExample(example);
        //设置巡店安排
        if(null!=list && list.size()>0){
            for (TFrontPlan frontPlan:list){
                work = getFrontPlanWork(frontPlan);
                workArr.add(work);
            }
            //设置 常规任务,指派任务，改善任务
            List<TLoopWork> loopWorkList= tLoopWorkMapper.calendarMonthList(calendarListIn.getUserId(),yearStr,monthStr);
            if(null!=loopWorkList && loopWorkList.size()>0){
                for (TLoopWork tLoopWork :loopWorkList){
                    work = getLoopWork(tLoopWork);
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
        calendarListDateOut.setDate( calendarListIn.getExecuteDate());
        calendarListDateOut.setUserId(calendarListIn.getUserId());
        Example example=new Example(TFrontPlan.class);
        example.createCriteria().andEqualTo("arrangeeId",calendarListIn.getUserId())
                .andEqualTo("plan_date",calendarListIn.getExecuteDate())
                .andEqualTo("isDel",false);
        example.setOrderByClause("plan_precise_time desc");
        List<TFrontPlan> list= tFrontPlanMapper.selectByExample(example);
        //设置巡店安排
        if(null!=list && list.size()>0){
            for (TFrontPlan frontPlan:list){
                work = getFrontPlanWork(frontPlan);
                workArr.add(work);
            }
            //设置 常规任务,指派任务，改善任务
            List<TLoopWork> loopWorkList= tLoopWorkMapper.calendarDateList(calendarListIn.getUserId(),calendarListIn.getExecuteDate() );
            if(null!=loopWorkList && loopWorkList.size()>0){
                for (TLoopWork tLoopWork :loopWorkList){
                    work = getLoopWork(tLoopWork);
                    workArr.add(work);
                }
            }

        }
        calendarListDateOut.setWorkArr(workArr);
        return calendarListDateOut;
    }

    private WorkVo_O.Work getFrontPlanWork(TFrontPlan frontPlan) {
        WorkVo_O.Work work=new WorkVo_O.Work();
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
        return work;
    }

    private WorkVo_O.Work getLoopWork(TLoopWork tLoopWork) {
        WorkVo_O.Work work=new WorkVo_O.Work();
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
        return work;
    }

    public List<SysUser> listLowerUser(Long userId){
        List<SysUser> list=new ArrayList<>();
        String userIds=sysUserMapper.findAllChildListByParentId(userId);
        String [] userArr=userIds.split(",");
       if(userArr.length>2){
          for (int i=2;i<userArr.length;i++){
              SysUser sysUser=sysUserMapper.selectByPrimaryKey(Long.parseLong(userArr[i]));
              list.add(sysUser);
          }
       }
        return list;
    }

}
