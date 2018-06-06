package com.emucoo.dto.modules.center;

import lombok.Data;

import java.util.Date;

@Data
public class RepairWorkVO {
    private Long id;
    private Long problemId;
    private String problemName;
    private Long shopId;
    private String shopName;
    private Date createTime;
    //验收结果： 1=合格， 2=不合格'
    private Integer reviewResult;
    //维修任务状态：1=待确定维修日期， 2=已经预约日期， 3=超时，4= 完成维修， 5= 验收完成， 6=验收超时'
    private Integer workStatus;
}
