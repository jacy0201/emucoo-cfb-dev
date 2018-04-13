package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.common.util.R;
import com.emucoo.model.SysUserToken;

import java.util.Map;

/**
 *
 * 系统用户Token 服务类
 *
 */
public interface SysUserTokenService extends BaseService<SysUserToken> {


	SysUserToken queryByToken(String token);
	
	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);
}
