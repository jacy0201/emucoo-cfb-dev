package com.emucoo.manager.controller.sys;
import com.emucoo.common.Constant;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.common.exception.RRException;
import com.emucoo.common.util.R;
import com.emucoo.manager.shiro.ShiroUtils;
import com.emucoo.model.SysMenu;
import com.emucoo.service.sys.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 菜单资源管理
 */
@RestController
@RequestMapping("/sys/menu")
@ApiIgnore()
public class SysMenuController extends BaseResource {
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 导航菜单
	 */
	@PostMapping("/nav")
	public R nav(){
		List<SysMenu> menuList = sysMenuService.getUserMenuList(ShiroUtils.getUserId());
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 所有菜单列表
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public List<SysMenu> list(){
		List<SysMenu> menuList = sysMenuService.findAll();
		for(SysMenu sysMenu : menuList){
			SysMenu parentMenuEntity = sysMenuService.findById(sysMenu.getParentId());
			if(parentMenuEntity != null){
				sysMenu.setParentName(parentMenuEntity.getName());
			}
		}

		return menuList;
	}


	/**
	 * 菜单信息
	 */
	@PostMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public R info(@PathVariable("menuId") Long menuId){
		SysMenu menu = sysMenuService.findById(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public R save(@RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.save(menu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public R update(@RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.updateSelective(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public R delete(long menuId){
		if(menuId <= 31){
			return R.error("系统菜单，不能删除");
		}

		//判断是否有子菜单或按钮
		List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		sysMenuService.deleteById(menuId);

		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenu menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenu parentMenu = sysMenuService.findById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
