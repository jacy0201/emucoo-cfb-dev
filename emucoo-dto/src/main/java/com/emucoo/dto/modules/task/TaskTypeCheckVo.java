package com.emucoo.dto.modules.task;

import org.springframework.validation.annotation.Validated;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/1 19:22
 */
@Data
public class TaskTypeCheckVo {

    @NotNull(message = "task type id 不能为空")
    private Long taskTypeId;

    private Long cmptId;

    private String checkOpt;
    /**
     * check排序，越小越靠前
     */
    private Integer checkOrder;
}
