package com.emucoo.service.center;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.center.FrontPlanVO;
import com.emucoo.dto.modules.center.RepairWorkVO;
import com.emucoo.dto.modules.center.ReportVO;
import com.emucoo.dto.modules.center.TaskVO;
import com.emucoo.model.TLoopWork;

import java.util.List;

public interface UserCenterService extends BaseService<TLoopWork> {

    //我执行的任务
    List<TaskVO> executeTaskList(String month,Long userId);

    //我审核的任务
    List<TaskVO> auditTaskList(String month,Long userId);

    //我创建的任务
    List<TaskVO> createTaskList(String month,Long userId);

    //巡店任务
    List<FrontPlanVO> frontPlanList(String month, Long userId);

    //维修任务
    List<RepairWorkVO> repairWorkList(String month, Long userId);

    //我接收的报告
    List<ReportVO> getReportList(String month, Long userId);

}
