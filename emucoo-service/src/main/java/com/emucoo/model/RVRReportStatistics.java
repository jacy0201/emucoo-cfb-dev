package com.emucoo.model;

import com.emucoo.annotation.FieldName;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by sj on 2018/6/1.
 */
public class RVRReportStatistics {

    @FieldName(value = "美方编号")
    private String code;

    @FieldName(value = "品牌名称")
    private String brandName;

    @FieldName(value = "分区")
    private String areaName;

    @FieldName(value = "城市")
    private String city;

    @FieldName(value = "店名")
    private String shopName;

    /*@FieldName(value = "经营性质")
    private String shopType;*/

    @FieldName(value = "评估人职位")
    private String reporterPosition;

    @FieldName(value = "评估人姓名")
    private String reporterName;

    @FieldName(value = "巡店安排计划日期")
    private String planDate;

    @FieldName(value = "实际巡店日期")
    private String actualPatrolShopDate;

    @FieldName(value = "实际巡店位置")
    private String actualPatrolShopAddress;

    @FieldName(value = "开始巡店时间")
    private String actualPatrolShopTime;

    @FieldName(value = "完成巡店时间")
    private String reportTime;

    @FieldName(value = "督导评估成绩")
    private String actualTotalscore;

    @FieldName(value = "得分率")
    private String actualTotalRate;

    @FieldName(value = "评估级别")
    private String level;

    @FieldName(value = "otherValueList")
    private List<TFormValue> otherValueList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /*public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }*/

    public String getReporterPosition() {
        return reporterPosition;
    }

    public void setReporterPosition(String reporterPosition) {
        this.reporterPosition = reporterPosition;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getActualPatrolShopDate() {
        return actualPatrolShopDate;
    }

    public void setActualPatrolShopDate(String actualPatrolShopDate) {
        this.actualPatrolShopDate = actualPatrolShopDate;
    }

    public String getActualPatrolShopAddress() {
        return actualPatrolShopAddress;
    }

    public void setActualPatrolShopAddress(String actualPatrolShopAddress) {
        this.actualPatrolShopAddress = actualPatrolShopAddress;
    }

    public String getActualPatrolShopTime() {
        return actualPatrolShopTime;
    }

    public void setActualPatrolShopTime(String actualPatrolShopTime) {
        this.actualPatrolShopTime = actualPatrolShopTime;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getActualTotalscore() {
        return actualTotalscore;
    }

    public void setActualTotalscore(String actualTotalscore) {
        this.actualTotalscore = actualTotalscore;
    }

    public String getActualTotalRate() {
        return actualTotalRate;
    }

    public void setActualTotalRate(String actualTotalRate) {
        this.actualTotalRate = actualTotalRate;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<TFormValue> getOtherValueList() {
        return otherValueList;
    }

    public void setOtherValueList(List<TFormValue> otherValueList) {
        this.otherValueList = otherValueList;
    }
}
