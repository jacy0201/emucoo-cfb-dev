package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/17.
 */
@Data
public class AssignTaskDetailVo_O {
    private AssignTaskStateVo taskStatement;
    private AssignTaskSubmitDetailVo taskSubmit;
    private AssignTaskReviewVo taskReview;
    private List<AssignTaskReplyVo> taskReplyArr;

}
