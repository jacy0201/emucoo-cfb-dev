package com.emucoo.job.handle;

import com.emucoo.service.task.LoopWorkService;
import com.emucoo.service.task.TaskRepairService;
import com.emucoo.utils.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@JobHandler("ExpiredTaskHandler")
@Component
public class TaskExpiredHandler extends IJobHandler {

    @Autowired
    private LoopWorkService loopWorkService;

    @Autowired
    private TaskRepairService taskRepairService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        Date dt = DateUtil.currentDate();
        loopWorkService.dealWithExpiredWorks(dt);
        taskRepairService.dealWithExpiredWorks(dt);
        return ReturnT.SUCCESS;
    }
}
