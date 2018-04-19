package com.emucoo.dto.modules.plan;

import lombok.Data;

/**
 * Created by sj on 2018/4/17.
 */
@Data
public class ShopVo {
    private Long shopID;
    private String shopName;
    private String brandName;
    private Integer shopStatus;
    private Long subID;
    private Long patrolShopArrangeID;
    private String exPatrloShopArrangeDate;
}
