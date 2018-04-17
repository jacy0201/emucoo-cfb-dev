package com.emucoo.service.task;

import com.emucoo.dto.modules.task.TaskCommonAuditIn;
import com.emucoo.dto.modules.task.TaskCommonDetailIn;
import com.emucoo.dto.modules.task.TaskCommonDetailOut;
import com.emucoo.dto.modules.task.TaskCommonSubmitIn;
import com.emucoo.model.SysUser;

public interface TaskCommonService {

    TaskCommonDetailOut detail(TaskCommonDetailIn vo);

    void submitTask(TaskCommonSubmitIn vo, SysUser user);

    void auditTask(TaskCommonAuditIn vo, SysUser user);
}
