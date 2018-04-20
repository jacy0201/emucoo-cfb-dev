package com.emucoo.service.plan;

import com.emucoo.dto.modules.plan.FindPlanListIn;
import com.emucoo.dto.modules.plan.FindPlanListOut;
import com.emucoo.dto.modules.plan.FindShopListIn;
import com.emucoo.dto.modules.plan.FindShopListOut;
import com.emucoo.dto.modules.plan.PlanProgressOut;
import com.emucoo.dto.modules.plan.ShopToPlanIn;
import com.emucoo.dto.modules.shop.ShopPlanListVO;
import com.emucoo.dto.modules.shop.ShopPlanProgressVO;
import com.emucoo.model.SysUser;

import java.util.List;

/**
 * 巡店计划
 * @author Jacy
 *
 */
public interface PlanService {
    FindShopListOut findShopList(SysUser user, FindShopListIn findShopListIn);

    void addShopToPlan(SysUser user, ShopToPlanIn addShopToPlanIn);

    void deleteShopInPlan(ShopToPlanIn shopToPlanIn);

    FindPlanListOut listPlan(SysUser user, FindPlanListIn findPlanListIn);

    PlanProgressOut findPlanProcessByUserId(SysUser user);

   /* *//**
     * 获取巡店计划列表
     * @param month
     * @return
     *//*
    ShopPlanListVO getShopPlanList(String month);

    *//**
     * 获取当前月巡店计划进度
     * @param userId
     * @return
     *//*
    ShopPlanProgressVO getShopPlanProgress(Long userId);*/

}
