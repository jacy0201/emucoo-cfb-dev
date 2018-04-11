package com.emucoo.dto.modules.task;

import lombok.Data;

@Data
public class TaskTmplVo {
    private String name;
    private String description;
    /**
     * 方案ID
     */
    private Long designId;

}
