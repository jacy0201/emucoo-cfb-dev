package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.RoleOfRole;

import java.util.List;

public interface RoleOfRoleService extends BaseService<RoleOfRole>{
    List<RoleOfRole> listUserRoleOfRole(Long userId);
}
