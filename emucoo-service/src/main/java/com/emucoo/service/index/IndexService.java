package com.emucoo.service.index;

import com.emucoo.dto.modules.index.ResetPwdVo_I;
import com.emucoo.dto.modules.user.UserLogin;
import com.emucoo.model.ReportItem;

import java.util.List;

public interface IndexService {
	
	public UserLogin getUserLogin(String mobile, String password);
	
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
	


	public List<ReportItem> fetchUnReadReports(long currUserId) ;

	Integer reportNum(Long userId, Boolean isRead);
}
