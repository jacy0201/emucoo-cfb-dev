package com.emucoo.service.sys;


import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.SysRoleMenu;

import java.util.List;

/**
 * 角色与菜单对应关系
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenu> {
    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);
}
