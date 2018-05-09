package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
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
     * 根据岗位ID查询用户列表
     * @return
     */
    List<SysUser> listByPostId(HashMap map) ;

    /**
     * 根据店铺ID查询用户列表
     * @return
     */
    List<SysUser> listByShopId(HashMap map) ;

    void resetPwd(@Param("mobile") String mobile, @Param("password") String password);

    void loginOut(String pushToken);


    List<String> findPushTokenByIds(List<Integer> asList);

    SysUser fetchOneByMobile(String mobile);

    SysUser fetchOneByEmail(String mobile);

    SysUser fetchOneByUsername(String mobile);

    List<SysUser> fetchAllUsers();

    List<SysUser> fetchUsersByAssociateShop(long userId);
}