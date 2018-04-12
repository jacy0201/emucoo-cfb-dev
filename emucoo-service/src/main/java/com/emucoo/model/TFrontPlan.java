package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_front_plan")
public class TFrontPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 计划id
     */
    @Column(name = "loop_plan_id")
    private Long loopPlanId;

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
    private Boolean status;

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
    private String planPreciseTime;

    /**
     * 关联的报告id
     */
    @Column(name = "report_id")
    private Long reportId;

    /**
     * 关联的检查表ids
     */
    @Column(name = "check_form_ids")
    private String checkFormIds;

    /**
     * 提醒时间类型(0：无，1：日程开始时，2：提前15分钟，3：提前30分钟，4：提前1小时，5：提前2小时，6：提前一天)
     */
    @Column(name = "remind_type")
    private Boolean remindType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 实际巡店时间
     */
    @Column(name = "actual_execute_time")
    private String actualExecuteTime;

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

    /**
     * 获取状态 0：未安排，1：未巡店，2：已巡店
     *
     * @return status - 状态 0：未安排，1：未巡店，2：已巡店
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态 0：未安排，1：未巡店，2：已巡店
     *
     * @param status 状态 0：未安排，1：未巡店，2：已巡店
     */
    public void setStatus(Boolean status) {
        this.status = status;
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

    /**
     * 获取预计到店时间
     *
     * @return plan_precise_time - 预计到店时间
     */
    public String getPlanPreciseTime() {
        return planPreciseTime;
    }

    /**
     * 设置预计到店时间
     *
     * @param planPreciseTime 预计到店时间
     */
    public void setPlanPreciseTime(String planPreciseTime) {
        this.planPreciseTime = planPreciseTime;
    }

    /**
     * 获取关联的报告id
     *
     * @return report_id - 关联的报告id
     */
    public Long getReportId() {
        return reportId;
    }

    /**
     * 设置关联的报告id
     *
     * @param reportId 关联的报告id
     */
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    /**
     * 获取关联的检查表ids
     *
     * @return check_form_ids - 关联的检查表ids
     */
    public String getCheckFormIds() {
        return checkFormIds;
    }

    /**
     * 设置关联的检查表ids
     *
     * @param checkFormIds 关联的检查表ids
     */
    public void setCheckFormIds(String checkFormIds) {
        this.checkFormIds = checkFormIds;
    }

    /**
     * 获取提醒时间类型(0：无，1：日程开始时，2：提前15分钟，3：提前30分钟，4：提前1小时，5：提前2小时，6：提前一天)
     *
     * @return remind_type - 提醒时间类型(0：无，1：日程开始时，2：提前15分钟，3：提前30分钟，4：提前1小时，5：提前2小时，6：提前一天)
     */
    public Boolean getRemindType() {
        return remindType;
    }

    /**
     * 设置提醒时间类型(0：无，1：日程开始时，2：提前15分钟，3：提前30分钟，4：提前1小时，5：提前2小时，6：提前一天)
     *
     * @param remindType 提醒时间类型(0：无，1：日程开始时，2：提前15分钟，3：提前30分钟，4：提前1小时，5：提前2小时，6：提前一天)
     */
    public void setRemindType(Boolean remindType) {
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

    /**
     * 获取实际巡店时间
     *
     * @return actual_execute_time - 实际巡店时间
     */
    public String getActualExecuteTime() {
        return actualExecuteTime;
    }

    /**
     * 设置实际巡店时间
     *
     * @param actualExecuteTime 实际巡店时间
     */
    public void setActualExecuteTime(String actualExecuteTime) {
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
}