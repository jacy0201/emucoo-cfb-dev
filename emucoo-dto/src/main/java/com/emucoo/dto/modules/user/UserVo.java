package com.emucoo.dto.modules.user;

import java.util.Date;

import com.emucoo.dto.modules.demo.demoVo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserVo {

	/**
     * 用户ID
     */
    private Long id;

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
     * 是否锁 0：正常 1：锁定
     */
    private Boolean isLock;

    /**
     * 是否逻辑删除0：正常1：逻辑删除
     */
    private Boolean isDel;

    /**
     * 是否是管理员1：是管理员 其它不是管理员
     */
    private Boolean isAdmin;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}
