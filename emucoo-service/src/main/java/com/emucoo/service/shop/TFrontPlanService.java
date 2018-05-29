package com.emucoo.service.shop;

import com.emucoo.dto.modules.report.*;
import com.emucoo.model.TFrontPlan;

import java.util.Date;
import java.util.List;

public interface TFrontPlanService {

    List<TFrontPlan> filterExecuteRemindArrange(Date currentDate, int cycleMinutes);
}
