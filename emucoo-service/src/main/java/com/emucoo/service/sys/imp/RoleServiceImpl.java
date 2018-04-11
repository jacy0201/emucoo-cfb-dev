package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.base.ISystem;
import com.emucoo.mapper.DepartmentMapper;
import com.emucoo.mapper.DeptRoleUnionMapper;
import com.emucoo.mapper.RoleMapper;
import com.emucoo.mapper.RoleOfRoleMapper;
import com.emucoo.mapper.RolePermissionMapper;
import com.emucoo.model.DeptRoleUnion;
import com.emucoo.model.Role;
import com.emucoo.model.RoleOfRole;
import com.emucoo.model.RolePermission;
import com.emucoo.service.sys.RoleService;
import com.emucoo.utils.DateUtil;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import tk.mybatis.mapper.entity.Example;

/**
 * Created by fujg on 2017/2/8.
 */
@Transactional
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

  @Resource
  private RoleMapper roleMapper;
  @Resource
  private RolePermissionMapper rolePermissionMapper;
  @Resource
  private DeptRoleUnionMapper deptRoleUnionMapper;
  @Resource
  private DepartmentMapper departmentMapper;
  @Resource
  private RoleOfRoleMapper roleOfRoleMapper;


  @Transactional(readOnly = true)
  @Override
  public Role findByName(String name) {
    Role role = new Role();
    role.setName(name);
    return this.findOne(role);
  }

  @Override
  public Role findByUserId(Long userId) {
    return roleMapper.findByUserId(userId);
  }

  @Override
  public Boolean deleteRoleAndRolePermissionByRoleId(Long roleId) {
    //删除角色
    int count1 = this.deleteById(roleId);

    //级联删除该角色所关联的权限
    RolePermission rolePermission = new RolePermission();
    rolePermission.setRoleId(roleId);
    rolePermissionMapper.delete(rolePermission);
    return count1 == 1;
  }

  @Override
  public Boolean saveRoleInDet(Role role, Long dptId, Long dptLbId, Long roleLbId) {
    this.save(role);
    DeptRoleUnion deptRoleUnion = new DeptRoleUnion();
    deptRoleUnion.setDptId(dptId);
    deptRoleUnion.setRoleId(role.getId());
    deptRoleUnion.setUserId(ISystem.UN_MATCH_USER);
    deptRoleUnion.setModifyTime(new Date());
    deptRoleUnion.setCreateTime(new Date());
    deptRoleUnionMapper.insert(deptRoleUnion);
    return true;
  }

  @Override
  public void saveSubRoles(Long supRoleId,List<Long> subRoles){
    // delete old relation
    Example delExm = new Example(RoleOfRole.class);
    delExm.createCriteria().andEqualTo("supRoleId",supRoleId);
    roleOfRoleMapper.deleteByExample(delExm);

    //add new relation
    Date today = DateUtil.currentDate();
    List<RoleOfRole> roleOfRoles = subRoles.stream().map(roleId -> {
      RoleOfRole roleOfRole = new RoleOfRole();
      roleOfRole.setSupRoleId(supRoleId);
      roleOfRole.setRoleId(roleId);
      roleOfRole.setCreateTime(today);
      roleOfRole.setModifyTime(today);
      return roleOfRole;
    }).collect(Collectors.toList());

    if(roleOfRoles.isEmpty()){
      return ;
    }
    roleOfRoleMapper.insertList(roleOfRoles);
  }

  @Override
  public List<Role> list(Long pid){
    return roleMapper.list(pid);
  }

}
