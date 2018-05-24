package com.emucoo.dto.modules.abilityForm;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
@ApiModel
public class AbilityFormMain {
    private Long patrolShopArrangeID;
    private Long shopID;
    private Long formID;
    private Integer formType;
    private String formName;
    private String shopName;
    private String gradeDate;
    private String brandName;
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
