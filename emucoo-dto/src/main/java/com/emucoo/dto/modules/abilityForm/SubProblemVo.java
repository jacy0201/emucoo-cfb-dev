package com.emucoo.dto.modules.abilityForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
@ApiModel
public class SubProblemVo {
    @ApiModelProperty(value = "子题项名", name = "subProblemName", example = "A.管理组符合配置:需了解门店应有配置数及当前实际配置数")
    private String subProblemName;
    @ApiModelProperty(value = "子题项id", name = "subProblemID", example = "1")
    private Long subProblemID;
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
    @ApiModelProperty(value = "是否有子表", name = "isSubList", example = "true")
    private Boolean isSubList;
    @ApiModelProperty(value = "机会点数组", name = "subProblemChanceArray")
    private List<ProblemChanceVo> subProblemChanceArray;
    @ApiModelProperty(value = "其他机会点数组", name = "subOtherChanceArray")
    private List<ProblemChanceVo> subOtherChanceArray;
    private List<ProblemImg> descImgArr;
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

    public List<ProblemImg> getDescImgArr() {
        return descImgArr;
    }

    public void setDescImgArr(List<ProblemImg> descImgArr) {
        this.descImgArr = descImgArr;
    }
}
