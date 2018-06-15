package com.emucoo.job.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emucoo.service.sys.UserService;
import com.emucoo.service.task.LoopWorkService;
import com.emucoo.utils.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@JobHandler(value = "TaskExecutionReminder")
@Component
public class TaskExecuteRemindHandler extends IJobHandler {

    @Autowired
    private LoopWorkService loopWorkService;

    @Autowired
    private UserService userService;

    @Override
    public ReturnT<String> execute(String json) throws Exception {
        JSONObject jsonArgs = JSON.parseObject(json);
        int cycleMinutes = jsonArgs == null ? 5 : jsonArgs.getIntValue("cycleMinutes");
        cycleMinutes = cycleMinutes == 0 ? 5 : cycleMinutes;
        Date currentDate = DateUtil.currentDate();
        loopWorkService.notifyNeedExecuteRemindLoopWorks(currentDate, cycleMinutes);
        return ReturnT.SUCCESS;
    }

}
