package com.emucoo.dto.modules.abilityForm;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
public class ProblemVo {
    private String problemName;
    private Long problemID;
    private String checkMode;
    private String notes;
    private String problemDescription;
    private Boolean isDone;
    private Boolean isPass;
    private Boolean isNA;
    private Boolean isSubProblem;
    private Boolean isSubList;
    private List<SubProblemVo> subProblemArray;
    private List<ProblemChanceVo> chanceArray;
    private List<ProblemChanceVo> otherChanceArray;
    private List<ProblemImg> descImgArr;
    private AbilitySubForm subListObject;

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getProblemName() {
        return problemName;
    }

    public Long getProblemID() {
        return problemID;
    }

    public void setProblemID(Long problemID) {
        this.problemID = problemID;
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

    public Boolean getIsNA() {
        return isNA;
    }

    public void setIsNA(Boolean isNA) {
        this.isNA = isNA;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
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

    public void setIsSubProblem(Boolean isSubProblem) {
        this.isSubProblem = isSubProblem;
    }

    public Boolean getIsSubProblem() {
        return isSubProblem;
    }

    public void setIsSubList(Boolean isSubList) {
        this.isSubList = isSubList;
    }

    public Boolean getIsSubList() {
        return isSubList;
    }

    public List<SubProblemVo> getSubProblemArray() {
        return subProblemArray;
    }

    public void setSubProblemArray(List<SubProblemVo> subProblemArray) {
        this.subProblemArray = subProblemArray;
    }

    public AbilitySubForm getSubListObject() {
        return subListObject;
    }

    public void setSubListObject(AbilitySubForm subListObject) {
        this.subListObject = subListObject;
    }

    public List<ProblemChanceVo> getChanceArray() {
        return chanceArray;
    }

    public void setChanceArray(List<ProblemChanceVo> chanceArray) {
        this.chanceArray = chanceArray;
    }

    public List<ProblemChanceVo> getOtherChanceArray() {
        return otherChanceArray;
    }

    public void setOtherChanceArray(List<ProblemChanceVo> otherChanceArray) {
        this.otherChanceArray = otherChanceArray;
    }

    public List<ProblemImg> getDescImgArr() {
        return descImgArr;
    }

    public void setDescImgArr(List<ProblemImg> descImgArr) {
        this.descImgArr = descImgArr;
    }
}
