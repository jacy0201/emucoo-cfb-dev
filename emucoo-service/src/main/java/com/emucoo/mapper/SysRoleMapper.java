package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysRole;

public interface SysRoleMapper extends MyMapper<SysRole> {

    /**
     * 根据用户ID查询角色对象信息
     * @param userId
     * @return
     */
    SysRole findByUserId(Long userId);

}