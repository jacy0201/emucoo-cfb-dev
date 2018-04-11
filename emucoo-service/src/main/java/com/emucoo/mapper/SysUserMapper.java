package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.user.UserLogin;
import com.emucoo.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {

    /**
     * 根据用户ID查询用户信息
     * @param id
     * @return
     */
    SysUser findById(Long id);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser findByUserName(String username);


    /**
     * 用户登录
     * @return
     */
    UserLogin getUserLogin(@Param("mobile") String mobile, @Param("password") String password);

    /**
     * 密码重置
     * @param mobile
     * @param password
     */
    void resetPwd(@Param("mobile") String mobile, @Param("password") String password);

    /**
     * 用户退出
     * @param pushToken
     */
    void loginOut(@Param("pushToken") String pushToken);


    List<SysUser> listUserVo(@Param("username") String username,
                          @Param("realName") String realName,
                          @Param("mobile") String mobile,
                          @Param("email") String email,
                          @Param("labels") List<Long> labels,
                          @Param("status") Integer status);

}