package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/17.
 */
@Data
public class AssignTaskStateVo {
    private long backTime;
    private int taskStatus;
    private String taskTitle;
    private String taskExplain;
    private List<ImageUrlVo> taskImgArr;
    private String startDate;
    private String endDate;
    private long submitDeadline;
    private long reviewDeadline;
    private long executorID;
    private String executorName;
    private long auditorID;
    private String auditorName;
    private String ccPersonNames;
    private int taskRepeatType;
    private String taskRepeatValue;
    private int taskRank;
    private int feedbackText;
    private int feedbackImg;
    private String digitalItemName;
    private int digitalItemType;
}
