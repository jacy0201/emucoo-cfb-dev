package com.emucoo.dto.modules.shop;

import lombok.Data;

/**
 * 上传巡店时间位置
 * 
 * @author zhangxq
 * @date 2018-03-28
 */
@Data
public class PatrolShopInfoIn {
	private Long patrolShopArrangeID;// 巡店安排ID
	private Long shopID;
	private Long patrolShopStartTime;
	private String patrolShopLocation;
	private String longitude;
	private String latitude;

}
