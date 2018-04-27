package com.emucoo.service.task;

import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysUser;

public interface TaskCommonService {

    TaskCommonDetailOut detail(TaskCommonDetailIn vo);

    void submitTask(TaskCommonSubmitIn vo, SysUser user);

    void auditTask(TaskCommonAuditIn vo, SysUser user);

    void editExcImgs(ExecuteImgIn vo, SysUser user);
}
