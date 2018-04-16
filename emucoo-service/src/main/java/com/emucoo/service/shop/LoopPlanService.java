package com.emucoo.service.shop;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.TFrontPlan;
import com.emucoo.model.TLoopPlan;

import java.util.Date;
import java.util.List;

/**
 * 巡店计划
 * 
 * @author zhangxq
 * @date 2018-03-23
 */
public interface LoopPlanService extends BaseService<TLoopPlan> {

    int countFrontPlan(Long userId, Date needDate);

    List<TFrontPlan> listFrontPlan(Long submitUserId, Date needDate);
}
