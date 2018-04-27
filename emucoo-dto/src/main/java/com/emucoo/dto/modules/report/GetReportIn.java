package com.emucoo.dto.modules.report;

import lombok.Data;

/**
 * @author :liuhp115
 * @create :2018-03-29 09:58.
 */
@Data
public class GetReportIn {
    private Long reportID;

    private Long patrolShopArrangeID;
    private Long shopID;
    private Long checklistID;
}
