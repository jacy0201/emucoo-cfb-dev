package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.SysUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	 * 根据岗位ID查询用户列表
	 * @return
	 */
	List<SysUser> listByPostId(HashMap map) ;

	/**
	 * 根据店铺ID查询用户列表
	 * @return
	 */
	List<SysUser> listByShopId(HashMap map) ;


}
