package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/18.
 */
@Data
public class AssignTaskSubmitVo_I {
    private int workType;
    private String workID;
    private String subID;
    private String workText;
    private List<AssignTaskSubmitImgVo> executeImgArr;
    private double digitalItemValue;
}
