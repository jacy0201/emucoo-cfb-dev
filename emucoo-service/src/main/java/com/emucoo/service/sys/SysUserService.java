package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.sys.UserBrandAreaShop;
import com.emucoo.dto.modules.user.UserQuery;
import com.emucoo.model.SysUser;

import java.util.HashMap;
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
	 * 根据岗位ID查询用户列表
	 * @return
	 */
	List<SysUser> listByPostId(HashMap map) ;

	/**
	 * 根据店铺ID查询用户列表
	 * @return
	 */
	List<SysUser> listByShopId(HashMap map) ;


	/**
	 * 根据条件分页查询用户列表
	 */
	List<SysUser> queryList(UserQuery userQuery);

	/**
	 * 创建用户
	 * @param sysUser
	 */
	void addUser(SysUser sysUser);

	/**
	 * 创建用户
	 * @param sysUser
	 */
	void editUser(SysUser sysUser);


	/**
	 * 设置用户品牌分区
	 */
	void setBrandArea(UserBrandAreaShop userBrandAreaShop,Long createUserId);

	/**
	 * 设置用户店铺
	 */
	void setShop(UserBrandAreaShop userBrandAreaShop,Long createUserId);

	/**
	 * 批量修改用户
	 * @param userList
	 */
	void modifyUserBatch(List<SysUser> userList);

}
