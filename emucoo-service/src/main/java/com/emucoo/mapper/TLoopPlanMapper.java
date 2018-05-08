package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TLoopPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TLoopPlanMapper extends MyMapper<TLoopPlan> {
    void addPlan(TLoopPlan plan);

    void updatePlanById(TLoopPlan plan);

    void modifyPlanStatusById(TLoopPlan plan);

    void deletePlanById(TLoopPlan plan);

    List<TLoopPlan> findPlanListByCondition(@Param("plan")TLoopPlan plan, @Param("pageSize")Integer pageSize, @Param("pageMargin")Integer pageMargin);

    TLoopPlan findPlanById(TLoopPlan plan);
}