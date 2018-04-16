package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_report")
public class TReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 店铺名
     */
    @Column(name = "shop_name")
    private String shopName;

    /**
     * 店长名
     */
    @Column(name = "shopkeeper_name")
    private String shopkeeperName;

    /**
     * 打表人姓名
     */
    @Column(name = "reporter_name")
    private String reporterName;

    /**
     * 打表人的id
     */
    @Column(name = "reporter_id")
    private String reporterId;

    /**
     * 打表人部门名
     */
    @Column(name = "reporter_dpt_name")
    private String reporterDptName;

    /**
     * 打表日期
     */
    @Column(name = "check_form_time")
    private Date checkFormTime;

    /**
     * 打表人职位名
     */
    @Column(name = "reporter_position")
    private String reporterPosition;

    /**
     * 检查表报告id，多个用逗号相隔
     */
    @Column(name = "form_report_ids")
    private String formReportIds;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 数据创建人ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 店铺id
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 店长id
     */
    @Column(name = "shopkeeper_id")
    private Long shopkeeperId;

    /**
     * 打表人部门id
     */
    @Column(name = "reporter_dpt_id")
    private Long reporterDptId;

    /**
     * 打表人职位id
     */
    @Column(name = "reporter_position_id")
    private Long reporterPositionId;

    @Column(name = "org_id")
    private Long orgId;


    @Transient
    private Boolean isRead;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取店铺名
     *
     * @return shop_name - 店铺名
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置店铺名
     *
     * @param shopName 店铺名
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 获取店长名
     *
     * @return shopkeeper_name - 店长名
     */
    public String getShopkeeperName() {
        return shopkeeperName;
    }

    /**
     * 设置店长名
     *
     * @param shopkeeperName 店长名
     */
    public void setShopkeeperName(String shopkeeperName) {
        this.shopkeeperName = shopkeeperName;
    }

    /**
     * 获取打表人姓名
     *
     * @return reporter_name - 打表人姓名
     */
    public String getReporterName() {
        return reporterName;
    }

    /**
     * 设置打表人姓名
     *
     * @param reporterName 打表人姓名
     */
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    /**
     * 获取打表人部门名
     *
     * @return reporter_dpt_name - 打表人部门名
     */
    public String getReporterDptName() {
        return reporterDptName;
    }

    /**
     * 设置打表人部门名
     *
     * @param reporterDptName 打表人部门名
     */
    public void setReporterDptName(String reporterDptName) {
        this.reporterDptName = reporterDptName;
    }

    /**
     * 获取打表日期
     *
     * @return check_form_time - 打表日期
     */
    public Date getCheckFormTime() {
        return checkFormTime;
    }

    /**
     * 设置打表日期
     *
     * @param checkFormTime 打表日期
     */
    public void setCheckFormTime(Date checkFormTime) {
        this.checkFormTime = checkFormTime;
    }

    /**
     * 获取打表人职位名
     *
     * @return reporter_position - 打表人职位名
     */
    public String getReporterPosition() {
        return reporterPosition;
    }

    /**
     * 设置打表人职位名
     *
     * @param reporterPosition 打表人职位名
     */
    public void setReporterPosition(String reporterPosition) {
        this.reporterPosition = reporterPosition;
    }

    /**
     * 获取检查表报告id，多个用逗号相隔
     *
     * @return form_report_ids - 检查表报告id，多个用逗号相隔
     */
    public String getFormReportIds() {
        return formReportIds;
    }

    /**
     * 设置检查表报告id，多个用逗号相隔
     *
     * @param formReportIds 检查表报告id，多个用逗号相隔
     */
    public void setFormReportIds(String formReportIds) {
        this.formReportIds = formReportIds;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取数据创建人ID
     *
     * @return create_user_id - 数据创建人ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置数据创建人ID
     *
     * @param createUserId 数据创建人ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取店铺id
     *
     * @return shop_id - 店铺id
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * 设置店铺id
     *
     * @param shopId 店铺id
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取店长id
     *
     * @return shopkeeper_id - 店长id
     */
    public Long getShopkeeperId() {
        return shopkeeperId;
    }

    /**
     * 设置店长id
     *
     * @param shopkeeperId 店长id
     */
    public void setShopkeeperId(Long shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
    }

    /**
     * 获取打表人部门id
     *
     * @return reporter_dpt_id - 打表人部门id
     */
    public Long getReporterDptId() {
        return reporterDptId;
    }

    /**
     * 设置打表人部门id
     *
     * @param reporterDptId 打表人部门id
     */
    public void setReporterDptId(Long reporterDptId) {
        this.reporterDptId = reporterDptId;
    }

    /**
     * 获取打表人职位id
     *
     * @return reporter_position_id - 打表人职位id
     */
    public Long getReporterPositionId() {
        return reporterPositionId;
    }

    /**
     * 设置打表人职位id
     *
     * @param reporterPositionId 打表人职位id
     */
    public void setReporterPositionId(Long reporterPositionId) {
        this.reporterPositionId = reporterPositionId;
    }

    /**
     * @return org_id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }
}