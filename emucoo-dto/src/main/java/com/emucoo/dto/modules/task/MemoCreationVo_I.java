package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

@Data
public class MemoCreationVo_I {
    private String submitToken;
    private String taskTitle;
    private String taskExplain;
    private List<ImageUrlVo> taskImgArr;
    private String startDate;
    private String endDate;
    private String submitDeadlineRule;
    private List<ExecutorIdVo> executorArray;
    private long auditorID;
    private List<CCPersonIdVo> ccPersonArray;
    private int taskRepeatType;
    private String taskRepeatValue;
    private int taskRank;
    private int feedbackText;
    private int feedbackImg;
    private String digitalItemName;
    private int digitalItemType;
}
