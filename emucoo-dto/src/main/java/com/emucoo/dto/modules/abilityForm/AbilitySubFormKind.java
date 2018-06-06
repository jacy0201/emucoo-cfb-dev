package com.emucoo.dto.modules.abilityForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
@ApiModel
public class AbilitySubFormKind {
    @ApiModelProperty(value = "类别名", name = "kindName", example = "1.根据目标100%完成人员配备")
    private String kindName;
    @ApiModelProperty(value = "类别id", name = "kindID", example = "1")
    private Long kindID;
    @ApiModelProperty(value = "是否完成", name = "isDone", example = "true")
    private Boolean isDone;
    @ApiModelProperty(value = "是否通过", name = "isPass", example = "true")
    private Boolean isPass;
    @ApiModelProperty(value = "题项数组", name = "problemArray")
    private List<ProblemVo> problemArray;

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindName() {
        return kindName;
    }

    public Long getKindID() {
        return kindID;
    }

    public void setKindID(Long kindID) {
        this.kindID = kindID;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setProblemArray(List<ProblemVo> problemArray) {
        this.problemArray = problemArray;
    }

    public List<ProblemVo> getProblemArray() {
        return problemArray;
    }
}
