package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_front_plan")
public class TFrontPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 计划id
     */
    @Column(name = "loop_plan_id")
    private Long loopPlanId;

    @Column(name = "sub_plan_id")
    private Long subPlanId;

    /**
     * 针对的店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 巡店日期
     */
    @Column(name = "plan_date")
    private Date planDate;

    /**
     * 状态 0：未安排，1：未巡店，2：已巡店
     */
    private Byte status;


    @Column(name = "arrange_year")
    private String arrangeYear;

    @Column(name = "arrange_month")
    private String arrangeMonth;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 预计到店时间
     */
    @Column(name = "plan_precise_time")
    private Date planPreciseTime;

    /**
     * 提醒时间类型(0：无，1：日程开始时，2：提前15分钟，3：提前30分钟，4：提前1小时，5：提前2小时，6：提前一天)
     */
    @Column(name = "remind_type")
    private Byte remindType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 实际巡店时间
     */
    @Column(name = "actual_execute_time")
    private Date actualExecuteTime;

    /**
     * 实际巡店位置
     */
    @Column(name = "actual_execute_address")
    private String actualExecuteAddress;

    /**
     * 实际提醒时间
     */
    @Column(name = "actual_remind_time")
    private Date actualRemindTime;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    /**
     * 巡店安排人id
     */
    @Column(name = "arranger_id")
    private Long arrangerId;

    /**
     * 被安排人id
     */
    @Column(name = "arrangee_id")
    private Long arrangeeId;

    @Column(name = "notice_user_id")
    private String noticeUserId;

    /**
     * 数据创建人ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 数据修改人ID
     */
    @Column(name = "modify_user_id")
    private Long modifyUserId;

    /**
     * 是否逻辑删除0：正常1：逻辑删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    @Column(name = "org_id")
    private Long orgId;

    @Transient
    private TShopInfo shop;

    @Transient
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
     * 获取计划id
     *
     * @return loop_plan_id - 计划id
     */
    public Long getLoopPlanId() {
        return loopPlanId;
    }

    /**
     * 设置计划id
     *
     * @param loopPlanId 计划id
     */
    public void setLoopPlanId(Long loopPlanId) {
        this.loopPlanId = loopPlanId;
    }

    public Long getSubPlanId() {
        return subPlanId;
    }

    public void setSubPlanId(Long subPlanId) {
        this.subPlanId = subPlanId;
    }

    /**
     * 获取针对的店铺ID
     *
     * @return shop_id - 针对的店铺ID
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * 设置针对的店铺ID
     *
     * @param shopId 针对的店铺ID
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getArrangeYear() {
        return arrangeYear;
    }

    public void setArrangeYear(String arrangeYear) {
        this.arrangeYear = arrangeYear;
    }

    public String getArrangeMonth() {
        return arrangeMonth;
    }

    public void setArrangeMonth(String arrangeMonth) {
        this.arrangeMonth = arrangeMonth;
    }

    /**
     * 获取巡店日期
     *
     * @return plan_date - 巡店日期
     */
    public Date getPlanDate() {
        return planDate;
    }

    /**
     * 设置巡店日期
     *
     * @param planDate 巡店日期
     */
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPlanPreciseTime() {
        return planPreciseTime;
    }

    public void setPlanPreciseTime(Date planPreciseTime) {
        this.planPreciseTime = planPreciseTime;
    }

    public Byte getRemindType() {
        return remindType;
    }

    public void setRemindType(Byte remindType) {
        this.remindType = remindType;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getActualExecuteTime() {
        return actualExecuteTime;
    }

    public void setActualExecuteTime(Date actualExecuteTime) {
        this.actualExecuteTime = actualExecuteTime;
    }

    /**
     * 获取实际巡店位置
     *
     * @return actual_execute_address - 实际巡店位置
     */
    public String getActualExecuteAddress() {
        return actualExecuteAddress;
    }

    /**
     * 设置实际巡店位置
     *
     * @param actualExecuteAddress 实际巡店位置
     */
    public void setActualExecuteAddress(String actualExecuteAddress) {
        this.actualExecuteAddress = actualExecuteAddress;
    }

    /**
     * 获取实际提醒时间
     *
     * @return actual_remind_time - 实际提醒时间
     */
    public Date getActualRemindTime() {
        return actualRemindTime;
    }

    /**
     * 设置实际提醒时间
     *
     * @param actualRemindTime 实际提醒时间
     */
    public void setActualRemindTime(Date actualRemindTime) {
        this.actualRemindTime = actualRemindTime;
    }

    public TShopInfo getShop() {
        return shop;
    }

    public void setShop(TShopInfo shop) {
        this.shop = shop;
    }

    /**
     * 获取巡店安排人id
     *
     * @return arranger_id - 巡店安排人id
     */
    public Long getArrangerId() {
        return arrangerId;
    }

    /**
     * 设置巡店安排人id
     *
     * @param arrangerId 巡店安排人id
     */
    public void setArrangerId(Long arrangerId) {
        this.arrangerId = arrangerId;
    }

    /**
     * 获取被安排人id
     *
     * @return arrangee_id - 被安排人id
     */
    public Long getArrangeeId() {
        return arrangeeId;
    }

    /**
     * 设置被安排人id
     *
     * @param arrangeeId 被安排人id
     */
    public void setArrangeeId(Long arrangeeId) {
        this.arrangeeId = arrangeeId;
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
     * 获取数据修改人ID
     *
     * @return modify_user_id - 数据修改人ID
     */
    public Long getModifyUserId() {
        return modifyUserId;
    }

    /**
     * 设置数据修改人ID
     *
     * @param modifyUserId 数据修改人ID
     */
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    /**
     * 获取是否逻辑删除0：正常1：逻辑删除
     *
     * @return is_del - 是否逻辑删除0：正常1：逻辑删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置是否逻辑删除0：正常1：逻辑删除
     *
     * @param isDel 是否逻辑删除0：正常1：逻辑删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
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

    public String getNoticeUserId() {
        return noticeUserId;
    }

    public void setNoticeUserId(String noticeUserId) {
        this.noticeUserId = noticeUserId;
    }
}