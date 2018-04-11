package com.emucoo.service.task;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.task.AssignTaskCreationVo_I;
import com.emucoo.model.DataAppend;
import com.emucoo.model.ImgAppend;
import com.emucoo.model.TaskTemplate;

import java.util.List;

/**
 * @author LXC
 * @date 2017/5/10
 */
public interface TaskTemplateService extends BaseService<TaskTemplate> {

    List<ImgAppend> findImgsByTempletId(long task_templet_id);

    DataAppend fetchDataAppend(Long id);
}
