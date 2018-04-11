/**
 * 
 */
package com.emucoo.dto.modules.shop;

/**
 * @author Simon
 */
public class ChecklistArrVO {
	private long checklistID;
	private long reportID;
	private String checklistName;
	private long checklistStatus;
	/**
	 * @return the checklistID
	 */
	public long getChecklistID() {
		return checklistID;
	}
	/**
	 * @param checklistID the checklistID to set
	 */
	public void setChecklistID(long checklistID) {
		this.checklistID = checklistID;
	}
	/**
	 * @return the checklistName
	 */
	public String getChecklistName() {
		return checklistName;
	}
	/**
	 * @param checklistName the checklistName to set
	 */
	public void setChecklistName(String checklistName) {
		this.checklistName = checklistName;
	}

	public long getChecklistStatus() {
		return checklistStatus;
	}

	public void setChecklistStatus(long checklistStatus) {
		this.checklistStatus = checklistStatus;
	}

	public long getReportID() {
		return reportID;
	}

	public void setReportID(long reportID) {
		this.reportID = reportID;
	}
}
