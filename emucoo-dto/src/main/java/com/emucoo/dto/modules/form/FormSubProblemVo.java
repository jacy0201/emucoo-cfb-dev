package com.emucoo.dto.modules.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class FormSubProblemVo {
    @ApiModelProperty(value = "机会点数组", name = "subProblemChanceArray")
    private List<FormChanceVo> subProblemChanceArray;
    @ApiModelProperty(value = "子题id", name = "subProblemID", required = true, example = "1")
    private Long subProblemID;
    @ApiModelProperty(value = "子题名称", name = "subProblemName", required = true, example = "a.披萨1")
    private String subProblemName;
    @ApiModelProperty(value = "子题得分", name = "subProblemScore", example = "1")
    private Integer subProblemScore;
    @ApiModelProperty(value = "子题总分", name = "subProblemTotal", example = "5")
    private Integer subProblemTotal;
}
