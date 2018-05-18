package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.common.security.token.TokenUtils;
import com.emucoo.mapper.SysUserTokenMapper;
import com.emucoo.model.SysUserToken;
import com.emucoo.service.sys.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * 系统用户Token 服务实现类
 *
 */
@Service
public class SysUserTokenServiceImpl extends BaseServiceImpl<SysUserToken> implements SysUserTokenService {
	@Autowired
	private SysUserTokenMapper sysUserTokenMapper;
	// token 过期时间， 默认2小时后过期
	@Value("${token.expire}")
	private int expire;
	
	@Override
	public SysUserToken queryByToken(String token) {
		return sysUserTokenMapper.queryByToken(token);
	}
	
	@Override
	public SysUserToken getToken(long userId) {
		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + expire * 1000 * 3600);

		//判断是否生成过token
		SysUserToken tokenEntity = sysUserTokenMapper.selectByPrimaryKey(userId);
		if(tokenEntity == null){
			tokenEntity = new SysUserToken();
			tokenEntity.setId(userId);
			//创建token
			tokenEntity.setToken(TokenUtils.generateValue());
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			//保存token
			 sysUserTokenMapper.insertSelective(tokenEntity);
		}else{
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			//更新token有效期
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
