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
public class PlanArrangeAddIn {

	private Long precinctID;
	private Long planID;
	private String exPatrloShopArrangeDate;
	private String postscript;
	private List<Shop> shopArr = new ArrayList<Shop>();

}
