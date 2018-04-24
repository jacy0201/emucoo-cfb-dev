package com.emucoo.service.index.imp;

import com.alibaba.druid.util.StringUtils;
import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.common.util.RegexMatcher;
import com.emucoo.dto.modules.index.ReportItemVo;
import com.emucoo.dto.modules.index.ResetPwdVo_I;
import com.emucoo.dto.modules.user.UserLoginInfo;
import com.emucoo.mapper.SysDeptMapper;
import com.emucoo.mapper.SysUserMapper;
import com.emucoo.mapper.TReportMapper;
import com.emucoo.mapper.TShopInfoMapper;
import com.emucoo.model.SysDept;
import com.emucoo.model.SysUser;
import com.emucoo.model.TShopInfo;
import com.emucoo.service.index.IndexService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class IndexServiceImpl extends BaseServiceImpl<SysUser> implements IndexService {

	@Autowired
	private SysUserMapper userMapper;

	@Autowired
	private SysDeptMapper sysDeptMapper;

	@Autowired
	private TShopInfoMapper shopInfoMapper;

	@Autowired
	private TReportMapper reportMapper;


	@Override
	public UserLoginInfo authenticate(String mobile, String password, String pushToken) {

		SysUser user = null;
		if (RegexMatcher.isPhoneNumber(mobile)) {
			SysUser u = new SysUser();
			u.setMobile(mobile);
			user = userMapper.selectOne(u);
		} else if (RegexMatcher.isEmail(mobile)) {
			SysUser u = new SysUser();
			u.setEmail(mobile);
			user = userMapper.selectOne(u);
		} else {
		    SysUser u = new SysUser();
		    u.setUsername(mobile);
			user = userMapper.selectOne(u);
		}

		if(user == null)
			return null;

		if(!StringUtils.equalsIgnoreCase(user.getPassword(), new Sha256Hash(password).toHex()))
			return null;

		//更新用户 push token
		user.setPushToken(pushToken);
		//如果用户没传token 需要设置为null
		userMapper.updateByPrimaryKey(user);

		UserLoginInfo loginInfo = new UserLoginInfo();

		SysDept d = new SysDept();
		d.setId(user.getDptId());
		SysDept dept = sysDeptMapper.selectByPrimaryKey(d);
		if (dept != null) {
			loginInfo.setUserType("");
			loginInfo.setDepartmentID(dept.getId());
			loginInfo.setDepartmentName(dept.getDptName());
			loginInfo.setDepartmentType(2);
		}

		List<TShopInfo> shopInfos = shopInfoMapper.fetchShopsOfUser(user.getId());
		List<UserLoginInfo.shop> shoptList = shopInfos.stream().map(shopInfo -> {
			UserLoginInfo.shop shop = new UserLoginInfo.shop();
			shop.setShopName(shopInfo.getShopName());
			shop.setShopID(shopInfo.getId());
			return shop;
		}).collect(Collectors.toList());

		loginInfo.setShopNameArr(shoptList);
		loginInfo.setHeadImgUrl(user.getHeadImgUrl());
		loginInfo.setUserName(user.getRealName());
		loginInfo.setUserId(user.getId());

		return loginInfo;
	}

	@Override
	public void resetPwd(ResetPwdVo_I vo, String vcode) {
		// 短信验证
		if (vo.getSmsCode().equals(vcode)) {
			userMapper.resetPwd(vo.getMobile(), vo.getPassword().toUpperCase());
		}
	}

	@Override
	public void loginOut(String pushToken) {
		if (pushToken != null && !"".equals(pushToken)) {
			userMapper.loginOut(pushToken);
		}
	}

	@Override
	public List<ReportItemVo> fetchUnReadReports(long currUserId) {
		return reportMapper.fetchUnReadReport(currUserId);
	}

	@Override
	public Integer reportNum(Long userId, Boolean isRead) {
		return reportMapper.countUnReadReport(userId);
	}
}
