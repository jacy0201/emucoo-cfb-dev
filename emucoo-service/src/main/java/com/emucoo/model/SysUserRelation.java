package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user_relation")
public class SysUserRelation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Transient
    private String userName;

    /**
     * parent_user_id上级id
     */
    @Column(name = "parent_user_id")
    private Long parentUserId;

    /**
     * parent_user_id 的 postId
     */
    @Column(name = "parent_post_id")
    private Long parentPostId;


    public Long getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(Long parentPostId) {
        this.parentPostId = parentPostId;
    }

    public String getParentPostName() {
        return parentPostName;
    }

    public void setParentPostName(String parentPostName) {
        this.parentPostName = parentPostName;
    }

    /**
     * 上级用户岗位
     */

    @Transient
    private String parentPostName;

    @Transient
    private String parentUserName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    /**
     * user_id 的岗位id
     */
    @Column(name = "post_id")

    private Long postId;

    @Transient
    private String postName;

    /**
     * user_id 的 dpt_id 
     */
    @Column(name = "dpt_id")
    private Long dptId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 数据创建人ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

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
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取parent_user_id下级id
     *
     * @return
     */
    public Long getParentUserId() {
        return parentUserId;
    }

    /**
     * 设置parent_user_id下级id
     *
     * @param parentUserId
     */
    public void setParentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
    }

    /**
     * 获取user_id 的岗位id
     *
     * @return post_id - user_id 的岗位id
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * 设置user_id 的岗位id
     *
     * @param postId user_id 的岗位id
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * 获取user_id 的 dpt_id 
     *
     * @return dpt_id - user_id 的 dpt_id 
     */
    public Long getDptId() {
        return dptId;
    }

    /**
     * 设置user_id 的 dpt_id 
     *
     * @param dptId user_id 的 dpt_id 
     */
    public void setDptId(Long dptId) {
        this.dptId = dptId;
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
}