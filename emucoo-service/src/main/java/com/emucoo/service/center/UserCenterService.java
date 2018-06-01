package com.emucoo.service.center;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.center.TaskVO;
import com.emucoo.model.TFrontPlan;
import com.emucoo.model.TLoopWork;

import java.util.List;

public interface UserCenterService extends BaseService<TLoopWork> {

    //我执行的任务
    List<TaskVO> executeTaskList(Long userId);

    //我审核的任务
    List<TaskVO> auditTaskList(Long userId);

    //我创建的任务
    List<TaskVO> createTaskList(Long userId);

    //我接收的报告
    List<TaskVO> getReportList(Long userId);

    //巡店任务
    List<TFrontPlan> frontPlanList(Long userId);

    //维修任务



}
