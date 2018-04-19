package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.SysUser;
import com.emucoo.service.sys.SysUserRoleService;
import com.emucoo.service.sys.SysUserService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/sys/user")
@Api(description="用户管理")
public class SysUserController extends BaseResource {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表分页查询
	 * @return
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:user:list")
	public ApiResult list(@RequestBody ParamVo<SysUser> param){
		SysUser sysUser=param.getData();
		PageHelper.startPage(param.getPageNumber(), param.getPageSize(),"create_time desc");
	//	List<SysUser> userList = sysUserService.listUser(sysUser);
		return success("success");
	}

	/**
	 * 根据用户id 获取用户详情
	 */
	@PostMapping("/getUserById")
	@ApiOperation(value="获取用户详情")
	@ResponseBody
	public ApiResult getUserById(@RequestBody SysUser sysUser){
		if(null==sysUser.getId()){return fail("Id 不能为空!");}
		return success(sysUserService.findById(sysUser.getId()));
	}

	/**
	 * 查询是店长的用户
	 */
	@PostMapping("/getShopManagers")
	@ApiOperation(value="获取店长信息集合")
	public ApiResult getShopManagers(){
		SysUser sysUser=new SysUser();
		sysUser.setIsShopManager(true);
		sysUser.setIsDel(false);
		List<SysUser> listUser=sysUserService.findListByWhere(sysUser);
		return success(listUser);
	}


}
