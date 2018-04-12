package com.emucoo.manager.controller.sys;

import com.emucoo.common.util.R;
import com.emucoo.common.util.StringUtil;
import com.emucoo.manager.controller.SysBaseController;
import com.emucoo.manager.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录相关
 */
@RestController
public class SysLoginController extends SysBaseController {
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(String username, String password) {
		Subject subject=null;
		Map<String,Object> map=null;
		try{
			map=new HashMap<String,Object>();
			subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
			//返回sessionId作为token
			String sessionId = (String) subject.getSession().getId();
			map.put("token",sessionId);
		}catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error("账号或密码不正确");
		}catch (LockedAccountException e) {
			return R.error("账号已被锁定,请联系管理员");
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
		return R.ok(map);
	}
	
	/**
	 * 退出
	 */
	@RequestMapping(value = "/sys/logout", method = RequestMethod.GET)
	public R logout(@RequestAttribute("token") String token) {
		Map<String,String> map=new HashMap<String,String>();;
		if(StringUtil.isNotEmpty(token)){
			SessionKey key = new WebSessionKey(token,request,response);
			Session se = SecurityUtils.getSecurityManager().getSession(key);
			se.stop();
			return R.ok();
		}else{
			return R.error("token 不能为空");
		}

	}

	/**
	 * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
	 * @return
	 */
	@RequestMapping(value = "/unAuth",method = RequestMethod.GET)
	@ResponseBody
	public Object unAuth() {
		return R.error(1000000,"未登录");
	}
}
