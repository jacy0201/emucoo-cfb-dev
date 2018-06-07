package com.emucoo.dto.modules.shop;

import lombok.Data;

@Data
public class ShopVO {
	private Long shopId;
	private String shopName;
	private Long shopManagerId;
	private String shopManager;
	private String address;
	private String tel;
}
