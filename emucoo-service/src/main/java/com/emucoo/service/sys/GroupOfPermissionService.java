package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.groupPermission.GroupOfPermissionVo;
import com.emucoo.model.GroupOfPermission;
import com.emucoo.model.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author fujg
 * Created by fujg on 2017/2/16.
 */
public interface GroupOfPermissionService extends BaseService<GroupOfPermission> {


  void insertOrDelete(Long groupId, List<GroupOfPermissionVo> list);

  Map<String, List<Permission>> permissions(Long id);
}
