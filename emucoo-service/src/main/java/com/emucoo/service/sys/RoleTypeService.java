package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.Role;
import com.emucoo.model.RoleType;

import java.util.List;

/**
 *
 */
public interface RoleTypeService extends BaseService<RoleType> {

    List<RoleType> list(String labelNames);
}
