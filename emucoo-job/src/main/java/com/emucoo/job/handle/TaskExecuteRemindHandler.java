package com.emucoo.job.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emucoo.common.util.MsgPushing;
import com.emucoo.model.SysUser;
import com.emucoo.model.TLoopWork;
import com.emucoo.service.sys.UserService;
import com.emucoo.service.task.LoopWorkService;
import com.emucoo.utils.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@JobHandler(value = "任务执行提醒")
@Component
public class TaskExecuteRemindHandler extends IJobHandler {

    @Autowired
    private LoopWorkService loopWorkService;

    @Autowired
    private UserService userService;

    @Autowired
    private MsgPushing msgPushing;

    @Override
    public ReturnT<String> execute(String json) throws Exception {
        JSONObject jsonArgs = JSON.parseObject(json);
        int aheadMinutes = jsonArgs == null ? 30 : jsonArgs.getIntValue("aheadMinutes");
        aheadMinutes = aheadMinutes == 0 ? 30 : aheadMinutes;
        int cycleMinutes = jsonArgs == null ? 10 : jsonArgs.getIntValue("cycleMinutes");
        cycleMinutes = cycleMinutes == 0 ? 10 : cycleMinutes;
        Date currentDate = DateUtil.currentDate();
        // 过滤规则：
        // 如果有提醒时间，根据： 提醒时间 在 [当前任务执行时间，当前任务执行时间+任务调度周期] 区间来过滤，
        // 如果没有提醒时间，根据：执行截止时间 在 [本次任务执行时间+提醒提前的时间量），（本次任务执行时间 + 提醒提前的时间量 + 任务调度周期] 来过滤
        List<TLoopWork> exeWorks = loopWorkService.filterNeedExecuteRemindWorks(currentDate, aheadMinutes, cycleMinutes);
        List<String> pushTokens = new ArrayList<>();
        for (TLoopWork work : exeWorks) {
            SysUser user = userService.findById(work.getExcuteUserId());
            Optional.ofNullable(user).ifPresent(u -> pushTokens.add(u.getPushToken()));
        }
        Map<String, String> extra = new HashMap<>();
        msgPushing.pushMessage("待执行任务提醒", "您有待执行任务马上到截止时间了！请注意查看，如果已经执行，请忽略该提醒。", extra, pushTokens);
        return ReturnT.SUCCESS;
    }

}
