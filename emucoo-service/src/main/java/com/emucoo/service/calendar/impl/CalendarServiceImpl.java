package com.emucoo.service.calendar.impl;

import com.emucoo.dto.modules.calendar.CalendarListIn;
import com.emucoo.dto.modules.calendar.CalendarListOut;
import com.emucoo.dto.modules.task.WorkVo_O;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.calendar.CalendarService;
import com.emucoo.utils.ConstantsUtil;
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



    public CalendarListOut listCalendar(CalendarListIn calendarListIn){
        CalendarListOut calendarListOut=new CalendarListOut();
        WorkVo_O.Work work = null;
        List<WorkVo_O.Work> workArr=new ArrayList<>();
        calendarListOut.setMonth( calendarListIn.getMonth());
        calendarListOut.setUserId(calendarListIn.getUserId());
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
            tLoopWorkMapper.selectByExample(exampleLoopWork);

        }
        calendarListOut.setWorkArr(workArr);


        return calendarListOut;
    }


}
