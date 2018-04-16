package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFrontPlan;

import java.util.Date;
import java.util.List;

public interface TFrontPlanMapper extends MyMapper<TFrontPlan> {
    int countTodayPlanOfUser(Long userId, Date needDate);

    List<TFrontPlan> listTodayPlanOfUser(Long submitUserId, Date needDate);
}