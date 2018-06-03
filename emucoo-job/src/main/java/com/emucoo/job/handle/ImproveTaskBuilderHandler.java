package com.emucoo.job.handle;

import com.emucoo.service.task.TaskImproveService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value = "ImproveTaskInstanceCreator")
@Component
public class ImproveTaskBuilderHandler extends IJobHandler {

    @Autowired
    private TaskImproveService taskImproveService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        taskImproveService.buildImproveTaskInstance();
        return ReturnT.SUCCESS;
    }
}
