package com.emucoo.dto.modules.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author river
 * @date 2018/3/8 17:05
 */
@Data
@ApiModel(value="待办任务对象")
public class WorkVo_O {
    private String date;
    private List<Work> workArr;
    private long backTime;

    @Data
    @ApiModel(value="Work子对象")
    public static class Work{
        @ApiModelProperty(value="id",name="id",required = true )
        private Long id = 0L;
        @ApiModelProperty(value="workID",name="workID",required = true )
        private String workID;
        @ApiModelProperty(value="subID",name="subID",required = true )
        private String subID;
        //任务类型：1-常规任务 2-指派任务 3-改善任务  4-巡店安排；5-工作备忘
        @ApiModelProperty(value="任务类型",name="workType",required = true ,notes = "1-常规任务 2-指派任务 3-改善任务  4-巡店安排；5-工作备忘, 7-维修任务")
        private Integer workType = 0;
        private Task task = new Task();
        private Inspection inspection = new Inspection();
        private Memo memo = new Memo();

        @Data
        @ApiModel(value="任务数据集")
        public class Task{
            private String taskTitle;
            private Integer taskStatus = 0;
            private Integer taskResult = 0;
            private Integer taskSourceType = 0;
            private String taskSourceName;
            private Long taskDeadline = 0L;
            private String taskSubHeadUrl;
            private String taskSubName;
        }
        @Data
        @ApiModel(value="巡店数据集")
        public class Inspection{
            private String inspTitle;
            private Long inspStartTime = 0L;
            private Long inspEndTime = 0L;
            private Integer inspStatus = 0;
        }
        @Data
        @ApiModel(value="备忘数据集")
        public class Memo{
            private String memoTitle;
            private String memoContent;
            private Long memoStartTime = 0L;
            private Long memoEndTime = 0L;
            private Boolean isSign = false;
        }
    }

}
