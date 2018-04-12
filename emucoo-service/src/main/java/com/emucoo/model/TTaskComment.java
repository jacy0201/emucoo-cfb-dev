package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_task_comment")
public class TTaskComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务事务ID
     */
    @Column(name = "loop_work_id")
    private Long loopWorkId;

    /**
     * 评论用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 评论用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 是否显示 0 ：显示  1：不显示
     */
    @Column(name = "is_show")
    private Boolean isShow;

    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 图片id，多个图片间用【,】分割
     */
    @Column(name = "img_ids")
    private String imgIds;

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
     * 评论的文本内容
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
     * 获取任务事务ID
     *
     * @return loop_work_id - 任务事务ID
     */
    public Long getLoopWorkId() {
        return loopWorkId;
    }

    /**
     * 设置任务事务ID
     *
     * @param loopWorkId 任务事务ID
     */
    public void setLoopWorkId(Long loopWorkId) {
        this.loopWorkId = loopWorkId;
    }

    /**
     * 获取评论用户ID
     *
     * @return user_id - 评论用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置评论用户ID
     *
     * @param userId 评论用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取评论用户名
     *
     * @return user_name - 评论用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置评论用户名
     *
     * @param userName 评论用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取是否显示 0 ：显示  1：不显示
     *
     * @return is_show - 是否显示 0 ：显示  1：不显示
     */
    public Boolean getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示 0 ：显示  1：不显示
     *
     * @param isShow 是否显示 0 ：显示  1：不显示
     */
    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    /**
     * @return is_del
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * @param isDel
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取图片id，多个图片间用【,】分割
     *
     * @return img_ids - 图片id，多个图片间用【,】分割
     */
    public String getImgIds() {
        return imgIds;
    }

    /**
     * 设置图片id，多个图片间用【,】分割
     *
     * @param imgIds 图片id，多个图片间用【,】分割
     */
    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
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
     * 获取评论的文本内容
     *
     * @return content - 评论的文本内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论的文本内容
     *
     * @param content 评论的文本内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}