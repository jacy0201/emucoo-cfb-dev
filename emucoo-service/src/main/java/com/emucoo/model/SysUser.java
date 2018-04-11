package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser extends BaseEntity {
    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    private String password;

    private String remark;

    /**
     * 上级id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 部门id ,关联sys_dept表主键
     */
    @Column(name = "dpt_id")
    private Long dptId;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 性别： 1-男  ； 2-女 ；3-其他
     */
    private Byte sex;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否锁 0：正常 1：锁定
     */
    @Column(name = "is_lock")
    private Boolean isLock;

    /**
     * 0：停用     1：启用
     */
    @Column(name = "is_use")
    private Boolean isUse;

    /**
     * 是否是管理员1：是管理员 其它不是管理员
     */
    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "head_img_url")
    private String headImgUrl;

    @Column(name = "push_token")
    private String pushToken;

    /**
     * 登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

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
     * 获取用户ID
     *
     * @return id - 用户ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置用户ID
     *
     * @param id 用户ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名称
     *
     * @return username - 用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名称
     *
     * @param username 用户名称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取上级id
     *
     * @return parent_id - 上级id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置上级id
     *
     * @param parentId 上级id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取部门id ,关联sys_dept表主键
     *
     * @return dpt_id - 部门id ,关联sys_dept表主键
     */
    public Long getDptId() {
        return dptId;
    }

    /**
     * 设置部门id ,关联sys_dept表主键
     *
     * @param dptId 部门id ,关联sys_dept表主键
     */
    public void setDptId(Long dptId) {
        this.dptId = dptId;
    }

    /**
     * 获取盐值
     *
     * @return salt - 盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐值
     *
     * @param salt 盐值
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取真实姓名
     *
     * @return real_name - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取性别： 1-男  ； 2-女 ；3-其他
     *
     * @return sex - 性别： 1-男  ； 2-女 ；3-其他
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置性别： 1-男  ； 2-女 ；3-其他
     *
     * @param sex 性别： 1-男  ； 2-女 ；3-其他
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取是否锁 0：正常 1：锁定
     *
     * @return is_lock - 是否锁 0：正常 1：锁定
     */
    public Boolean getIsLock() {
        return isLock;
    }

    /**
     * 设置是否锁 0：正常 1：锁定
     *
     * @param isLock 是否锁 0：正常 1：锁定
     */
    public void setIsLock(Boolean isLock) {
        this.isLock = isLock;
    }

    /**
     * 获取0：停用     1：启用
     *
     * @return is_use - 0：停用     1：启用
     */
    public Boolean getIsUse() {
        return isUse;
    }

    /**
     * 设置0：停用     1：启用
     *
     * @param isUse 0：停用     1：启用
     */
    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    /**
     * 获取是否是管理员1：是管理员 其它不是管理员
     *
     * @return is_admin - 是否是管理员1：是管理员 其它不是管理员
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * 设置是否是管理员1：是管理员 其它不是管理员
     *
     * @param isAdmin 是否是管理员1：是管理员 其它不是管理员
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return head_img_url
     */
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    /**
     * @param headImgUrl
     */
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    /**
     * @return push_token
     */
    public String getPushToken() {
        return pushToken;
    }

    /**
     * @param pushToken
     */
    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    /**
     * 获取登录时间
     *
     * @return login_time - 登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置登录时间
     *
     * @param loginTime 登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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