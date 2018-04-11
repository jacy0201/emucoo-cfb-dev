package com.emucoo.service.sys.imp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.model.UserRole;
import com.emucoo.service.sys.UserRoleService;

/**
 * @author fujg
 * Created by fujg on 2017/2/16.
 */
@Transactional
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService{
    @Transactional(readOnly = true)
    @Override
    public UserRole findByUserIdAndRoleId(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return super.findOne(userRole);
    }
}
