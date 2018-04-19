package com.emucoo.dto.modules.plan;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrecinctArr {

    private Long precinctID;
    private String precinctName;
    private int shopNum;
    private List<ShopVo> shopArr;

}
