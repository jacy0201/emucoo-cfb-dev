package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TBrandInfo;
import com.emucoo.model.TFrontPlan;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface TFrontPlanMapper extends MyMapper<TFrontPlan> {
    int countTodayPlanOfUser(@Param("userId") Long userId, @Param("today") Date today);

    List<TFrontPlan> listTodayPlanOfUser(@Param("userId") Long userId, @Param("today") Date today);

    void addUnArrangeToPlan(TFrontPlan tFrontPlan);

    List<TFrontPlan> findArrangeListByAreaId(@Param(value = "areaId") Long areaId, @Param(value = "year") String year,
                                             @Param(value = "month") String month, @Param(value = "brandList")List<TBrandInfo> brandInfos);

    List<HashMap<String, Long>> findFinishedArrangeListByForms(@Param(value = "planId") Long planId, @Param(value = "formIds") List<Long> formIds);

    void deleteFrontPlanByIds(List<Long> subIds);

    void updateFrontPlan(TFrontPlan tFrontPlan);

    int uploadArrangeProcess(TFrontPlan tFrontPlan);

    void updateFrontPlanStatus(@Param("status")Integer status, @Param("arrangeId")Long arrangeId);
}