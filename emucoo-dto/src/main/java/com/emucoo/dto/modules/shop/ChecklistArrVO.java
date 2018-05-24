/**
 * 
 */
package com.emucoo.dto.modules.shop;

import lombok.Data;

/**
 * @author Simon
 */
@Data
public class ChecklistArrVO {
	private Long checklistID;
	private Long reportID;
	private String checklistName;
	private Long checklistStatus;
	private Integer checklistType;
}
