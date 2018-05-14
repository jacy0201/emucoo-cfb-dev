package com.emucoo.model;

/**
 * Created by sj on 2018/5/14.
 */
public class ShopFormUseSummary {

    private Long shopId;
    private Long formId;
    private Integer formCount;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Integer getFormCount() {
        return formCount;
    }

    public void setFormCount(Integer formCount) {
        this.formCount = formCount;
    }
}
