package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_business_msg")
public class TBusinessMsg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联的模块id
     */
    @Column(name = "union_id")
    private Long unionId;

    /**
     * 消息主体模块类型(1：常规任务，2：指派任务：3：改善任务，4：巡店安排，5：评论，6：工作备忘)
     */
    @Column(name = "union_type")
    private Byte unionType;

    /**
     * 功能类型（1:创建提醒，2：执行提醒，3：待审提醒，4：超时提醒，5：审核完成提醒，6：评论提醒）
     */
    @Column(name = "function_type")
    private Byte functionType;

    /**
     * 消息发送时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 是否已读（0：未读，1：已读）
     */
    @Column(name = "is_read")
    private Boolean isRead;

    /**
     * 是否发送
     */
    @Column(name = "is_send")
    private Boolean isSend;

    /**
     * 推送状态（1：成功 2：失败 ）
     */
    @Column(name = "send_status")
    private Byte sendStatus;

    /**
     * 消息接收人id
     */
    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 发送失败原因
     */
    @Column(name = "send_fail_reason")
    private String sendFailReason;

    @Column(name = "org_id")
    private Long orgId;

    /**
     * 消息内容
     */
    private String content;

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
     * 获取关联的模块id
     *
     * @return union_id - 关联的模块id
     */
    public Long getUnionId() {
        return unionId;
    }

    /**
     * 设置关联的模块id
     *
     * @param unionId 关联的模块id
     */
    public void setUnionId(Long unionId) {
        this.unionId = unionId;
    }

    public Byte getUnionType() {
        return unionType;
    }

    public void setUnionType(Byte unionType) {
        this.unionType = unionType;
    }

    public Byte getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Byte functionType) {
        this.functionType = functionType;
    }

    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
    }

    /**
     * 获取消息发送时间
     *
     * @return send_time - 消息发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置消息发送时间
     *
     * @param sendTime 消息发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取是否已读（0：未读，1：已读）
     *
     * @return is_read - 是否已读（0：未读，1：已读）
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * 设置是否已读（0：未读，1：已读）
     *
     * @param isRead 是否已读（0：未读，1：已读）
     */
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * 获取是否发送
     *
     * @return is_send - 是否发送
     */
    public Boolean getIsSend() {
        return isSend;
    }

    /**
     * 设置是否发送
     *
     * @param isSend 是否发送
     */
    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }

    public Byte getSendStatus() {
        return sendStatus;
    }

    /**
     * 获取消息接收人id
     *
     * @return receiver_id - 消息接收人id
     */
    public Long getReceiverId() {
        return receiverId;
    }

    /**
     * 设置消息接收人id
     *
     * @param receiverId 消息接收人id
     */
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
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
     * 获取发送失败原因
     *
     * @return send_fail_reason - 发送失败原因
     */
    public String getSendFailReason() {
        return sendFailReason;
    }

    /**
     * 设置发送失败原因
     *
     * @param sendFailReason 发送失败原因
     */
    public void setSendFailReason(String sendFailReason) {
        this.sendFailReason = sendFailReason;
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
     * 获取消息内容
     *
     * @return content - 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}