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
    private Long executorID;
    private String executorName;
    private Float passRate;
    private String taskTitle;
    private String workID;
    private Integer workType;
}
