package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_loop_work")
public class TLoopWork extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 事务ID
     */
    @Column(name = "work_id")
    private String workId;

    /**
     * 子事务ID
     */
    @Column(name = "sub_work_id")
    private String subWorkId;

    /**
     * 1常规任务 2指派任务 3改善任务 4巡店安排 5工作备忘
     */
    private Integer type;

    /**
     * 创建用户ID（可为空）
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建人的用户名
     */
    @Column(name = "create_user_name")
    private String createUserName;

    /**
     * 执行人用户ID
     */
    @Column(name = "excute_user_id")
    private Long excuteUserId;

    /**
     * 执行人用户名
     */
    @Column(name = "excute_user_name")
    private String excuteUserName;

    /**
     * 审核人用户ID
     */
    @Column(name = "audit_user_id")
    private Long auditUserId;

    /**
     * 审核人名称
     */
    @Column(name = "audit_user_name")
    private String auditUserName;

    /**
     * 抄送人用户ID,多个抄送人用【，】分割
     */
    @Column(name = "send_user_ids")
    private String sendUserIds;

    /**
     * 1：未提交 2：已提交 3：已过期
     */
    @Column(name = "work_status")
    private Integer workStatus;

    /**
     * 1：合格 2：不合格
            
     */
    @Column(name = "work_result")
    private Integer workResult;

    /**
     * 执行开始日期
     */
    @Column(name = "execute_begin_date")
    private Date executeBeginDate;

    /**
     * 执行结束日期
     */
    @Column(name = "execute_end_date")
    private Date executeEndDate;

    /**
     * 执行提醒时间
     */
    @Column(name = "execute_remind_time")
    private Date executeRemindTime;

    /**
     * 执行截止时间
     */
    @Column(name = "execute_deadline")
    private Date executeDeadline;

    /**
     * 审核截止时间
     */
    @Column(name = "audit_deadline")
    private Date auditDeadline;

    /**
     * 任务表id
     */
    @Column(name = "task_id")
    private Long taskId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 实际得分
     */
    private String score;

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
     * 获取事务ID
     *
     * @return work_id - 事务ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置事务ID
     *
     * @param workId 事务ID
     */
    public void setWorkId(String workId) {
        this.workId = workId;
    }

    /**
     * 获取子事务ID
     *
     * @return sub_work_id - 子事务ID
     */
    public String getSubWorkId() {
        return subWorkId;
    }

    /**
     * 设置子事务ID
     *
     * @param subWorkId 子事务ID
     */
    public void setSubWorkId(String subWorkId) {
        this.subWorkId = subWorkId;
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
     * 获取创建用户ID（可为空）
     *
     * @return create_user_id - 创建用户ID（可为空）
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建用户ID（可为空）
     *
     * @param createUserId 创建用户ID（可为空）
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取创建人的用户名
     *
     * @return create_user_name - 创建人的用户名
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 设置创建人的用户名
     *
     * @param createUserName 创建人的用户名
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /**
     * 获取执行人用户ID
     *
     * @return excute_user_id - 执行人用户ID
     */
    public Long getExcuteUserId() {
        return excuteUserId;
    }

    /**
     * 设置执行人用户ID
     *
     * @param excuteUserId 执行人用户ID
     */
    public void setExcuteUserId(Long excuteUserId) {
        this.excuteUserId = excuteUserId;
    }

    /**
     * 获取执行人用户名
     *
     * @return excute_user_name - 执行人用户名
     */
    public String getExcuteUserName() {
        return excuteUserName;
    }

    /**
     * 设置执行人用户名
     *
     * @param excuteUserName 执行人用户名
     */
    public void setExcuteUserName(String excuteUserName) {
        this.excuteUserName = excuteUserName;
    }

    /**
     * 获取审核人用户ID
     *
     * @return audit_user_id - 审核人用户ID
     */
    public Long getAuditUserId() {
        return auditUserId;
    }

    /**
     * 设置审核人用户ID
     *
     * @param auditUserId 审核人用户ID
     */
    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    /**
     * 获取审核人名称
     *
     * @return audit_user_name - 审核人名称
     */
    public String getAuditUserName() {
        return auditUserName;
    }

    /**
     * 设置审核人名称
     *
     * @param auditUserName 审核人名称
     */
    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    /**
     * 获取抄送人用户ID,多个抄送人用【，】分割
     *
     * @return send_user_ids - 抄送人用户ID,多个抄送人用【，】分割
     */
    public String getSendUserIds() {
        return sendUserIds;
    }

    /**
     * 设置抄送人用户ID,多个抄送人用【，】分割
     *
     * @param sendUserIds 抄送人用户ID,多个抄送人用【，】分割
     */
    public void setSendUserIds(String sendUserIds) {
        this.sendUserIds = sendUserIds;
    }

    /**
     * 获取1：未提交 2：已提交 3：已过期
     *
     * @return work_status - 1：未提交 2：已提交 3：已过期
     */
    public Integer getWorkStatus() {
        return workStatus;
    }

    /**
     * 设置1：未提交 2：已提交 3：已过期
     *
     * @param workStatus 1：未提交 2：已提交 3：已过期
     */
    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    /**
     * 获取1：合格 2：不合格
            
     *
     * @return work_result - 1：合格 2：不合格
            
     */
    public Integer getWorkResult() {
        return workResult;
    }

    /**
     * 设置1：合格 2：不合格
            
     *
     * @param workResult 1：合格 2：不合格
            
     */
    public void setWorkResult(Integer workResult) {
        this.workResult = workResult;
    }

    /**
     * 获取执行开始日期
     *
     * @return execute_begin_date - 执行开始日期
     */
    public Date getExecuteBeginDate() {
        return executeBeginDate;
    }

    /**
     * 设置执行开始日期
     *
     * @param executeBeginDate 执行开始日期
     */
    public void setExecuteBeginDate(Date executeBeginDate) {
        this.executeBeginDate = executeBeginDate;
    }

    /**
     * 获取执行结束日期
     *
     * @return execute_end_date - 执行结束日期
     */
    public Date getExecuteEndDate() {
        return executeEndDate;
    }

    /**
     * 设置执行结束日期
     *
     * @param executeEndDate 执行结束日期
     */
    public void setExecuteEndDate(Date executeEndDate) {
        this.executeEndDate = executeEndDate;
    }

    /**
     * 获取执行提醒时间
     *
     * @return execute_remind_time - 执行提醒时间
     */
    public Date getExecuteRemindTime() {
        return executeRemindTime;
    }

    /**
     * 设置执行提醒时间
     *
     * @param executeRemindTime 执行提醒时间
     */
    public void setExecuteRemindTime(Date executeRemindTime) {
        this.executeRemindTime = executeRemindTime;
    }

    /**
     * 获取执行截止时间
     *
     * @return execute_deadline - 执行截止时间
     */
    public Date getExecuteDeadline() {
        return executeDeadline;
    }

    /**
     * 设置执行截止时间
     *
     * @param executeDeadline 执行截止时间
     */
    public void setExecuteDeadline(Date executeDeadline) {
        this.executeDeadline = executeDeadline;
    }

    /**
     * 获取审核截止时间
     *
     * @return audit_deadline - 审核截止时间
     */
    public Date getAuditDeadline() {
        return auditDeadline;
    }

    /**
     * 设置审核截止时间
     *
     * @param auditDeadline 审核截止时间
     */
    public void setAuditDeadline(Date auditDeadline) {
        this.auditDeadline = auditDeadline;
    }

    /**
     * 获取任务表id
     *
     * @return task_id - 任务表id
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * 设置任务表id
     *
     * @param taskId 任务表id
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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
     * 获取版本号
     *
     * @return version - 版本号
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取实际得分
     *
     * @return score - 实际得分
     */
    public String getScore() {
        return score;
    }

    /**
     * 设置实际得分
     *
     * @param score 实际得分
     */
    public void setScore(String score) {
        this.score = score;
    }
}