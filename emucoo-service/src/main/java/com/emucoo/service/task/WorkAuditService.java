package com.emucoo.service.task;

import java.util.List;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.WorkAudit;

/**
 * 常规任务审核
 * 
 * @author zhangxq
 * @date 2018-03-19
 */
public interface WorkAuditService extends BaseService<WorkAudit> {

	void add(WorkAudit workAudit);

	void addList(List<WorkAudit> list);

}
