package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import javax.persistence.*;

@Table(name = "sys_user_post")
public class SysUserPost extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id,sys_user表主键
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 岗位id
     */
    @Column(name = "post_id")
    private Long postId;

    /**
     * 1-已删除；0-未删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户id,sys_user表主键
     *
     * @return user_id - 用户id,sys_user表主键
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id,sys_user表主键
     *
     * @param userId 用户id,sys_user表主键
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取岗位id
     *
     * @return post_id - 岗位id
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * 设置岗位id
     *
     * @param postId 岗位id
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * 获取1-已删除；0-未删除
     *
     * @return is_del - 1-已删除；0-未删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置1-已删除；0-未删除
     *
     * @param isDel 1-已删除；0-未删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}