package com.emucoo.service.shop;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.shop.PlanArrangeAddIn;
import com.emucoo.dto.modules.shop.PlanShopAddListVO;
import com.emucoo.dto.modules.shop.ShopPlanProgressOut;
import com.emucoo.model.CheckSheet;
import com.emucoo.model.FrontPlan;
import com.emucoo.model.LoopPlan;
import com.emucoo.model.LoopPlanSrc;

import java.util.Date;
import java.util.List;

/**
 * 巡店计划
 * 
 * @author zhangxq
 * @date 2018-03-23
 */
public interface LoopPlanService extends BaseService<LoopPlanSrc> {

    List<CheckSheet> listCheckSheets();

    //获取巡店计划未安排的店面集合
    List<LoopPlanSrc>  getLoopPlanAddList();

    List<FrontPlan> listFrontPlan(Long userId, Date needDate);

    int countFrontPlan(Long userId, Date needDate);
}
