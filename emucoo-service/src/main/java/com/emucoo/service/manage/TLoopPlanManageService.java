package com.emucoo.service.manage;

import com.emucoo.model.TLoopPlan;

/**
 * Created by sj on 2018/4/15.
 */
public interface TLoopPlanManageService {

    void addPlan(TLoopPlan plan);

    void updatePlanById(TLoopPlan plan);

    void startPlanById(TLoopPlan plan);

    void stopPlanById(TLoopPlan plan);

    void deletePlanById(TLoopPlan plan);
}
