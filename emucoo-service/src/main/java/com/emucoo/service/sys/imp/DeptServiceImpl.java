package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.sys.TreeNodeVo;
import com.emucoo.mapper.DepartmentMapper;
import com.emucoo.mapper.DeptRoleUnionMapper;
import com.emucoo.model.Department;
import com.emucoo.model.DeptRoleUnion;
import com.emucoo.model.TreeNode;
import com.emucoo.service.sys.DeptService;
import com.emucoo.utils.TreeUtil;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.emucoo.dto.base.ISystem.*;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/30 17:49
 * @modified by:
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<Department> implements DeptService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private DeptRoleUnionMapper deptRoleUnionMapper;

    @Override
    public List<Department> listSubDept(Long pid){
        return departmentMapper.listSubDept(pid);
    }

    @Override
    public List<Department> queryDept(String dptName, Integer dptType, List<Long> dptLbs) {
        return departmentMapper.queryDept(dptName,dptType,dptLbs);
    }

    @Override
    public Boolean updateSubDepts(Long deptId, List<String> subDeptIds) {

        Example delExmp = new Example (Department.class);
        delExmp.createCriteria().andEqualTo ("parentId",deptId);

        Department department = new Department ();
        department.setParentId (SYS_DEFALT_DEPT);
        departmentMapper.updateByExampleSelective (department,delExmp);

        Example addExmp = new Example (Department.class);
        addExmp.createCriteria().andIn ("id",subDeptIds);
        department.setParentId (deptId);
        departmentMapper.updateByExampleSelective (department,addExmp);
        return true;
    }

    @Override
    public TreeNodeVo getTreeRoot(@Param("merchId") Long merchId) {
        List<TreeNode> treeNodes = departmentMapper.getTreeNode (merchId);
        return TreeUtil.getTreeRoot (treeNodes);
    }

    @Override
    public int updateLabels(Long deptId, String lbIds) {
        Example example = new Example (Department.class);
        example.createCriteria().andEqualTo ("id",deptId);
        Department department = new Department ();
        department.setLbIds(lbIds);
        return departmentMapper.updateByExampleSelective (department,example);
    }
    @Override
    public Long batchUpdateIsUse(List<String> idList, Boolean isUse) {
        return departmentMapper.batchUpdateIsUse(idList, isUse);
    }
}
