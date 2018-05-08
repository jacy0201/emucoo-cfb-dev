package com.emucoo.job.handle;

import com.emucoo.service.task.LoopWorkService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler("TaskExpiredHandler")
@Component
public class TaskExpiredHandler extends IJobHandler {

    @Autowired
    private LoopWorkService loopWorkService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        loopWorkService.markExpiredWorks();
        return ReturnT.SUCCESS;
    }
}
