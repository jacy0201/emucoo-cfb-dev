package com.emucoo.dto.modules.abilityForm;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
public class AbilitySubForm {
    private String subFormName;
    private Long subFormID;
    private Boolean isUsable;
    private Boolean isDone;
    private Boolean isPass;
    private List<AbilitySubFormKind> subFormKindArray;

    public void setSubFormName(String subFormName) {
        this.subFormName = subFormName;
    }

    public String getSubFormName() {
        return subFormName;
    }


    public void setIsUsable(Boolean isUsable) {
        this.isUsable = isUsable;
    }

    public Boolean getIsUsable() {
        return isUsable;
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

    public List<AbilitySubFormKind> getSubFormKindArray() {
        return subFormKindArray;
    }

    public void setSubFormKindArray(List<AbilitySubFormKind> subFormKindArray) {
        this.subFormKindArray = subFormKindArray;
    }

    public Long getSubFormID() {
        return subFormID;
    }

    public void setSubFormID(Long subFormID) {
        this.subFormID = subFormID;
    }
}
