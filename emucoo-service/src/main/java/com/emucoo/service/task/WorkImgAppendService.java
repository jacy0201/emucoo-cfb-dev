package com.emucoo.service.task;

import java.util.List;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.task.ExecuteImgIn;
import com.emucoo.model.WorkImgAppend;

/**
 * 任务图片附录
 * 
 * @author zhangxq
 * @date 2018-03-18
 */
public interface WorkImgAppendService extends BaseService<WorkImgAppend> {

	void add(WorkImgAppend workImgAppend);
	
	void addList(List<WorkImgAppend> list);

    List<WorkImgAppend> fetchTaskImgs(String workId, String subWorkId);

    void addExcImgs(ExecuteImgIn executeImgIn);
}
