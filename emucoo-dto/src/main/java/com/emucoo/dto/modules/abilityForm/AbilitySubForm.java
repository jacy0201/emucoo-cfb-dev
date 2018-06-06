package com.emucoo.dto.modules.abilityForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
@ApiModel
public class AbilitySubForm {
    @ApiModelProperty(value = "子表名", name = "subFormName", example = "危急")
    private String subFormName;
    @ApiModelProperty(value = "子表id", name = "subFormID", example = "1")
    private Long subFormID;
    @ApiModelProperty(value = "是否可用", name = "isUsable", example = "true")
    private Boolean isUsable;
    @ApiModelProperty(value = "是否完成", name = "isDone", example = "true")
    private Boolean isDone;
    @ApiModelProperty(value = "是否通过", name = "isPass", example = "true")
    private Boolean isPass;
    @ApiModelProperty(value = "子表类别", name = "subFormKindArray")
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
