package com.emucoo.dto.modules.report;

import lombok.Data;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class ReportWorkVo {
    private Integer allNum;
    private Integer doneNum;
    private String executorHeadImgUrl;
    private Integer executorID;
    private String executorName;
    private Double passRate;
    private String taskTitle;
    private Integer workID;
    private Integer workType;
}
