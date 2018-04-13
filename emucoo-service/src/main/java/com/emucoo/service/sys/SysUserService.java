package com.emucoo.service.sys;
import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.SysUser;



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

}
