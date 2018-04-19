package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.SysUser;

import java.util.List;


/**
 *
 * 系统用户服务类
 *
 */
public interface SysUserService extends BaseService<SysUser> {

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);


	/**
	 * 根据条件参数查询用户列表
	 * @param sysUser
	 * @return
	 */
	List<SysUser> listUser(SysUser sysUser) ;


}
