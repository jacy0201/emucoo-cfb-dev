package com.emucoo.dto.modules.shop;

import java.util.List;

/**
 * @author Simon
 *
 */
public class ShopArrVO {
	private Long shopID;
	private String shopName;
	private String postscript;
	private long exPatrloShopArrangeTime;
	private String shopStatus;
	private List<ChecklistArrVO> checklistArr;
	private long patrolShopStartTime;
	private String patrolShopLocation;
	private List<ReplyArrVO> replyArr;
	private Double longitude;
	private Double latitude;
	private long reportId;
	private long remindType;
	private String remindName;
	private long subId;
	
	
	/**
	 * @return the subId
	 */
	public long getSubId() {
		return subId;
	}
	/**
	 * @param subId the subId to set
	 */
	public void setSubId(long subId) {
		this.subId = subId;
	}
	/**
	 * @return the remindType
	 */
	public long getRemindType() {
		return remindType;
	}
	/**
	 * @param remindType the remindType to set
	 */
	public void setRemindType(long remindType) {
		this.remindType = remindType;
	}
	/**
	 * @return the remindName
	 */
	public String getRemindName() {
		return remindName;
	}
	/**
	 * @param remindName the remindName to set
	 */
	public void setRemindName(String remindName) {
		this.remindName = remindName;
	}
	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the reportId
	 */
	public long getReportId() {
		return reportId;
	}
	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	/**
	 * @return the shopID
	 */
	public Long getShopID() {
		return shopID;
	}
	/**
	 * @param shopID the shopID to set
	 */
	public void setShopID(Long shopID) {
		this.shopID = shopID;
	}
	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}
	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * @return the postscript
	 */
	public String getPostscript() {
		return postscript;
	}
	/**
	 * @param postscript the postscript to set
	 */
	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}
	/**
	 * @return the exPatrloShopArrangeTime
	 */
	public long getExPatrloShopArrangeTime() {
		return exPatrloShopArrangeTime;
	}
	/**
	 * @param exPatrloShopArrangeTime the exPatrloShopArrangeTime to set
	 */
	public void setExPatrloShopArrangeTime(long exPatrloShopArrangeTime) {
		this.exPatrloShopArrangeTime = exPatrloShopArrangeTime;
	}
	/**
	 * @return the shopStatus
	 */
	public String getShopStatus() {
		return shopStatus;
	}
	/**
	 * @param shopStatus the shopStatus to set
	 */
	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}
	/**
	 * @return the checklistArr
	 */
	public List<ChecklistArrVO> getChecklistArr() {
		return checklistArr;
	}
	/**
	 * @param checklistArr the checklistArr to set
	 */
	public void setChecklistArr(List<ChecklistArrVO> checklistArr) {
		this.checklistArr = checklistArr;
	}
	/**
	 * @return the patrolShopStartTime
	 */
	public long getPatrolShopStartTime() {
		return patrolShopStartTime;
	}
	/**
	 * @param patrolShopStartTime the patrolShopStartTime to set
	 */
	public void setPatrolShopStartTime(long patrolShopStartTime) {
		this.patrolShopStartTime = patrolShopStartTime;
	}
	/**
	 * @return the patrolShopLocation
	 */
	public String getPatrolShopLocation() {
		return patrolShopLocation;
	}
	/**
	 * @param patrolShopLocation the patrolShopLocation to set
	 */
	public void setPatrolShopLocation(String patrolShopLocation) {
		this.patrolShopLocation = patrolShopLocation;
	}
	/**
	 * @return the replyArr
	 */
	public List<ReplyArrVO> getReplyArr() {
		return replyArr;
	}
	/**
	 * @param replyArr the replyArr to set
	 */
	public void setReplyArr(List<ReplyArrVO> replyArr) {
		this.replyArr = replyArr;
	}
	
}
