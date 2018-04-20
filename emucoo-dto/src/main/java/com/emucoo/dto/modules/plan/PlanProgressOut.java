package com.emucoo.dto.modules.plan;

import lombok.Data;

@Data
public class PlanProgressOut {
    private Long brandID;
    private String brandName;
    private Long departmentID;
    private String departmentName;
    private float progress;
    private int progressStatus;

}
