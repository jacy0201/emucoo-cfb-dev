package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/17.
 */
@Data
public class AssignTaskReplyVo {
    private long replyID;
    private String replyContent;
    private long replyTime;
    private long answerID;
    private String answerName;
    private String answerHeadUrl;
    private int replyAction;
    private List<ImageUrlVo> replyImgArr;
}
