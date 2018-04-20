package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TLoopPlan;
import com.emucoo.model.TPlanFormRelation;

import java.util.List;

public interface TPlanFormRelationMapper extends MyMapper<TPlanFormRelation> {
    void addPlanFormRelation(List<TPlanFormRelation> planFormRelationList);

    void deleteById(Long id);

    void modifyUseStatus(TLoopPlan plan);
}