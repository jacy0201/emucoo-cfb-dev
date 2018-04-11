package com.emucoo.dto.modules.report;

import lombok.Data;

/**
 * @author :liuhp115
 * @create :2018-03-30 13:05.
 */
@Data
public class GetReportListOut {
    private Long reportID;
    private String reporter;
    private String dptName;
    private String createDate;
    private String sheetName;

}
