package com.emucoo.service.index;

import com.emucoo.dto.modules.index.ReportItemVo;
import com.emucoo.dto.modules.index.ResetPwdVo_I;
import com.emucoo.dto.modules.task.WorkVo_O;
import com.emucoo.dto.modules.user.UserLoginInfo;
import com.emucoo.model.TRepairWork;

import java.util.Date;
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

    List<TRepairWork> fetchPendingRepairWorks(Long loginUserId);

	WorkVo_O filterPendingReviewWorks(Long auditUserId);

	int countPendingReviewWorks(Long loginUserId);

	WorkVo_O filterPendingExeWorks(Date needDate, Long submitUserId);

	int fetchPendingExeWorkNum(Long loginUserId);

	int countFrontPlan(Long loginUserId);
}
