package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TLoopPlan;

import java.util.List;

public interface TLoopPlanMapper extends MyMapper<TLoopPlan> {
    void addPlan(TLoopPlan plan);

    void updatePlanById(TLoopPlan plan);

    void modifyPlanStatusById(TLoopPlan plan);

    void deletePlanById(TLoopPlan plan);

    List<TLoopPlan> findPlanListByCondition(TLoopPlan plan);

    TLoopPlan findPlanById(TLoopPlan plan);
}