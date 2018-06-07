package com.emucoo.dto.modules.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
@ApiModel
public class FormKindVo {
    @ApiModelProperty(value = "是否完成", name = "isDone", required = true, example = "true")
    private Boolean isDone;
    @ApiModelProperty(value = "类别id", name = "kindID", required = true, example = "1")
    private Long kindID;
    @ApiModelProperty(value = "类别名称", name = "kindName", required = true, example = "A.出品")
    private String kindName;
    @ApiModelProperty(value = "题项数组", name = "problemArray", required = true)
    private List<FormProblemVo> problemArray;
    @ApiModelProperty(value = "总题目数", name = "problemNum")
    private Integer problemNum;
    @ApiModelProperty(value = "得分率", name = "scoreRate")
    private Float scoreRate;
    @ApiModelProperty(value = "错题数", name = "wrongNum")
    private Integer wrongNum;
    @ApiModelProperty(value = "实际得分", name = "realScore")
    private Integer realScore;
    @ApiModelProperty(value = "实际总分，扣除NA", name = "realTotal")
    private Integer realTotal;

}
