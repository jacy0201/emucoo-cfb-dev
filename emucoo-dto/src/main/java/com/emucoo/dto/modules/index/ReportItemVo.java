package com.emucoo.dto.modules.index;

import lombok.Data;

/**
 * Created by tombaby on 2018/3/16.
 */
@Data
public class ReportItemVo {
    private Long reportID;
    private Boolean isRead;
    private String reportTitle;
    private String reporterHeadUrl;
    private String reporterName;
    private Integer reportType;
    private Integer reportSourceType;
    private String reportSourceName;
    private Long reportTime;
}
