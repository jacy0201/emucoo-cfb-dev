package com.emucoo.dto.modules.shop;

import lombok.Data;

import java.util.List;

@Data
public class ShopPlanListVO {
    private List<PatrolShopCycle> patrolShopCycle;
    private List<PrecinctArr> precinctArr;
}
