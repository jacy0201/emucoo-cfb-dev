package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.RoleOfRoleMapper;
import com.emucoo.model.RoleOfRole;
import com.emucoo.model.UserOfUser;
import com.emucoo.service.sys.RoleOfRoleService;
import com.emucoo.service.sys.UserOfUserService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class RoleOfRoleServiceImpl extends BaseServiceImpl<RoleOfRole> implements RoleOfRoleService {
    @Resource
    private RoleOfRoleMapper roleOfRoleMapper;

    @Override
    public List<RoleOfRole> listUserRoleOfRole(Long userId){
        return roleOfRoleMapper.listUserRoleOfRole(userId);
    }
}
