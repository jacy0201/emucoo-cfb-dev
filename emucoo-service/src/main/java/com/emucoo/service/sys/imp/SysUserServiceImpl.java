package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysUserMapper;
import com.emucoo.model.SysUser;
import com.emucoo.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
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
	public List<SysUser> listByPostId(HashMap map) {

		return sysUserMapper.listByPostId(map);

	}

	@Override
	public List<SysUser> listByShopId(HashMap map) {

		return sysUserMapper.listByShopId(map);

	}

}
