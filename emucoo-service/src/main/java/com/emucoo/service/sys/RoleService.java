package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.Role;

import java.util.List;

/**
 *
 * Created by fujg on 2017/2/8.
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 根据角色名称查询角色对象信息
     * @param name
     * @return
     */
    Role findByName(String name);
    /**
     * 根据用户ID查询角色对象信息
     * @param userId
     * @return
     */
    Role findByUserId(Long userId);

    /**
     * 根据角色ID删除角色并级联删除该角色和权限的关联信息
     * @param roleId
     * @return
     */
    Boolean deleteRoleAndRolePermissionByRoleId(Long roleId);

    /**
     * 保存 角色 和 部门角色 unit
     * @param role  角色 object
     * @param dptId  部门id
     * @param dptLbId  部门标签id
     * @param roleLbId  角色标签id
     * @return 成功true or 失败false
     */
    Boolean saveRoleInDet(Role role, Long dptId, Long dptLbId, Long roleLbId);

    /**
     * 保存角色的下级角色list
     * @param supRoleId
     * @param subRoles
     */
    void saveSubRoles(Long supRoleId, List<Long> subRoles);

    List<Role> list(Long pid);
}
