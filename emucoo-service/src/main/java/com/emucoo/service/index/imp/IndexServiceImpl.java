package com.emucoo.service.index.imp;

import javax.annotation.Resource;

import com.emucoo.mapper.UserOfReportMapper;
import com.emucoo.model.ReportItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.index.ResetPwdVo_I;
import com.emucoo.dto.modules.user.UserLogin;
import com.emucoo.mapper.UserMapper;
import com.emucoo.model.User;
import com.emucoo.service.index.IndexService;

import java.util.List;

@Transactional
@Service
public class IndexServiceImpl extends BaseServiceImpl<User> implements IndexService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserOfReportMapper userOfReportMapper;

	@Override
	public UserLogin getUserLogin(String mobile, String password) {
		return userMapper.getUserLogin(mobile, password);
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
	public List<ReportItem> fetchUnReadReports(long currUserId) {
		return userOfReportMapper.fetchUnReadReport(currUserId);
	}

	@Override
	public Integer reportNum(Long userId, Boolean isRead) {
		return userOfReportMapper.reportNum(userId, isRead);
	}
}
