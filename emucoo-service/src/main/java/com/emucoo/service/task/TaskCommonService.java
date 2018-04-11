package com.emucoo.service.task;

import com.emucoo.dto.modules.task.TaskCommonAuditIn;
import com.emucoo.dto.modules.task.TaskCommonDetailIn;
import com.emucoo.dto.modules.task.TaskCommonDetailOut;
import com.emucoo.dto.modules.task.TaskCommonSubmitIn;
import com.emucoo.model.User;

/**
 * 常规任务
 * 
 * @author zhangxq
 * @date 2018-03-18
 */
public interface TaskCommonService {

	void submitTask(TaskCommonSubmitIn taskCommonSubmitIn, User user);
	
	void auditTask(TaskCommonAuditIn taskCommonAuditIn, User user);

	TaskCommonDetailOut detail(TaskCommonDetailIn taskCommonDetailIn);
}
