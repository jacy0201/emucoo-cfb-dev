package com.emucoo.service.index;

import com.emucoo.dto.modules.index.ReportItemVo;
import com.emucoo.dto.modules.index.ResetPwdVo_I;
import com.emucoo.dto.modules.user.UserLogin;
import com.emucoo.dto.modules.user.UserLoginInfo;
import com.emucoo.model.TReport;

import java.util.List;

public interface IndexService {
	

	UserLoginInfo authenticate(String mobile, String password, String pushToken);
	
	/**
	 * 密码重置
	 * @param vo
	 */
	void resetPwd(ResetPwdVo_I vo, String vcode);
	
	/**
	 * 用户退出
	 * @param pushToken
	 */
	void loginOut(String pushToken);
	


	public List<ReportItemVo> fetchUnReadReports(long currUserId) ;

	Integer reportNum(Long userId, Boolean isRead);
}
