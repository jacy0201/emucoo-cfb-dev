package com.emucoo.service.shop;

import com.emucoo.dto.modules.shop.ShopPlanListVO;
import com.emucoo.dto.modules.shop.ShopPlanProgressVO;

/**
 * 巡店计划
 * @author Jacy
 *
 */
public interface ShopPlanService {

    /**
     * 获取巡店计划列表
     * @param month
     * @return
     */
    ShopPlanListVO getShopPlanList(String month);

    /**
     * 获取当前月巡店计划进度
     * @param userId
     * @return
     */
    ShopPlanProgressVO getShopPlanProgress(Long userId);

}
