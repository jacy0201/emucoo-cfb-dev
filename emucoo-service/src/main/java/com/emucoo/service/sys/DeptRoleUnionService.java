package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.dept.UserDeptVo;
import com.emucoo.dto.modules.sys.UserRoleVo;
import com.emucoo.model.DeptRoleUnion;

import java.util.List;

public interface DeptRoleUnionService extends BaseService<DeptRoleUnion> {
    void updateUserRoles(Long userId , List<UserRoleVo> roleList);

    List<String> listUserRoleName(Long userId);

    Boolean updateDeptRoleRlat(Long deptId, List<String> addRoles);

    List<UserDeptVo> listUserDeptVo(Long userId);
}
