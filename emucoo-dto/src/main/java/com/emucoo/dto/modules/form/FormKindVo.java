package com.emucoo.dto.modules.form;

import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class FormKindVo {
    private Boolean isDone;
    private Long kindID;
    private String kindName;
    private List<FormProblemVo> problemArray;
    private Integer problemNum;
    private Float scoreRate;
    private Integer wrongNum;

}
