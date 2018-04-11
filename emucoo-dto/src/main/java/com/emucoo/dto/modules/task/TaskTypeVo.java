package com.emucoo.dto.modules.task;


import lombok.Data;

/**
 * @author river
 * @date 2018/3/2 16:00
 */
@Data
public class TaskTypeVo {
    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型描述
     */
    private String description;

    /**
     * 任务方案ID
     */
    private Long designId;
    /**
     * 任务方案ID
     */
    private String designName;

    /**
     * 是否有效0：启用1：不启用
     */
    private Boolean isUse = false;

    /**
     * 是否支持前端创建任务0：支持1：不支持
     */
    private Boolean isSupportFrontCreate = false;

    /**
     * 是否支持机会点任务0：支持1：不支持
     */
    private Boolean isSupportOpportCreate = false;

    /**
     * 是否支持打包计划0：支持1：不支持
     */
    private Boolean isSupportPakagePlan = false;

    /**
     * 是否支持工作流0：支持1：不支持
     */
    private Boolean isSupportActivity = false;

}
