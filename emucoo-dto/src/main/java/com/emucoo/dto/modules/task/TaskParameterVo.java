package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

@Data
public class TaskParameterVo {

    @Data
    public static class IdNamePair {
        private Long id;
        private String name;

    }

    @Data
    public static class DeptPosition {
        private IdNamePair dept;
        private List<IdNamePair> positions;

    }

    @Data
    public static class TaskOption {
        private Long id;
        private String name;
        private String score;
        private String weight;
        private Boolean needFeedbackText;
        private String feedbackTextName;
        private String feedbackTextDescription;
        private Boolean needFeedbackNum;
        private String feedbackNumName;
        private Integer feedbackNumType;
        private Boolean needFeedbackImg;
        private String feedbackImgName;
        private Integer feedbackImgCount;
        private Integer feedbackImgType;
        private String feedbackImgSampleUrl;

    }

    private Long id;
    private String name;
    private String description;
    private Boolean use;
    private List<IdNamePair> labels;
    private List<ImageUrl> imgUrls;

    private Integer exeCloseHour;
    private Integer exeCloseMinute;
    private Integer exeRemindHour;
    private Integer exeRemindMinute;
    private Integer auditCloseHour;
    private Integer auditCloseMinute;

    private Integer exeUserType;
    private List<DeptPosition> exeDeptPositions;
    private List<IdNamePair> exeUserShops;

    private Long auditDeptId;
    private String auditDeptName;

    private List<DeptPosition> ccUserPositions;

    private Integer scoreType;
    private String scoreValue;
    private String scoreWeight;

    private List<TaskOption> taskOptions;

    private Integer durationType;
    private String durationBegin;
    private String durationEnd;

    private Integer repeatType;
    private Integer intervalDays;
    private List<Integer> days;


}
