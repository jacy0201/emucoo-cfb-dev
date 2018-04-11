package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.GroupPermission;
import com.emucoo.model.UserPermission;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface UserPermissionService extends BaseService<UserPermission> {
    List<UserPermission> listUserPermissionGroup(Long userId);

    /**
     * 添加用户和权限组的关系
     * @param userId
     * @param groupIds
     * @return
     * @throws Exception
     */
    int addUserPermissionGroup(Long userId, List<Long> groupIds) throws Exception;
}
