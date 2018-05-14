package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_task")
public class TTask extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务说明图片id
     */
    @Column(name = "illustration_img_ids")
    private String illustrationImgIds;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 1常规任务 2指派任务 3改善任务 4巡店安排 5工作备忘
     */
    private Integer type;

    /**
     * 描述说明
     */
    private String description;

    /**
     * 执行截止时间
     */
    @Column(name = "execute_deadline")
    private String executeDeadline;

    /**
     * 执行提醒时间
     */
    @Column(name = "execute_remind_time")
    private String executeRemindTime;

    /**
     * 审核人ids
     */
    @Column(name = "audit_user_id")
    private Long auditUserId;

    /**
     * 审核截止时间
     */
    @Column(name = "audit_deadline")
    private String auditDeadline;

    /**
     * 执行人部门id
     */
    @Column(name = "executor_dpt_id")
    private Long executorDptId;

    /**
     * 执行人ids
     */
    @Column(name = "execute_user_ids")
    private String executeUserIds;

    @Column(name = "execute_user_type")
    private Integer executeUserType;

    /**
     * 执行人岗位ids
     */
    @Column(name = "executor_position_ids")
    private String executorPositionIds;

    /**
     * 执行人店铺ids
     */
    @Column(name = "executor_shop_ids")
    private String executorShopIds;

    /**
     * 抄送人ids
     */
    @Column(name = "cc_user_ids")
    private String ccUserIds;

    /**
     * 抄送人岗位ids
     */
    @Column(name = "cc_position_ids")
    private String ccPositionIds;

    /**
     * 评分方式（1：任务评分，2：操作项评分）
     */
    @Column(name = "score_type")
    private Byte scoreType;

    /**
     * 是否启用（0：停用，1：启用）
     */
    @Column(name = "is_use")
    private Boolean isUse;

    /**
     * 任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     */
    @Column(name = "duration_time_type")
    private Integer durationTimeType;

    /**
     * 重复任务开始日期
     */
    @Column(name = "task_start_date")
    private Date taskStartDate;

    /**
     * 重复任务结束日期
     */
    @Column(name = "task_end_date")
    private Date taskEndDate;

    /**
     * 循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     */
    @Column(name = "loop_cycle_type")
    private Integer loopCycleType;

    /**
     * 循环周期的值，和loop_cycle_type配合使用
            1.如果是周循环，存储的是1,2,3,6,7，从1-7代表星期一到星期日
            2.如果是月循环，存储的是详细日期的拼接串，以逗号相隔
     */
    @Column(name = "loop_cycle_value")
    private String loopCycleValue;

    /**
     * 主事务id
     */
    @Column(name = "work_id")
    private String workId;

    /**
     * 1：操作项审核 2：整体审核
     */
    @Column(name = "audit_type")
    private Integer auditType;

    /**
     * 是否删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 改善任务关联的机会点id
     */
    @Column(name = "oppt_id")
    private Long opptId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

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
     * 审核部门id
     */
    @Column(name = "audit_dpt_id")
    private Long auditDptId;

    @Column(name = "audit_dpt_name")
    private String auditDptName;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 预设的满分
     */
    @Column(name = "preinstall_score")
    private String preinstallScore;

    /**
     * 预设的权重
     */
    @Column(name = "preinstall_weight")
    private String preinstallWeight;

    @Column(name = "org_id")
    private Long orgId;

    /**
     * 报告id
     */
    @Column(name = "report_id")
    private Long reportId;

    /**
     * 巡店安排
     */
    @Column(name = "front_plan_id")
    private Long frontPlanId;

    @Transient
    List<TLoopWork> workList;

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
     * 获取任务说明图片id
     *
     * @return illustration_img_ids - 任务说明图片id
     */
    public String getIllustrationImgIds() {
        return illustrationImgIds;
    }

    /**
     * 设置任务说明图片id
     *
     * @param illustrationImgIds 任务说明图片id
     */
    public void setIllustrationImgIds(String illustrationImgIds) {
        this.illustrationImgIds = illustrationImgIds;
    }

    /**
     * 获取任务名称
     *
     * @return name - 任务名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置任务名称
     *
     * @param name 任务名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取1常规任务 2指派任务 3改善任务 4巡店安排 5工作备忘
     *
     * @return type - 1常规任务 2指派任务 3改善任务 4巡店安排 5工作备忘
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1常规任务 2指派任务 3改善任务 4巡店安排 5工作备忘
     *
     * @param type 1常规任务 2指派任务 3改善任务 4巡店安排 5工作备忘
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取描述说明
     *
     * @return description - 描述说明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述说明
     *
     * @param description 描述说明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取执行截止时间
     *
     * @return execute_deadline - 执行截止时间
     */
    public String getExecuteDeadline() {
        return executeDeadline;
    }

    /**
     * 设置执行截止时间
     *
     * @param executeDeadline 执行截止时间
     */
    public void setExecuteDeadline(String executeDeadline) {
        this.executeDeadline = executeDeadline;
    }

    /**
     * 获取执行提醒时间
     *
     * @return execute_remind_time - 执行提醒时间
     */
    public String getExecuteRemindTime() {
        return executeRemindTime;
    }

    /**
     * 设置执行提醒时间
     *
     * @param executeRemindTime 执行提醒时间
     */
    public void setExecuteRemindTime(String executeRemindTime) {
        this.executeRemindTime = executeRemindTime;
    }

    /**
     * 获取审核人ids
     *
     * @return audit_user_id - 审核人ids
     */
    public Long getAuditUserId() {
        return auditUserId;
    }

    /**
     * 设置审核人ids
     *
     * @param auditUserId 审核人ids
     */
    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    /**
     * 获取审核截止时间
     *
     * @return audit_deadline - 审核截止时间
     */
    public String getAuditDeadline() {
        return auditDeadline;
    }

    /**
     * 设置审核截止时间
     *
     * @param auditDeadline 审核截止时间
     */
    public void setAuditDeadline(String auditDeadline) {
        this.auditDeadline = auditDeadline;
    }

    /**
     * 获取执行人部门id
     *
     * @return executor_dpt_id - 执行人部门id
     */
    public Long getExecutorDptId() {
        return executorDptId;
    }

    /**
     * 设置执行人部门id
     *
     * @param executorDptId 执行人部门id
     */
    public void setExecutorDptId(Long executorDptId) {
        this.executorDptId = executorDptId;
    }

    /**
     * 获取执行人ids
     *
     * @return execute_user_ids - 执行人ids
     */
    public String getExecuteUserIds() {
        return executeUserIds;
    }

    /**
     * 设置执行人ids
     *
     * @param executeUserIds 执行人ids
     */
    public void setExecuteUserIds(String executeUserIds) {
        this.executeUserIds = executeUserIds;
    }

    /**
     * 获取执行人岗位ids
     *
     * @return executor_position_ids - 执行人岗位ids
     */
    public String getExecutorPositionIds() {
        return executorPositionIds;
    }

    /**
     * 设置执行人岗位ids
     *
     * @param executorPositionIds 执行人岗位ids
     */
    public void setExecutorPositionIds(String executorPositionIds) {
        this.executorPositionIds = executorPositionIds;
    }

    /**
     * 获取执行人店铺ids
     *
     * @return executor_shop_ids - 执行人店铺ids
     */
    public String getExecutorShopIds() {
        return executorShopIds;
    }

    /**
     * 设置执行人店铺ids
     *
     * @param executorShopIds 执行人店铺ids
     */
    public void setExecutorShopIds(String executorShopIds) {
        this.executorShopIds = executorShopIds;
    }

    /**
     * 获取抄送人ids
     *
     * @return cc_user_ids - 抄送人ids
     */
    public String getCcUserIds() {
        return ccUserIds;
    }

    /**
     * 设置抄送人ids
     *
     * @param ccUserIds 抄送人ids
     */
    public void setCcUserIds(String ccUserIds) {
        this.ccUserIds = ccUserIds;
    }

    /**
     * 获取抄送人岗位ids
     *
     * @return cc_position_ids - 抄送人岗位ids
     */
    public String getCcPositionIds() {
        return ccPositionIds;
    }

    /**
     * 设置抄送人岗位ids
     *
     * @param ccPositionIds 抄送人岗位ids
     */
    public void setCcPositionIds(String ccPositionIds) {
        this.ccPositionIds = ccPositionIds;
    }

    /**
     * 获取评分方式（1：任务评分，2：操作项评分）
     *
     * @return score_type - 评分方式（1：任务评分，2：操作项评分）
     */
    public Byte getScoreType() {
        return scoreType;
    }

    /**
     * 设置评分方式（1：任务评分，2：操作项评分）
     *
     * @param scoreType 评分方式（1：任务评分，2：操作项评分）
     */
    public void setScoreType(Byte scoreType) {
        this.scoreType = scoreType;
    }

    /**
     * 获取是否启用（0：停用，1：启用）
     *
     * @return is_use - 是否启用（0：停用，1：启用）
     */
    public Boolean getIsUse() {
        return isUse;
    }

    /**
     * 设置是否启用（0：停用，1：启用）
     *
     * @param isUse 是否启用（0：停用，1：启用）
     */
    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    /**
     * 获取任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     *
     * @return duration_time_type - 任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     */
    public Integer getDurationTimeType() {
        return durationTimeType;
    }

    /**
     * 设置任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     *
     * @param durationTimeType 任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     */
    public void setDurationTimeType(Integer durationTimeType) {
        this.durationTimeType = durationTimeType;
    }

    /**
     * 获取重复任务开始日期
     *
     * @return task_start_date - 重复任务开始日期
     */
    public Date getTaskStartDate() {
        return taskStartDate;
    }

    /**
     * 设置重复任务开始日期
     *
     * @param taskStartDate 重复任务开始日期
     */
    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    /**
     * 获取重复任务结束日期
     *
     * @return task_end_date - 重复任务结束日期
     */
    public Date getTaskEndDate() {
        return taskEndDate;
    }

    /**
     * 设置重复任务结束日期
     *
     * @param taskEndDate 重复任务结束日期
     */
    public void setTaskEndDate(Date taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    /**
     * 获取循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     *
     * @return loop_cycle_type - 循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     */
    public Integer getLoopCycleType() {
        return loopCycleType;
    }

    /**
     * 设置循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     *
     * @param loopCycleType 循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     */
    public void setLoopCycleType(Integer loopCycleType) {
        this.loopCycleType = loopCycleType;
    }

    /**
     * 获取循环周期的值，和loop_cycle_type配合使用
            1.如果是周循环，存储的是1,2,3,6,7，从1-7代表星期一到星期日
            2.如果是月循环，存储的是详细日期的拼接串，以逗号相隔
     *
     * @return loop_cycle_value - 循环周期的值，和loop_cycle_type配合使用
            1.如果是周循环，存储的是1,2,3,6,7，从1-7代表星期一到星期日
            2.如果是月循环，存储的是详细日期的拼接串，以逗号相隔
     */
    public String getLoopCycleValue() {
        return loopCycleValue;
    }

    /**
     * 设置循环周期的值，和loop_cycle_type配合使用
            1.如果是周循环，存储的是1,2,3,6,7，从1-7代表星期一到星期日
            2.如果是月循环，存储的是详细日期的拼接串，以逗号相隔
     *
     * @param loopCycleValue 循环周期的值，和loop_cycle_type配合使用
            1.如果是周循环，存储的是1,2,3,6,7，从1-7代表星期一到星期日
            2.如果是月循环，存储的是详细日期的拼接串，以逗号相隔
     */
    public void setLoopCycleValue(String loopCycleValue) {
        this.loopCycleValue = loopCycleValue;
    }

    /**
     * 获取主事务id
     *
     * @return work_id - 主事务id
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置主事务id
     *
     * @param workId 主事务id
     */
    public void setWorkId(String workId) {
        this.workId = workId;
    }

    /**
     * 获取1：操作项审核 2：整体审核
     *
     * @return audit_type - 1：操作项审核 2：整体审核
     */
    public Integer getAuditType() {
        return auditType;
    }

    /**
     * 设置1：操作项审核 2：整体审核
     *
     * @param auditType 1：操作项审核 2：整体审核
     */
    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    /**
     * 获取是否删除
     *
     * @return is_del - 是否删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除
     *
     * @param isDel 是否删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取改善任务关联的机会点id
     *
     * @return oppt_id - 改善任务关联的机会点id
     */
    public Long getOpptId() {
        return opptId;
    }

    /**
     * 设置改善任务关联的机会点id
     *
     * @param opptId 改善任务关联的机会点id
     */
    public void setOpptId(Long opptId) {
        this.opptId = opptId;
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
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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
     * 获取审核部门id
     *
     * @return audit_dpt_id - 审核部门id
     */
    public Long getAuditDptId() {
        return auditDptId;
    }

    /**
     * 设置审核部门id
     *
     * @param auditDptId 审核部门id
     */
    public void setAuditDptId(Long auditDptId) {
        this.auditDptId = auditDptId;
    }

    /**
     * 获取版本
     *
     * @return version - 版本
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置版本
     *
     * @param version 版本
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取预设的满分
     *
     * @return preinstall_score - 预设的满分
     */
    public String getPreinstallScore() {
        return preinstallScore;
    }

    /**
     * 设置预设的满分
     *
     * @param preinstallScore 预设的满分
     */
    public void setPreinstallScore(String preinstallScore) {
        this.preinstallScore = preinstallScore;
    }

    /**
     * 获取预设的权重
     *
     * @return preinstall_weight - 预设的权重
     */
    public String getPreinstallWeight() {
        return preinstallWeight;
    }

    /**
     * 设置预设的权重
     *
     * @param preinstallWeight 预设的权重
     */
    public void setPreinstallWeight(String preinstallWeight) {
        this.preinstallWeight = preinstallWeight;
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

    /**
     * 获取报告id
     *
     * @return report_id - 报告id
     */
    public Long getReportId() {
        return reportId;
    }

    /**
     * 设置报告id
     *
     * @param reportId 报告id
     */
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    /**
     * 获取巡店安排
     *
     * @return front_plan_id - 巡店安排
     */
    public Long getFrontPlanId() {
        return frontPlanId;
    }

    /**
     * 设置巡店安排
     *
     * @param frontPlanId 巡店安排
     */
    public void setFrontPlanId(Long frontPlanId) {
        this.frontPlanId = frontPlanId;
    }

    public List<TLoopWork> getWorkList() {
        return workList;
    }

    public void setWorkList(List<TLoopWork> workList) {
        this.workList = workList;
    }

    public Integer getExecuteUserType() {
        return executeUserType;
    }

    public void setExecuteUserType(Integer executeUserType) {
        this.executeUserType = executeUserType;
    }

    public String getAuditDptName() {
        return auditDptName;
    }

    public void setAuditDptName(String auditDptName) {
        this.auditDptName = auditDptName;
    }
}