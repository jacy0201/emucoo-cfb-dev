package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.GroupPermission;
import com.emucoo.model.Permission;

import java.util.List;

/**
 * @author fujg
 * Created by fujg on 2017/2/16.
 */
public interface GroupPermissionService extends BaseService<GroupPermission> {

  void delete(Long id) throws Exception;

  List<GroupPermission> selectGroupPermission(String sortField,
                                              String sort, String name);

  void isStop(List<Long> ids, Boolean isStop);

  List<Permission> listPermission(Long groupId);
}
