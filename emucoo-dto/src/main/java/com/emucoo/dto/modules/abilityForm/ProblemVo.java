package com.emucoo.dto.modules.abilityForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
@ApiModel
public class ProblemVo {
    @ApiModelProperty(value = "题项名", name = "problemName", example = "101.门店人员配置")
    private String problemName;
    @ApiModelProperty(value = "题项id", name = "problemID", example = "1")
    private Long problemID;
    @ApiModelProperty(value = "检查方式", name = "checkMode", example = "")
    private String checkMode;
    @ApiModelProperty(value = "检查结果", name = "notes", example = "地板不干净，玻璃脏")
    private String notes;
    @ApiModelProperty(value = "问题描述", name = "problemDescription", example = "")
    private String problemDescription;
    @ApiModelProperty(value = "是否完成", name = "isDone", example = "true")
    private Boolean isDone;
    @ApiModelProperty(value = "是否通过", name = "isPass", example = "true")
    private Boolean isPass;
    @ApiModelProperty(value = "是否NA", name = "isNA", example = "true")
    private Boolean isNA;
    @ApiModelProperty(value = "是否有子题", name = "isSubProblem", example = "true")
    private Boolean isSubProblem;
    @ApiModelProperty(value = "是否有子表", name = "isSubList", example = "true")
    private Boolean isSubList;
    @ApiModelProperty(value = "子题数组", name = "subProblemArray")
    private List<SubProblemVo> subProblemArray;
    @ApiModelProperty(value = "机会点数组", name = "chanceArray")
    private List<ProblemChanceVo> chanceArray;
    @ApiModelProperty(value = "其他机会点数组", name = "otherChanceArray")
    private List<ProblemChanceVo> otherChanceArray;
    @ApiModelProperty(value = "照片数组", name = "descImgArr")
    private List<ProblemImg> descImgArr;
    @ApiModelProperty(value = "子表", name = "subListObject")
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
