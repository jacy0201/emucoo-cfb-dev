package com.emucoo.dto.modules.task;

import lombok.Data;

@Data
public class RepairWorkParam {

    // 格式是： YYYYMM
    private String date;
    private long shopId;
}
