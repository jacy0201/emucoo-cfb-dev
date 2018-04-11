package com.emucoo.service.task;

import java.util.List;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.WorkDataAppend;

/**
 * 任务数字项附录
 * 
 * @author zhangxq
 * @date 2018-03-18
 */
public interface WorkDataAppendService extends BaseService<WorkDataAppend> {

	void add(WorkDataAppend workDataAppend);

	void addList(List<WorkDataAppend> list);

    WorkDataAppend fetchWorkDataAppend(String workid, String subWorkid);
}
