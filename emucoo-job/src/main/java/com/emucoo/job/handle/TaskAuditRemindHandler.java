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

@JobHandler("TaskAuditReminder")
@Component
public class TaskAuditRemindHandler extends IJobHandler {

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
        // 如果没有提醒时间，根据：审核截止时间 在 [本次任务执行时间+提醒提前的时间量），（本次任务执行时间 + 提醒提前的时间量 + 任务调度周期] 来过滤
        List<TLoopWork> auditWorks = loopWorkService.filterNeedAuditRemindWorks(currentDate, aheadMinutes, cycleMinutes);
        List<String> pushTokens = new ArrayList<>();
        for (TLoopWork work : auditWorks) {
            SysUser user = userService.findById(work.getExcuteUserId());
            Optional.ofNullable(user).ifPresent(u -> pushTokens.add(u.getPushToken()));
        }
        Map<String, String> extra = new HashMap<>();
        msgPushing.pushMessage("待审核任务提醒", "您有待审核任务需要处理！请注意查看，如果已经完成，请忽略该提醒。", extra, pushTokens);

        return ReturnT.SUCCESS;
    }
}
