package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 *
 * 涂鸦执行照片
 */
@Data
public class ExecuteImgIn {
    private String workType;
    private String workID;
    private String subID;
    private String taskItemID;
    private List<ExecuteImg> executeImgArr;

    @Data
    public static  class ExecuteImg{
        private String imgUrl;
    }
}
