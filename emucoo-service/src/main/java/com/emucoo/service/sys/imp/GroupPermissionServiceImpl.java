package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.GroupPermissionMapper;
import com.emucoo.model.GroupPermission;
import com.emucoo.model.Permission;
import com.emucoo.service.sys.GroupPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LXC
 * @date 2017/5/10
 */
@Service
public class GroupPermissionServiceImpl extends BaseServiceImpl<GroupPermission> implements GroupPermissionService {

  @Resource
  private GroupPermissionMapper groupPermissionMapper;


  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) throws Exception {
    GroupPermission groupPermission = groupPermissionMapper.selectByPrimaryKey(id);
    if (groupPermission.getIsUse()) {
      throw new Exception("权限组状态为启用");
    }
    groupPermissionMapper.deleteByPrimaryKey(id);
  }

  @Override
  public List<GroupPermission> selectGroupPermission(String sortField, String sort, String name) {
    return groupPermissionMapper.selectGroupPermission(sortField, sort, name);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void isStop(List<Long> ids, Boolean isStop) {
    Example example = new Example(GroupPermission.class);
    example.createCriteria().andIn("id", ids);

    GroupPermission groupPermission = new GroupPermission();
    groupPermission.setIsUse(isStop);
    groupPermissionMapper.updateByExampleSelective(groupPermission, example);
  }

  @Override
  public List<Permission> listPermission(Long groupId){
    return groupPermissionMapper.listPermission(groupId);
  }
}
