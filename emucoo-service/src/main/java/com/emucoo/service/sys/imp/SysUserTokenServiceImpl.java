package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.common.util.R;
import com.emucoo.common.util.TokenGenerator;
import com.emucoo.mapper.SysUserTokenMapper;
import com.emucoo.model.SysUserToken;
import com.emucoo.service.sys.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 系统用户Token 服务实现类
 *
 */
@Service
public class SysUserTokenServiceImpl extends BaseServiceImpl<SysUserToken> implements SysUserTokenService {
	@Autowired
	private SysUserTokenMapper sysUserTokenMapper;
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;
	
	@Override
	public SysUserToken queryByToken(String token) {
		return sysUserTokenMapper.queryByToken(token);
	}
	
	@Override
	public SysUserToken getToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();
		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		SysUserToken tokenEntity = sysUserTokenMapper.selectByPrimaryKey(userId);
		if(tokenEntity == null){
			tokenEntity = new SysUserToken();
			tokenEntity.setId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			//保存token
			 sysUserTokenMapper.insertSelective(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			//更新token
			sysUserTokenMapper.updateByPrimaryKeySelective(tokenEntity);
		}
		return tokenEntity;
	}

	@Override
	public void expireToken(long id){
		Date now = new Date();
		SysUserToken tokenEntity = new SysUserToken();
		tokenEntity.setId(id);
		tokenEntity.setUpdateTime(now);
		tokenEntity.setExpireTime(now);
		sysUserTokenMapper.updateByPrimaryKeySelective(tokenEntity);
	}


}
