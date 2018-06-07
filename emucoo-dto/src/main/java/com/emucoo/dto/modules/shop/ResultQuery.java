package com.emucoo.dto.modules.shop;

import lombok.Data;

@Data
public class ResultQuery {
    private Long shopId;
    private String startMonth;
    private String endMonth;
    private Long deptId;
    private Long formId;
    private String formName;
}
