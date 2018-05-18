package com.emucoo.dto.modules.abilityForm;

import java.util.List;

/**
 * Created by sj on 2018/5/17.
 */
public class AbilityFormMain {
    private Long formID;
    private String formName;
    private String shopName;
    private String gradeDate;
    private String brandName;
    private List<AbilitySubForm> subFormArray;

    public Long getFormID() {
        return formID;
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
