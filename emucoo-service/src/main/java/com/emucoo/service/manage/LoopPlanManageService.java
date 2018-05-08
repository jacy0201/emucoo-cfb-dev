package com.emucoo.service.manage;

import com.emucoo.model.TLoopPlan;

import java.util.List;

/**
 * Created by sj on 2018/4/15.
 */
public interface LoopPlanManageService {

    void addPlan(TLoopPlan plan);

    void updatePlanById(TLoopPlan plan);

    void modifyPlanUseById(TLoopPlan plan);

    void deletePlanById(TLoopPlan plan);

    List<TLoopPlan> findPlanListByCondition(TLoopPlan plan, Integer pageNumber, Integer pageSize);

    TLoopPlan findPlanById(TLoopPlan plan);
}
