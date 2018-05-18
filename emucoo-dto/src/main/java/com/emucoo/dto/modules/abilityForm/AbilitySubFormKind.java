package com.emucoo.dto.modules.abilityForm;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
public class AbilitySubFormKind {
    private String kindName;
    private Long kindID;
    private Boolean isDone;
    private Boolean isPass;
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
