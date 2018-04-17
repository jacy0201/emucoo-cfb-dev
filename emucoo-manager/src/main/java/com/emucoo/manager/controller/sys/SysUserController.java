package com.emucoo.manager.controller.sys;

import com.emucoo.common.util.R;
import com.emucoo.dto.base.Page;
import com.emucoo.dto.modules.user.UserQuery;
import com.emucoo.dto.modules.user.UserVo;
import com.emucoo.manager.controller.AbstractController;
import com.emucoo.service.sys.SysUserRoleService;
import com.emucoo.service.sys.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统用户
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表分页查询
	 * @param page
	 * @param userQuery
	 * @return
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:user:list")
	R list(Page page, UserQuery userQuery){
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getSortField() + " " + page.getSort());
		List<UserVo> userList = sysUserService.listUser(userQuery.getRealName(),userQuery.getUsername(),userQuery.getMobile(),userQuery.getEmail(),userQuery.getDptId(),userQuery.getShopId(),userQuery.getPostId(),userQuery.getStatus());
		return R.ok().put("page", new PageInfo(userList));
	}

}
