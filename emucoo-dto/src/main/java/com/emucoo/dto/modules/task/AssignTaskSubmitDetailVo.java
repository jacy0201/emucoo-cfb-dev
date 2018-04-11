package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/17.
 */
@Data
public class AssignTaskSubmitDetailVo {
    private long taskSubPerID;
    private String taskSubPerHeadUrl;
    private long taskSubTime;
    private String workText;
    private List<ImageUrlVo> executeImgArr;
    private double digitalItemValue;
}
