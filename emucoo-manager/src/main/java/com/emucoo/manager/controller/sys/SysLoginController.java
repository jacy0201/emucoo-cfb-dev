package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.common.util.MD5Util;
import com.emucoo.dto.modules.sys.SysUserLogin;
import com.emucoo.manager.shiro.ShiroUtils;
import com.emucoo.model.SysUser;
import com.emucoo.model.SysUserToken;
import com.emucoo.service.sys.SysUserService;
import com.emucoo.service.sys.SysUserTokenService;
import io.swagger.annotations.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录相关
 */
@RestController
@Api(description="登录/退出" )
public class SysLoginController extends BaseResource {

	@Autowired
	private SysUserTokenService sysUserTokenService;

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 登录
	 */
	@PostMapping(value = "/sys/login")
	@ApiOperation(value="用户登录")
	public ApiResult<SysUserToken> login(@RequestBody SysUserLogin sysUserLogin){

		SysUser user=null;
		//用户名
		SysUser sysUser1=new SysUser();
		sysUser1.setUsername(sysUserLogin.getUsername());
		user = sysUserService.findOne(sysUser1);

		//手机号
		if(user==null){
			SysUser sysUser2=new SysUser();
			sysUser2.setMobile(sysUserLogin.getUsername());
			user = sysUserService.findOne(sysUser2);
		}
		//邮箱
		if(user==null){
			SysUser sysUser3=new SysUser();
			sysUser3.setEmail(sysUserLogin.getUsername());
			user = sysUserService.findOne(sysUser3);
		}

		//账号不存在、密码错误
		if(user==null || !user.getPassword().equalsIgnoreCase(new Sha256Hash(MD5Util.getMd5Hash(sysUserLogin.getPassword()),user.getSalt()).toHex())) {
			return fail(ApiExecStatus.FAIL,"账号或密码不正确");
		}

		//账号锁定
		if(null==user.getStatus() || user.getStatus()!=0){
			return fail(ApiExecStatus.FAIL,"账号未启用,请联系管理员");
		}

		//生成token，并保存到数据库
		SysUserToken sysUserToken = sysUserTokenService.getToken(user.getId());
		return success(sysUserToken);
	}

	/**
	 * 退出
	 */
	@PostMapping(value = "/sys/logout")
	@ApiOperation(value="用户退出")
	public ApiResult logout(){
		sysUserTokenService.expireToken(ShiroUtils.getUserId());
		ShiroUtils.logout();
		return success("success");
	}

}
