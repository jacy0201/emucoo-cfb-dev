package com.emucoo.dto.modules.index;

import lombok.Data;

/**
 * Created by tombaby on 2018/3/16.
 */
@Data
public class ReportItemVo {
    private long reportID;
    private boolean isRead;
    private String reportTitle;
    private String reporterHeadUrl;
    private String reporterName;
    private int reportSourceType;
    private String reportSourceName;
    private long reportTime;
}
