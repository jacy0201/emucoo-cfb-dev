package com.emucoo.dto.modules.plan;

import lombok.Data;

import java.util.List;

@Data
public class FindPlanListOut {
    private String presentMonth;
    private List<PatrolShopCycle> patrolShopCycle;
    private List<PrecinctArr> precinctArr;
}
