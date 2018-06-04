package com.emucoo.dto.modules.statistics.plan;

import lombok.Data;

/**
 * Created by sj on 2018/5/31.
 */
@Data
public class GeneratePlanReportIn {
    private String beginDate;
    private String endDate;
    private Integer formType;
    private Long formId;
}
