package com.emucoo.manager.controller;

import com.emucoo.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysBaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getId();
	}

	protected Long getDeptId() {
		return getUser().getDptId();
	}

}
