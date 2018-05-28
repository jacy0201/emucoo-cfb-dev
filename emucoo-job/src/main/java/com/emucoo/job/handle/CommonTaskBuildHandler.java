package com.emucoo.job.handle;

import com.emucoo.service.task.TaskCommonService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value = "CommonTaskInstanceCreator")
@Component
public class CommonTaskBuildHandler extends IJobHandler {

    @Autowired
    private TaskCommonService taskCommonService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        taskCommonService.buildCommonTaskInstance();
        return ReturnT.SUCCESS;
    }
}
