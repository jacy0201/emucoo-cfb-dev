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
     * child_user_id下级id
     */
    @Column(name = "child_user_id")
    private Long childUserId;

    /**
     * child_user_id 的 postId
     */
    @Column(name = "child_post_id")
    private Long childPostId;


    public Long getChildPostId() {
        return childPostId;
    }

    public void setChildPostId(Long childPostId) {
        this.childPostId = childPostId;
    }

    public String getChildPostName() {
        return childPostName;
    }

    public void setChildPostName(String childPostName) {
        this.childPostName = childPostName;
    }

    /**
     * 下级用户岗位
     */

    @Transient
    private String childPostName;

    @Transient
    private String childUserName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChildUserName() {
        return childUserName;
    }

    public void setChildUserName(String childUserName) {
        this.childUserName = childUserName;
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
     * 获取child_user_id下级id
     *
     * @return child_user_id - child_user_id下级id
     */
    public Long getChildUserId() {
        return childUserId;
    }

    /**
     * 设置child_user_id下级id
     *
     * @param childUserId child_user_id下级id
     */
    public void setChildUserId(Long childUserId) {
        this.childUserId = childUserId;
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