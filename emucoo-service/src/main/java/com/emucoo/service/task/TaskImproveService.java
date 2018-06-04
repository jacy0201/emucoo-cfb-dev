package com.emucoo.service.task;

import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysUser;

/**
 * 改善任务
 * 
 * @author zhangxq
 * @date 2018-03-20
 */
public interface TaskImproveService {

	void createImproveTask(TaskImproveVo vo, SysUser user);

	void submitImproveTask(TaskImproveSubmitIn taskImproveSubmitIn, SysUser user);

	void auditImproveTask(TaskImproveAuditIn taskImproveAuditIn, SysUser user);

	TaskImproveDetailOut viewImproveTaskDetail(TaskImproveDetailIn taskImproveDetailIn);

    void buildImproveTaskInstance();
}
