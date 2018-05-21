package com.emucoo.dto.modules.abilityForm;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
public class SubProblemVo {
    private String subProblemName;
    private Long subProblemID;
    private String checkMode;
    private String notes;
    private String description;
    private Boolean isDone;
    private Boolean isPass;
    private Boolean isSubList;
    private List<ProblemChanceVo> subProblemChanceArray;
    private List<ProblemChanceVo> subOtherChanceArray;
    private AbilitySubForm subListObject;

    public void setSubProblemName(String subProblemName) {
        this.subProblemName = subProblemName;
    }

    public String getSubProblemName() {
        return subProblemName;
    }

    public Long getSubProblemID() {
        return subProblemID;
    }

    public void setSubProblemID(Long subProblemID) {
        this.subProblemID = subProblemID;
    }

    public void setCheckMode(String checkMode) {
        this.checkMode = checkMode;
    }

    public String getCheckMode() {
        return checkMode;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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

    public void setIsSubList(Boolean isSubList) {
        this.isSubList = isSubList;
    }

    public Boolean  getIsSubList() {
        return isSubList;
    }

    public List<ProblemChanceVo> getSubProblemChanceArray() {
        return subProblemChanceArray;
    }

    public void setSubProblemChanceArray(List<ProblemChanceVo> subProblemChanceArray) {
        this.subProblemChanceArray = subProblemChanceArray;
    }

    public AbilitySubForm getSubListObject() {
        return subListObject;
    }

    public void setSubListObject(AbilitySubForm subListObject) {
        this.subListObject = subListObject;
    }

    public List<ProblemChanceVo> getSubOtherChanceArray() {
        return subOtherChanceArray;
    }

    public void setSubOtherChanceArray(List<ProblemChanceVo> subOtherChanceArray) {
        this.subOtherChanceArray = subOtherChanceArray;
    }
}
