package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFrontPlan;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TFrontPlanMapper extends MyMapper<TFrontPlan> {
    int countTodayPlanOfUser(@Param("userId") Long userId, @Param("today") Date today);

    List<TFrontPlan> listTodayPlanOfUser(@Param("userId") Long userId, @Param("today") Date today);
}