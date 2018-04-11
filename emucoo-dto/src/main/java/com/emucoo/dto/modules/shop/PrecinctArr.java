package com.emucoo.dto.modules.shop;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrecinctArr {

    private int precinctID;
    private String precinctName;
    private int shopNum;
    private List<Shop> shopArr=new ArrayList<>();

    @Data
    public static class Shop {
        private Long shopID;
        private String shopName;
        private Integer shopStatus;
        private Long subID;
        private Long patrolShopArrangeID;
        private String exPatrloShopArrangeDate;
    }


}
