package com.emucoo.dto.modules.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="创建工作备忘")
public class MemoCreationVo_I {
    @ApiModelProperty(value="重复提交token",name="submitToken",required = true)
    private String submitToken;
    //备忘标题
    @ApiModelProperty(value="备忘标题",name="taskTitle",required = true)
    private String taskTitle;
    //备注说明
    @ApiModelProperty(value="备注说明",name="taskExplain")
    private String taskExplain;
    //备注图片
    @ApiModelProperty(value="备注图片",name="taskImgArr")
    private List<ImageUrlVo> taskImgArr;
    //开始日期 YYYY-MM-DD
    @ApiModelProperty(value="开始日期",name="startDate",example = "2018-05-30",notes = "YYYY-MM-DD")
    private String startDate;
    //开始时间 HH:mm
    @ApiModelProperty(value="开始时间串",name="startTime",example = "09:30",notes = "HH:mm")
    private String startTime;

    //结束日期 YYYY-MM-DD
    @ApiModelProperty(value="结束日期",name="endDate",example = "2018-05-30",notes = "YYYY-MM-DD")
    private String endDate;
    //结束时间 HH:mm
    @ApiModelProperty(value="结束时间串",name="endTime",example = "09:56",notes = "HH:mm")
    private String endTime;

    //提醒时间 HH:mm
    @ApiModelProperty(value="提醒时间串",name="remindTime",example = "09:56",notes = "HH:mm")
    private String remindTime;

    /**
     * 提醒方式：null(不提醒)；1(开始时间)；2(提前5分钟)；3(提前15分钟)；4(提前30分钟)；
     * 5(提前1小时)；6（提前2小时）；7（提前1天）;
     */
    @ApiModelProperty(value="提醒方式",name="remindType",notes = "null(不提醒)；1(开始时间)；2(提前5分钟)；3(提前15分钟)；4(提前30分钟)；5(提前1小时)；6（提前2小时）；7（提前1天）;")
    private Integer remindType;

    //任务抄送人数组
    private List<CCPersonIdVo> ccPersonArray;
    //任务重复方式 :0不重复 1每天 2每周
    @ApiModelProperty(value="任务重复方式",name="taskRepeatType",notes = "0-不重复;1-每天;2-每周")
    private Integer taskRepeatType;
    @ApiModelProperty(value="任务重复值",name="taskRepeatValue",notes = "taskRepeatType为2时有值,每周几逗号分隔：1,3,5")
    //taskRepeatType为2时有值,每周几逗号分隔：1,3,5
    private String taskRepeatValue;
    //是否标记为重要工作
    @ApiModelProperty(value="是否标记为重要工作",name="isSign",notes = "1-是；0-否")
    private Boolean isSign;
}
