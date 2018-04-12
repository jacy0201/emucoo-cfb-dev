package com.emucoo.manager.controller;

import com.emucoo.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysBaseController {

	@Resource
	public  HttpServletRequest request;

	@Resource
	public  HttpServletResponse response;

	/**
	 * 根据sessionID 获取用户信息
	 * @param sessionID
	 * @return
	 */
	public SysUser getCurrentUser(String sessionID) throws Exception{
		SessionKey key = new WebSessionKey(sessionID,request,response);
		Session se = SecurityUtils.getSecurityManager().getSession(key);
		Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
		SysUser sysUser = (SysUser)coll.getPrimaryPrincipal();

		if(sysUser!=null){
			SysUser user = (SysUser) se.getAttribute("currentUser");
			if(user==null){
				se.setAttribute("currentUser", user);
			}
			return sysUser;
		}else{
			return null;
		}

	}

}
