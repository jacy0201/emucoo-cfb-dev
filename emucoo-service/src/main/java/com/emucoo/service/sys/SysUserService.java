package com.emucoo.service.sys;
import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.user.UserQuery;
import com.emucoo.dto.modules.user.UserVo;
import com.emucoo.model.SysUser;

import java.util.List;


/**
 *
 * 系统用户服务类
 *
 */
public interface SysUserService extends BaseService<SysUser> {

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUser queryByUserName(String username);


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
