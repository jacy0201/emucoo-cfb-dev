package com.emucoo.dto.modules.abilityForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
@ApiModel
public class AbilityFormMain {
    @ApiModelProperty(value = "巡店id", name = "patrolShopArrangeID", required = true, example = "1")
    private Long patrolShopArrangeID;
    @ApiModelProperty(value = "店铺id", name = "shopID", required = true, example = "1")
    private Long shopID;
    @ApiModelProperty(value = "表单id", name = "formID", required = true, example = "1")
    private Long formID;
    @ApiModelProperty(value = "表单类型 1-类RVR表，2-能力模型", name = "formType", example = "2")
    private Integer formType;
    @ApiModelProperty(value = "表单名称", name = "formName", example = "RVR")
    private String formName;
    @ApiModelProperty(value = "店铺名称", name = "shopName", example = "南京西路店")
    private String shopName;
    @ApiModelProperty(value = "打表日期", name = "gradeDate", example = "2018-06-01")
    private String gradeDate;
    @ApiModelProperty(value = "品牌", name = "brandName", example = "DQ")
    private String brandName;
    @ApiModelProperty(value = "子表", name = "subFormArray")
    private List<AbilitySubForm> subFormArray;


    public Long getPatrolShopArrangeID() {
        return patrolShopArrangeID;
    }

    public void setPatrolShopArrangeID(Long patrolShopArrangeID) {
        this.patrolShopArrangeID = patrolShopArrangeID;
    }

    public Long getShopID() {
        return shopID;
    }

    public void setShopID(Long shopID) {
        this.shopID = shopID;
    }

    public Long getFormID() {
        return formID;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public void setFormID(Long formID) {
        this.formID = formID;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormName() {
        return formName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setGradeDate(String gradeDate) {
        this.gradeDate = gradeDate;
    }

    public String getGradeDate() {
        return gradeDate;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public List<AbilitySubForm> getSubFormArray() {
        return subFormArray;
    }

    public void setSubFormArray(List<AbilitySubForm> subFormArray) {
        this.subFormArray = subFormArray;
    }
}
