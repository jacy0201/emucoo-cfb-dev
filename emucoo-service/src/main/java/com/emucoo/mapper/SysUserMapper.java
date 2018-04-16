package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.user.UserQuery;
import com.emucoo.dto.modules.user.UserVo;
import com.emucoo.model.SysUser;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据条件参数查询用户列表
     * @param realName
     * @param username
     * @param mobile
     * @param email
     * @param dptId
     * @param shopId
     * @param postId
     * @param status
     * @return
     */
    List<UserVo> listUser(String realName,String username,String mobile,String email,String dptId,String shopId,String postId,Integer status) ;
}