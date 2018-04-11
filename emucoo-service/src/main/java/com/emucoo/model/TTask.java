package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_task")
public class TTask extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务说明图片id
     */
    @Column(name = "illustration_img_id")
    private Long illustrationImgId;

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
     * 审核截止时间
     */
    @Column(name = "audit_deadline")
    private String auditDeadline;

    /**
     * 执行人部门ids
     */
    @Column(name = "executor_dpt_ids")
    private Long executorDptIds;

    /**
     * 执行人岗位ids
     */
    @Column(name = "executor_position_ids")
    private Long executorPositionIds;

    /**
     * 执行人店铺ids
     */
    @Column(name = "executor_shop_ids")
    private Long executorShopIds;

    /**
     * 抄送人岗位id
     */
    @Column(name = "cc_position_ids")
    private Long ccPositionIds;

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
     * 操作项ids
     */
    @Column(name = "operate_ids")
    private String operateIds;

    /**
     * 任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     */
    @Column(name = "duration_time_type")
    private Boolean durationTimeType;

    /**
     * 指定的任务持续开始时间
     */
    @Column(name = "duration_start_time")
    private String durationStartTime;

    /**
     * 指定的任务持续结束时间
     */
    @Column(name = "duration_end_time")
    private String durationEndTime;

    /**
     * 重复任务开始日期
     */
    @Column(name = "loop_start_date")
    private Date loopStartDate;

    /**
     * 重复任务结束日期
     */
    @Column(name = "loop_end_date")
    private Date loopEndDate;

    /**
     * 循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     */
    @Column(name = "loop_cycle_type")
    private Boolean loopCycleType;

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
    private Boolean auditType;

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

    /**
     * 版本
     */
    private String version;

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
     * @return illustration_img_id - 任务说明图片id
     */
    public Long getIllustrationImgId() {
        return illustrationImgId;
    }

    /**
     * 设置任务说明图片id
     *
     * @param illustrationImgId 任务说明图片id
     */
    public void setIllustrationImgId(Long illustrationImgId) {
        this.illustrationImgId = illustrationImgId;
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
     * 获取执行人部门ids
     *
     * @return executor_dpt_ids - 执行人部门ids
     */
    public Long getExecutorDptIds() {
        return executorDptIds;
    }

    /**
     * 设置执行人部门ids
     *
     * @param executorDptIds 执行人部门ids
     */
    public void setExecutorDptIds(Long executorDptIds) {
        this.executorDptIds = executorDptIds;
    }

    /**
     * 获取执行人岗位ids
     *
     * @return executor_position_ids - 执行人岗位ids
     */
    public Long getExecutorPositionIds() {
        return executorPositionIds;
    }

    /**
     * 设置执行人岗位ids
     *
     * @param executorPositionIds 执行人岗位ids
     */
    public void setExecutorPositionIds(Long executorPositionIds) {
        this.executorPositionIds = executorPositionIds;
    }

    /**
     * 获取执行人店铺ids
     *
     * @return executor_shop_ids - 执行人店铺ids
     */
    public Long getExecutorShopIds() {
        return executorShopIds;
    }

    /**
     * 设置执行人店铺ids
     *
     * @param executorShopIds 执行人店铺ids
     */
    public void setExecutorShopIds(Long executorShopIds) {
        this.executorShopIds = executorShopIds;
    }

    /**
     * 获取抄送人岗位id
     *
     * @return cc_position_ids - 抄送人岗位id
     */
    public Long getCcPositionIds() {
        return ccPositionIds;
    }

    /**
     * 设置抄送人岗位id
     *
     * @param ccPositionIds 抄送人岗位id
     */
    public void setCcPositionIds(Long ccPositionIds) {
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
     * 获取操作项ids
     *
     * @return operate_ids - 操作项ids
     */
    public String getOperateIds() {
        return operateIds;
    }

    /**
     * 设置操作项ids
     *
     * @param operateIds 操作项ids
     */
    public void setOperateIds(String operateIds) {
        this.operateIds = operateIds;
    }

    /**
     * 获取任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     *
     * @return duration_time_type - 任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     */
    public Boolean getDurationTimeType() {
        return durationTimeType;
    }

    /**
     * 设置任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     *
     * @param durationTimeType 任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）
     */
    public void setDurationTimeType(Boolean durationTimeType) {
        this.durationTimeType = durationTimeType;
    }

    /**
     * 获取指定的任务持续开始时间
     *
     * @return duration_start_time - 指定的任务持续开始时间
     */
    public String getDurationStartTime() {
        return durationStartTime;
    }

    /**
     * 设置指定的任务持续开始时间
     *
     * @param durationStartTime 指定的任务持续开始时间
     */
    public void setDurationStartTime(String durationStartTime) {
        this.durationStartTime = durationStartTime;
    }

    /**
     * 获取指定的任务持续结束时间
     *
     * @return duration_end_time - 指定的任务持续结束时间
     */
    public String getDurationEndTime() {
        return durationEndTime;
    }

    /**
     * 设置指定的任务持续结束时间
     *
     * @param durationEndTime 指定的任务持续结束时间
     */
    public void setDurationEndTime(String durationEndTime) {
        this.durationEndTime = durationEndTime;
    }

    /**
     * 获取重复任务开始日期
     *
     * @return loop_start_date - 重复任务开始日期
     */
    public Date getLoopStartDate() {
        return loopStartDate;
    }

    /**
     * 设置重复任务开始日期
     *
     * @param loopStartDate 重复任务开始日期
     */
    public void setLoopStartDate(Date loopStartDate) {
        this.loopStartDate = loopStartDate;
    }

    /**
     * 获取重复任务结束日期
     *
     * @return loop_end_date - 重复任务结束日期
     */
    public Date getLoopEndDate() {
        return loopEndDate;
    }

    /**
     * 设置重复任务结束日期
     *
     * @param loopEndDate 重复任务结束日期
     */
    public void setLoopEndDate(Date loopEndDate) {
        this.loopEndDate = loopEndDate;
    }

    /**
     * 获取循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     *
     * @return loop_cycle_type - 循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     */
    public Boolean getLoopCycleType() {
        return loopCycleType;
    }

    /**
     * 设置循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     *
     * @param loopCycleType 循环周期类型（1：隔X天一次，2：周循环，3：月循环）
     */
    public void setLoopCycleType(Boolean loopCycleType) {
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
    public Boolean getAuditType() {
        return auditType;
    }

    /**
     * 设置1：操作项审核 2：整体审核
     *
     * @param auditType 1：操作项审核 2：整体审核
     */
    public void setAuditType(Boolean auditType) {
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
    public String getVersion() {
        return version;
    }

    /**
     * 设置版本
     *
     * @param version 版本
     */
    public void setVersion(String version) {
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
}