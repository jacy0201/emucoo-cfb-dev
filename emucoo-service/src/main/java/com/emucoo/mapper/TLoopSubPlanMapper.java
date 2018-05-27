package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TLoopSubPlan;

public interface TLoopSubPlanMapper extends MyMapper<TLoopSubPlan> {
    void addLoopSubPlan(TLoopSubPlan tLoopSubPlan);

    TLoopSubPlan findSubPlanByArrangeId(Long frontPlanId);
}