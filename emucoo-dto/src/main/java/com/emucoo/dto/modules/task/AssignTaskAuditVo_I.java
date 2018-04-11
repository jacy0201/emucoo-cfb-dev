package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/18.
 */
@Data
public class AssignTaskAuditVo_I {
    private int workType;
    private String workID;
    private String subID;
    private int reviewResult;
    private String reviewOpinion;
    private List<ImageUrlVo> reviewImgArr;
}
