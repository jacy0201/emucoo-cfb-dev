package com.emucoo.dto.modules.report;


import java.util.Date;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/8 18:45
 */

@Data
public class ReportVo {
    private Integer reportID;
    private Boolean isRead;
    private Long userId;
    private String reportTitle;
    private String reporterHeadUrl;
    private String reporterName;
    private Integer reportSourceType;
    private String reportSourceName;
    private Date reportTime;
}
