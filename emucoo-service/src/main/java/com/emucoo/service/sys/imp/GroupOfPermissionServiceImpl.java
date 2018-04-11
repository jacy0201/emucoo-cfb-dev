package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.groupPermission.GroupOfPermissionVo;
import com.emucoo.mapper.GroupOfPermissionMapper;
import com.emucoo.model.GroupOfPermission;
import com.emucoo.model.Permission;
import com.emucoo.service.sys.GroupOfPermissionService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LXC
 * @date 2017/5/10
 */
@Service
public class GroupOfPermissionServiceImpl extends BaseServiceImpl<GroupOfPermission> implements GroupOfPermissionService {

  @Resource
  private GroupOfPermissionMapper groupOfPermissionMapper;

  @Override
  public void insertOrDelete(Long groupId,List<GroupOfPermissionVo> list) {
    Long id = 0L;
    if (list.size() > 0) {
      id = list.get(0).getGroupId();
    }

    Example example = new Example(GroupOfPermission.class);
    example.createCriteria().andEqualTo("groupId", groupId);
    groupOfPermissionMapper.deleteByExample(example);

    List<GroupOfPermission> data = Lists.newArrayList();

    Date date = new Date();
    list.forEach(d -> {
      GroupOfPermission groupOfPermission = new GroupOfPermission();
      BeanUtils.copyProperties(d, groupOfPermission);
      groupOfPermission.setGroupId(groupId);
      groupOfPermission.setCreateTime(date);
      groupOfPermission.setModifyTime(date);
      data.add(groupOfPermission);
    });
    if(data.isEmpty()){
      return;
    }
    groupOfPermissionMapper.insertList(data);
  }

  @Override
  public Map<String, List<Permission>> permissions(Long id) {
    List<Permission> permissions = groupOfPermissionMapper.permissions(id);
    return permissions.stream().collect(Collectors.groupingBy(Permission::getType));
  }
}
