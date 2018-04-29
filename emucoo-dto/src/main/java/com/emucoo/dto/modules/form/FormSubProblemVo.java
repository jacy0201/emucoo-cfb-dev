package com.emucoo.dto.modules.form;

import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class FormSubProblemVo {
    private List<FormChanceVo> subProblemChanceArray;
    private Long subProblemID;
    private String subProblemName;
    private Integer subProblemScore;
    private Integer subProblemTotal;
}
