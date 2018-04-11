package com.emucoo.dto.modules.shop;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Shop {
	private Long shopID;
	private Long subID;
	private Long exPatrloShopArrangeTime;
	private Integer remindType;
	private List<CheckList> checklistArr = new ArrayList<CheckList>();
}
