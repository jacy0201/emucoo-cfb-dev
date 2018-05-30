package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

@Data
public class MemoDetailVo_O {
    //任务类型：5-工作备忘
    private Integer workType=5;
    private String workID;
    private String subID;
    private Integer memoStatus;
    //备忘标题
    private String taskTitle;
    //备注说明
    private String taskExplain;
    //备注图片
    private List<ImageUrlVo> taskImgArr;
    //开始时间
    private Long startDateTime;
    //结束时间
    private Long endDateTime;

    /**
     * 提醒方式：1(开始时间)；2(提前15分钟)；3(提前30分钟)；
     * 4(提前1小时)；5（提前2小时）；6（提前1天）;7 无
     */
    private Integer remindType;

    private String remindName;

    //任务抄送人
    private List<CCPerson> ccPersonList;
    //任务重复方式 :0不重复 1每天 2每周
    private Integer taskRepeatType;
    //taskRepeatType为2时有值,每周几逗号分隔：1,3,5
    private String taskRepeatValue;
    //是否标记为重要工作
    private Boolean isSign;


    @Data
    public static class CCPerson {
        private Long ccPersonID;
        private String ccPersonName;
        private String headImgUrl;
    }
}
