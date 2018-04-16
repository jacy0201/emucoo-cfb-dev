package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TLoopPlan;

public interface TLoopPlanMapper extends MyMapper<TLoopPlan> {
    void addPlan(TLoopPlan plan);

    void updatePlanById(TLoopPlan plan);

    void modifyPlanStatusById(TLoopPlan plan);

    void deletePlanById(TLoopPlan plan);
}