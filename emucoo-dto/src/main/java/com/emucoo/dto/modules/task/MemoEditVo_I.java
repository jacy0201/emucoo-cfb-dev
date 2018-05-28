package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

@Data
public class MemoEditVo_I {
    private String workID;
    private String subWorkID;
    private String submitToken;
    //备忘标题
    private String taskTitle;
    //备注说明
    private String taskExplain;
    //备注图片
    private List<ImageUrlVo> taskImgArr;
    //开始日期 YYYYMMDD
    private String startDate;
    //开始时间 HHMM
    private String startTime;

    //结束日期 YYYYMMDD
    private String endDate;
    //结束时间 HHMM
    private String endTime;

    //提醒时间 HH:MM
    private String remindTime;

    /**
     * 提醒方式：null(不提醒)；1(开始时间)；2(提前5分钟)；3(提前15分钟)；4(提前30分钟)；
     * 5(提前1小时)；6（提前2小时）；7（提前1天）;
     */
    private Integer remindType;

    //任务抄送人数组
    private List<CCPersonIdVo> ccPersonArray;
    //任务重复方式 :0不重复 1每天 2每周
    private Integer taskRepeatType;
    //taskRepeatType为2时有值,每周几逗号分隔：1,3,5
    private String taskRepeatValue;
    //是否标记为重要工作
    private Boolean isSign;
}
