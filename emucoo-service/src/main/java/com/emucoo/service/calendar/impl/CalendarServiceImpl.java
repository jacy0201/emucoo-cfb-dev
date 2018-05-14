package com.emucoo.service.calendar.impl;

import com.emucoo.dto.modules.calendar.CalendarListIn;
import com.emucoo.dto.modules.calendar.CalendarListOut;
import com.emucoo.mapper.TFrontPlanMapper;
import com.emucoo.model.TFrontPlan;
import com.emucoo.service.calendar.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    public CalendarListOut listCalendar(CalendarListIn calendarListIn){

        CalendarListOut calendarListOut=null;
        String yearMonth = calendarListIn.getMonth();
        calendarListIn.getMonth();
        Long userId=calendarListIn.getUserId();
        Example example=new Example(TFrontPlan.class);
        example.createCriteria().andEqualTo("arrangeeId",userId);
        List<TFrontPlan> list= tFrontPlanMapper.selectByExample(example);

        return calendarListOut;
    }


}
