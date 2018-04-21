package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.SysUserToken;

/**
 *
 * 系统用户Token 服务类
 *
 */
public interface SysUserTokenService extends BaseService<SysUserToken> {


	SysUserToken queryByToken(String token);
	
	/**
	 * 生成token
	 * @param id  用户id
	 */
	SysUserToken getToken(long id);


	/**
	 * 设置token过期
	 * @param id  用户id
	 */
	void expireToken(long id);
}
