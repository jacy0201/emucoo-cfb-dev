package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.common.util.R;
import com.emucoo.model.SysUser;
import com.emucoo.service.sys.SysUserService;
import com.emucoo.service.sys.SysUserTokenService;
import io.swagger.annotations.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;

/**
 * 登录相关
 */
@RestController
@Api(description="用户登录" )
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
	@ApiImplicitParams({
			@ApiImplicitParam(name="username",value="账号",required=true,paramType="query"),
			@ApiImplicitParam(name="password",value="密码",required=true,paramType="query")
	})
	public R login(String username, String password)throws IOException {

		//用户信息
		SysUser sysUser=new SysUser();
		sysUser.setUsername(username);
		SysUser user = sysUserService.findOne(sysUser);
		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus()!=0){
			return R.error("账号已被锁定或停用,请联系管理员");
		}

		//生成token，并保存到数据库
		R r = sysUserTokenService.createToken(user.getId());
		return r;
	}

}
