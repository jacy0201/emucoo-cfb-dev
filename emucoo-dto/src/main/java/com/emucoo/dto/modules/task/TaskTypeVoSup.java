package com.emucoo.dto.modules.task;

import lombok.Data;

/**
 * @author river
 * @date 2018/2/23 17:42
 */
@Data
public class TaskTypeVoSup {

    /**
     * 是否支持前端创建任务0：支持1：不支持
     */
    private Boolean isSupportFrontCreate;

    /**
     * 是否支持机会点任务0：支持1：不支持
     */
    private Boolean isSupportOpportCreate;

    /**
     * 是否支持打包计划0：支持1：不支持
     */
    private Boolean isSupportPakagePlan;

    /**
     * 是否支持工作流0：支持1：不支持
     */
    private Boolean isSupportActivity;
}
