package com.emucoo.dto.modules.shop;

import java.util.List;

/**
 * @author Simon
 *
 */
public class PatrolShopArrangeDetailVO {
	private String exPatrloShopArrangeDate;
	private List<ShopArrVO> shopArr;
	/**
	 * @return the exPatrloShopArrangeDate
	 */
	public String getExPatrloShopArrangeDate() {
		return exPatrloShopArrangeDate;
	}
	/**
	 * @param exPatrloShopArrangeDate the exPatrloShopArrangeDate to set
	 */
	public void setExPatrloShopArrangeDate(String exPatrloShopArrangeDate) {
		this.exPatrloShopArrangeDate = exPatrloShopArrangeDate;
	}
	/**
	 * @return the shopArr
	 */
	public List<ShopArrVO> getShopArr() {
		return shopArr;
	}
	/**
	 * @param shopArr the shopArr to set
	 */
	public void setShopArr(List<ShopArrVO> shopArr) {
		this.shopArr = shopArr;
	}
	
}
