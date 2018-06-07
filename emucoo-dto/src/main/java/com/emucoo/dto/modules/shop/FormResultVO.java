package com.emucoo.dto.modules.shop;

import lombok.Data;

import java.util.Date;

@Data
public class FormResultVO {
    //报告id
    private Long reportId;
    //表单id
    private Long formId;
    //表单名称
    private String formName;
    //报告人
    private String reporterName;
    //机会点个数
    private Integer opptNum;
    //打表时间
    private Date checkFormTime;
    //总分得分率
    private Integer scoreRate;

}
