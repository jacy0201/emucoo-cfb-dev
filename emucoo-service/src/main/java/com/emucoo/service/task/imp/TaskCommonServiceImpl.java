package com.emucoo.service.task.imp;

import com.emucoo.dto.modules.task.TaskCommonAuditIn;
import com.emucoo.dto.modules.task.TaskCommonDetailIn;
import com.emucoo.dto.modules.task.TaskCommonDetailOut;
import com.emucoo.dto.modules.task.TaskCommonSubmitIn;
import com.emucoo.model.SysUser;
import com.emucoo.service.task.TaskCommonService;
import org.springframework.stereotype.Service;

@Service
public class TaskCommonServiceImpl implements TaskCommonService {
    @Override
    public TaskCommonDetailOut detail(TaskCommonDetailIn vo) {
        return null;
    }

    @Override
    public void submitTask(TaskCommonSubmitIn vo, SysUser user) {

    }

    @Override
    public void auditTask(TaskCommonAuditIn vo, SysUser user) {

    }
}
