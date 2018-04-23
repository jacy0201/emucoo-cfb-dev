package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFrontPlanForm;

import java.util.List;

public interface TFrontPlanFormMapper extends MyMapper<TFrontPlanForm> {

    void addRelationToArrangeAndForm(List<TFrontPlanForm> frontPlanForms);

    void updateById(TFrontPlanForm tFrontPlanForm);
}