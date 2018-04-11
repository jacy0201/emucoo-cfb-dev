package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.UserRole;

/**
 * @author fujg
 * Created by fujg on 2017/2/16.
 */
public interface UserRoleService extends BaseService<UserRole> {
    /**
     * 根据用户ID和角色ID查询用户和角色绑定信息
     * @param userId
     * @param roleId
     * @return
     */
    UserRole findByUserIdAndRoleId(Long userId, Long roleId);
}
