package com.emucoo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.user.UserLogin;
import com.emucoo.model.User;

/**
 * Created by fujg on 2017/1/19.
 */
public interface UserMapper extends MyMapper<User>{
    /**
     * 根据用户ID查询用户信息
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUserName(String username);

    List<User> listUserVo(@Param("username") String username,
                          @Param("realName") String realName,
                          @Param("mobile") String mobile,
                          @Param("email") String email,
                          @Param("labels") List<Long> labels,
                          @Param("status") Integer status);
    
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
     * @param mobile
     * @param password
     */
    void loginOut(@Param("pushToken") String pushToken);

    User findByDptId(Long dptId);
}
