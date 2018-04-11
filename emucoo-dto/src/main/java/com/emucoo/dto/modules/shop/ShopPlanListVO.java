package com.emucoo.dto.modules.shop;

import lombok.Data;

import java.util.List;

@Data
public class ShopPlanListVO {

    //当前月份
    private String presentMonth;
    private List<PatrolShopCycle> patrolShopCycle;
    private List<PrecinctArr> precinctArr;
}
