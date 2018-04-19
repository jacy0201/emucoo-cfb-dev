package com.emucoo.dto.modules.plan;

import lombok.Data;

import java.util.List;

@Data
public class FindPlanListOut {
    private List<PatrolShopCycle> patrolShopCycle;
    private List<PrecinctArr> precinctArr;
}
