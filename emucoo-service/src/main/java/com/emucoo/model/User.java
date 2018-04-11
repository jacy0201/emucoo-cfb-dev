package com.emucoo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.emucoo.common.base.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 * Created by fujg on 2017/1/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_user")
public class User extends BaseEntity {

    /**
     * 标签ID，多个ID中间用【,】分隔
     */
    @Column(name = "lb_ids")
    private String lbIds;

    /**
     * 用户名称
     */
    private String username;

    private String password;

    private String remark;

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
     * 性别
     */
    private String sex;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态1：启用   0：停用
     */
    @Column(name = "is_use")
    private Boolean isUse;

    /**
     * 是否锁 0：正常 1：锁定
     */
    @Column(name = "is_lock")
    private Boolean isLock;

    /**
     * 是否逻辑删除0：正常1：逻辑删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 是否是管理员1：是管理员 其它不是管理员
     */
    @Column(name = "is_admin")
    private Boolean isAdmin;

    /**
     * 登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    @Column(name = "head_img_url")
    private String headImgUrl;

    @Column(name = "push_token")
    private String pushToken;

    @Transient
    private String roleNames;

    /**
     * 便签数量
     */
    @Transient
    private Integer labelNum;

    /**
     * 下属数量
     */
    @Transient
    private Integer subNum;

}
