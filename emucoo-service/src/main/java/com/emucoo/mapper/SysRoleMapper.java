package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysRole;

public interface SysRoleMapper extends MyMapper<SysRole> {
    SysRole findByUserId(Long id);
}