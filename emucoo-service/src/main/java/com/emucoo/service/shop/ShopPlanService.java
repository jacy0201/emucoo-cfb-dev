package com.emucoo.service.shop;

import com.emucoo.dto.modules.shop.ShopPlanListVO;
import com.emucoo.dto.modules.shop.ShopPlanProgressVO;

public interface ShopPlanService {
    ShopPlanListVO getShopPlanList(String month);

    ShopPlanProgressVO getShopPlanProgress(Long id);
}
