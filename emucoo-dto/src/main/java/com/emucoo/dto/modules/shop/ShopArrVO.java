package com.emucoo.dto.modules.shop;

import lombok.Data;

import java.util.List;

/**
 * @author Simon
 *
 */
@Data
public class ShopArrVO {
	private Long shopID;
	private String shopName;
	private String postscript;
	private Long exPatrloShopArrangeTime;
	private int shopStatus;
	private List<ChecklistArrVO> checklistArr;
	private Long patrolShopStartTime;
	private String patrolShopLocation;
	private List<ReplyArrVO> replyArr;
	private String longitude;
	private String latitude;
	private Long remindType;
	private String remindName;

	
}
