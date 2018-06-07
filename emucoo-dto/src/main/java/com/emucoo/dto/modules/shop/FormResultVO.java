package com.emucoo.dto.modules.shop;

import lombok.Data;

@Data
public class FormResultVO {
    private Long formId;
    private String formName;
    //报告人
    private String reporterName;
    //机会点个数
    private Integer opptNum;
    //改善任务个数
    private Integer taskNum;
    //打表完成时间
    private Long finishTime;
}
