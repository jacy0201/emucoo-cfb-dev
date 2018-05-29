package com.emucoo.service.shop.imp;

import com.emucoo.dto.modules.report.*;
import com.emucoo.mapper.TFrontPlanMapper;
import com.emucoo.model.TFrontPlan;
import com.emucoo.service.shop.TFrontPlanService;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TFrontPlanServiceImpl implements TFrontPlanService {

    @Autowired
    private TFrontPlanMapper frontPlanMapper;

    public List<TFrontPlan> filterExecuteRemindArrange(Date currentDate, int cycleMinutes) {
        Date remindTimeLeft = currentDate;
        Date remindTimeRight = DateUtil.timeForward(currentDate, 0, cycleMinutes);
        List<TFrontPlan> frontPlans = frontPlanMapper.filterExecuteRemindArrange(remindTimeLeft, remindTimeRight);
        return frontPlans;
    }

}
