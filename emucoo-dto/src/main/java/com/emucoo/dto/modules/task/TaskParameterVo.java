package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

@Data
public class TaskParameterVo {

    @Data
    public class IdNamePair {
        private Long id;
        private String name;
    }

    @Data
    public class DeptPosition {
        private IdNamePair dept;
        private List<IdNamePair> positions;
    }

    @Data
    public class TaskOption {
        private Long id;
        private String name;
        private String score;
        private String weight;
        private Boolean needFeedbackText;
        private String feedbackTextName;
        private String feedbackTextContent;
        private Boolean needFeedbackNum;
        private String feedbackNumName;
        private Integer feedbackNumType;
        private Boolean needFeedbackImg;
        private Integer feedbackImgCount;
        private Integer feedbackImgType;
        private Long feedbackImgSampleId;
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
