package com.emucoo.dto.modules.center;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="报告对象")
public class ReportVO {
    //报告id
    private Long reportId;
    //店铺名称
    private String shopName;
    //打表人
    private String reporterName;
    //报告类型 :1RVR类表，2能力模型表
    private Byte formType;
    //打表日期
    private Long checkFormTime;
    //报告名称：店铺名称+formType+评估报告
    private String reportName;



}
