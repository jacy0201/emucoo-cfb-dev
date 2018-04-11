package com.emucoo.service.task;

import com.emucoo.dto.modules.task.TaskImproveAuditIn;
import com.emucoo.dto.modules.task.TaskImproveDetailIn;
import com.emucoo.dto.modules.task.TaskImproveDetailOut;
import com.emucoo.dto.modules.task.TaskImproveSubmitIn;
import com.emucoo.dto.modules.task.TaskImproveVo;
import com.emucoo.model.User;

/**
 * 改善任务
 * 
 * @author zhangxq
 * @date 2018-03-20
 */
public interface TaskImproveService {

	void save(TaskImproveVo vo, User user);

	void submit(TaskImproveSubmitIn taskImproveSubmitIn, User user);

	void audit(TaskImproveAuditIn taskImproveAuditIn, User user);

	TaskImproveDetailOut detail(TaskImproveDetailIn taskImproveDetailIn);

}
