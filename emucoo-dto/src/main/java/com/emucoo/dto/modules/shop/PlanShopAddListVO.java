package com.emucoo.dto.modules.shop;
import lombok.Data;

import java.util.List;


/**
 * @author: jacy
 * @description:可添加巡店计划店面
 */

@Data
public class PlanShopAddListVO {

    private List<PlanShop> shopList;

    @Data
    public static class PlanShop{
        //店面ID
        private Integer shopID;

        //店面名称
        private String  shopName;

    }

}
