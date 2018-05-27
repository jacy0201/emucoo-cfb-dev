package com.emucoo.dto.modules.calendar;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value="待办任务对象")
public class CalendarVO {
        private String date;
        private List<Task> taskList = new ArrayList<>();
        private List<Inspection> inspectionList = new ArrayList<>();
        private List<Memo> memoList =new ArrayList<>();

        @Data
        @ApiModel(value="任务数据集")
        public static class Task{
            private String workID;
            private String subID;
            private String taskTitle;
            private Integer taskStatus;
            private Integer taskResult;
            private Long taskDeadline;
            //任务类型：1-常规任务 2-指派任务 3-改善任务  4-巡店安排；5-工作备忘
            private Integer workType;
            private String taskSubHeadUrl;
            private String taskSubName;

        }
        @Data
        @ApiModel(value="巡店数据集")
        public static class Inspection{
            private String workID;
            private Integer workType=4;
            private String inspTitle;
            private Long inspStartTime;
            private Long inspEndTime;
            private Integer inspStatus;
        }
        @Data
        @ApiModel(value="备忘数据集")
        public static class Memo{
            private String workID;
            private String subID;
            private Integer workType=5;
            private String memoTitle;
            private String memoContent;
            private Long memoStartTime;
            private Long memoEndTime;
            private Boolean isSign;
        }
}
