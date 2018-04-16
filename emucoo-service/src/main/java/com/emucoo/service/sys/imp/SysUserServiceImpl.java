package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.user.UserQuery;
import com.emucoo.dto.modules.user.UserVo;
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
	public SysUser queryByUserName(String username) {
		Example example=new Example(SysUser.class);
		example.createCriteria().andEqualTo("username",username);
		return sysUserMapper.selectByExample(example).get(0);

	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserMapper.queryAllMenuId(userId);
	}


	@Override
	public List<UserVo> listUser(String realName,String username,String mobile,String email,String dptId,String shopId,String postId,Integer status) {
		return sysUserMapper.listUser( realName, username, mobile, email, dptId, shopId, postId, status);
	}

}
