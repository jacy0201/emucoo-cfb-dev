package com.emucoo.dto.modules.shop;

import lombok.Data;

@Data
public class ShopPlanProgressVO {
    private int brandID;
    private String brandName;
    private Long departmentID;
    private String departmentName;
    private String progress;
    private int progressStatus;

}
