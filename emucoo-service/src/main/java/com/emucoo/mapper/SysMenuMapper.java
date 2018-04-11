package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysMenu;

import java.util.List;

public interface SysMenuMapper extends MyMapper<SysMenu> {

    /**
     * 根据用户ID查询该用户所拥有的权限列表
     * @param userId
     * @return
     */
    List<SysMenu> findListPermissionByUserId(Long userId);
}