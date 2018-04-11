package com.emucoo.dto.modules.task;

import com.sun.org.apache.xpath.internal.operations.Bool;

import lombok.Data;

@Data
public class TaskDesignVo {
    private String designName;
    private String designDesc;
    private Boolean isUse;
    /**
     * 包含的组件
     */
    private Integer compDetailId;
}
