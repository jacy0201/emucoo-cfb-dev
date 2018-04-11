package com.emucoo.service.task;

import com.emucoo.dto.modules.task.TaskCommonAuditIn;
import com.emucoo.dto.modules.task.TaskCommonDetailIn;
import com.emucoo.dto.modules.task.TaskCommonDetailOut;
import com.emucoo.dto.modules.task.TaskCommonSubmitIn;
import com.emucoo.model.SysUser;


/**
 * 常规任务
 * 
 * @author zhangxq
 * @date 2018-03-18
 */
public interface TaskCommonService {

	void submitTask(TaskCommonSubmitIn taskCommonSubmitIn, SysUser user);
	
	void auditTask(TaskCommonAuditIn taskCommonAuditIn, SysUser user);

	TaskCommonDetailOut detail(TaskCommonDetailIn taskCommonDetailIn);
}
