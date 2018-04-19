package com.emucoo.dto.modules.plan;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by sj on 2018/4/17.
 */
@Data
public class ShopToPlanIn {
    // 管辖区域ID
    private Long precinctID;
    // 管辖区域ID
    private Long planID;
    // 计划年份
    private String planYear;
    // 计划月份
    private String planMonth;

    private List<ShopVo> shopArr;

}
