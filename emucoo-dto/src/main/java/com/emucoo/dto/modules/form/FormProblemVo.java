package com.emucoo.dto.modules.form;

import com.emucoo.dto.modules.abilityForm.ProblemImg;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class FormProblemVo {
    private List<FormChanceVo> chanceArray;
    private Boolean isImportant;
    private Boolean isNA;
    private Boolean isScore;
    private List<FormChanceVo> otherChanceArray;
    private String problemDescription;
    private Long problemID;
    private String problemName;
    private Integer problemScore;
    private Integer problemTotal;
    private Integer problemType;
    private List<FormSubProblemVo> subProblemArray;
    private List<ProblemImg> descImgArr;
    private List<FormSubProblemUnitVo> subProblemUnitArray;
}
