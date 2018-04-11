package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/17.
 */
@Data
public class AssignTaskReviewVo {
    private int reviewResult = 0;
    private long reviewID = 0L;
    private String reviewOpinion = "";
    private long reviewTime = 0L;
    private long auditorID = 0L;
    private String auditorName = "";
    private String auditorHeadUrl = "";
    private List<ImageUrlVo> reviewImgArr;
}
