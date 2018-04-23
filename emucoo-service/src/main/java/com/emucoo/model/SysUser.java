package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    private String password;

    private String remark;

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
     * 用户状态：0-启用；1-停用；2-锁定
     */
    private Integer status;


    /**
     * 用户拥有的岗位集合
     */
    @Transient
    private List<SysPost> postList;


    /**
     * 用户负责的分区集合
     */
    @Transient
    private List<SysArea> AreaList;


    /**
     * 用户负责的品牌集合
     */
    @Transient
    private List<TBrandInfo> brandList;


    /**
     *用户所负责的店铺集合
     */
    @Transient
    private List<TShopInfo> shopList;



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    @Column(name = "org_id")
    private Long orgId;

    public Boolean getIsShopManager() {
        return isShopManager;
    }

    public void setIsShopManager(Boolean isShopManager) {
        this.isShopManager = isShopManager;
    }

    /**
     * 是否是店长：0-否；1-是
     *
     */
    @Column(name = "is_shop_manager")
    private Boolean isShopManager;

    /**
     * 部门名称
     */
    @Transient
    private String dptName;


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


    public List<SysPost> getPostList() {
        return postList;
    }

    public void setPostList(List<SysPost> postList) {
        this.postList = postList;
    }

    public List<SysArea> getAreaList() {
        return AreaList;
    }

    public void setAreaList(List<SysArea> areaList) {
        AreaList = areaList;
    }

    public List<TBrandInfo> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<TBrandInfo> brandList) {
        this.brandList = brandList;
    }

    public List<TShopInfo> getShopList() {
        return shopList;
    }

    public void setShopList(List<TShopInfo> shopList) {
        this.shopList = shopList;
    }

}