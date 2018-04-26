package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFrontPlanForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFrontPlanFormMapper extends MyMapper<TFrontPlanForm> {

    void addRelationToArrangeAndForm(List<TFrontPlanForm> frontPlanForms);

    void updateById(TFrontPlanForm tFrontPlanForm);

    void updateStatusByFormAndArrange(@Param("status") int status, @Param("arrangeId") Long patrolShopArrangeID,
                                      @Param("formId") Long formId, @Param("reportId")Long reportId);
}