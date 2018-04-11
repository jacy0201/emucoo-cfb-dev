package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.sys.TreeNodeVo;
import com.emucoo.model.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/30 17:49
 * @modified by:
 */
public interface DeptService extends BaseService<Department> {
    List<Department> listSubDept(Long pid);

    List<Department> queryDept(String dptName, Integer dptType, List<Long> dptLbs);

    Boolean updateSubDepts(Long deptId, List<String> subDeptIds);

    /**
     * 获取部门树结构 root
     * @param merchId 组织ID
     * @return TreeNodeVo
     */
    TreeNodeVo getTreeRoot(@Param("merchId") Long merchId);

    int updateLabels(Long deptId, String lbIds);

//    Boolean updateRoleRlat(Long deptId, List<String> addRoles);

    Long batchUpdateIsUse(List<String> idList, Boolean isUse);
}
