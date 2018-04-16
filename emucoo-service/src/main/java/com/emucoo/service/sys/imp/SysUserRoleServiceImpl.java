package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysUserRoleMapper;
import com.emucoo.model.SysUserRole;
import com.emucoo.service.sys.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户与角色对应关系
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements SysUserRoleService {
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		Example example=new Example(SysUserRole.class);
		example.createCriteria().andEqualTo("user_id",userId);
		sysUserRoleMapper.deleteByExample(example);
		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}
		//保存用户与角色关系
		List<SysUserRole> list = new ArrayList<>(roleIdList.size());
		for(Long roleId : roleIdList){
			SysUserRole sysUserRoleEntity = new SysUserRole();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);
			list.add(sysUserRoleEntity);
		}
		sysUserRoleMapper.insertList(list);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return sysUserRoleMapper.deleteBatch(roleIds);
	}
}
