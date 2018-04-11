package com.emucoo.dto.modules.shop;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 计划安排创建
 * 
 * @author zhangxq
 * @date 2018-03-26
 */
@Data
public class PlanArrangeEditIn {

	private Long patrolShopArrangeID;// 巡店安排ID
	private String exPatrloShopArrangeDate;
	private String postscript;
	private List<Shop> shopArr = new ArrayList<Shop>();

}
