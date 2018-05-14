package com.emucoo.dto.modules.task;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/8 17:05
 */
@Data
public class WorkVo_O {
    private String date;
    private List<Work> workArr;
    private Date backTime;

    @Data
    public static class Work{
        private Long id;
        private String workID;
        private String subID;
        //任务类型：1-常规任务 2-指派任务 3-改善任务  4-巡店安排；5-工作备忘
        private Integer workType;
        private Task task = new Task();
        private Inspection inspection = new Inspection();
        private Memo memo = new Memo();

        @Data
        public class Task{
            private String taskTitle;
            private Integer taskStatus;
            private Integer taskResult;
            private Integer taskSourceType;
            private String taskSourceName;
            private Date taskDeadline;
            private String taskSubHeadUrl;
            private String taskSubName;
        }
        @Data
        public class Inspection{
            private String inspTitle;
            private Date inspStartTime;
            private Date inspEndTime;
            private Integer inspStatus;
        }
        @Data
        public class Memo{
            private String memoContent;
            private Date memoStartTime;
            private Date memoEndTime;
            private Integer importStatus;
        }
    }

}
