package com.emucoo.job.handle;

import com.emucoo.service.task.LoopWorkService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;

@JobHandler("指派任务根据任务定义创建每天执行的任务实例")
public class AssignTaskBuildHandler extends IJobHandler {

    @Autowired
    private LoopWorkService loopWorkService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        loopWorkService.buildAssingTaskInstance();
        return ReturnT.SUCCESS;
    }
}
