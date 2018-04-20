package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.SysUserRelation;

import java.util.List;

/**
 * 用户关系管理
 *
 */
public interface SysUserRelationService extends BaseService<SysUserRelation> {

    List<SysUserRelation> listUserRelation(Long dptId);

}
