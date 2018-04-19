package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysUserMapper;
import com.emucoo.model.SysUser;
import com.emucoo.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 *
 * 系统用户 服务实现类
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserMapper.queryAllMenuId(userId);
	}


	@Override
	public List<SysUser> listUser(SysUser sysUser) {
		Example example=new Example(SysUser.class);
	    if(null!=sysUser.getRealName())
			example.createCriteria().andEqualTo("realName",sysUser.getRealName());
		if(null!=sysUser.getUsername())
			example.createCriteria().andEqualTo("username",sysUser.getUsername());
		if(null!=sysUser.getMobile())
			example.createCriteria().andEqualTo("mobile",sysUser.getMobile());
		if(null!=sysUser.getEmail())
			example.createCriteria().andEqualTo("email",sysUser.getEmail());
		if(null!=sysUser.getDptId())
			example.createCriteria().andEqualTo("deptId",sysUser.getDptId());
		if(null!=sysUser.getIsShopManager())
			example.createCriteria().andEqualTo("isShopManager",sysUser.getIsShopManager());
		if(null!=sysUser.getStatus())
			example.createCriteria().andEqualTo("status",sysUser.getStatus());
		if(null!=sysUser.getIsDel())
			example.createCriteria().andEqualTo("isDel",sysUser.getIsDel());

		return sysUserMapper.selectByExample(example);
	}

}
