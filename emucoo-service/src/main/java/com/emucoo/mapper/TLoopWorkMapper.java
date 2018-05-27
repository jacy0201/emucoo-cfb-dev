package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.task.TaskCommonItemVo;
import com.emucoo.dto.modules.task.TaskCommonStatement;
import com.emucoo.dto.modules.task.TaskImproveStatement;
import com.emucoo.dto.modules.task.TaskImproveSubmit;
import com.emucoo.model.TLoopWork;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TLoopWorkMapper extends MyMapper<TLoopWork> {

    TLoopWork fetchOneTaskByWorkIds(@Param("workId") String workId, @Param("subWorkId") String subWorkId);

    void updateWorkStatus(TLoopWork lw);

    void auditLoopWork(TLoopWork loopWork);

    TLoopWork fetchByWorkIdAndType(@Param("workId") String workId, @Param("subWorkId") String subWorkId, @Param("workType") int workType);

    List<TLoopWork> listPendingExecute(@Param("executeUserId") Long executeUserId, @Param("date") Date date);

    List<TLoopWork> listPendingReview(@Param("auditUserId") Long auditUserId, @Param("ldt") Date ldt, @Param("rdt") Date rdt);

    List<TLoopWork> calendarMonthList(@Param("userId") Long userId,@Param("yearStr") String yearStr,@Param("monthStr")  String monthStr);

    List<TLoopWork> calendarDateList(@Param("userId") Long userId,@Param("executeDate") String executeDate,@Param("workType") Integer workType);

    int countPendingExecuteWorkNum(@Param("submitUserId") Long submitUserId, @Param("today") Date today);

    int countPendingReviewWorkNum(@Param("auditUserId") Long auditUserId);

    List<TLoopWork> fetchTaskExeHistory(@Param("workType") String workType, @Param("workId") String workId);

    TaskImproveSubmit getTaskImproveSubmit(@Param("loopWorkId") Long loopWorkId);

    TaskImproveStatement getTaskImproveStatement(@Param("loopWorkId") Long loopWorkId);

    List<TLoopWork> findImproveTaskList(@Param("opptId")Long opptId, @Param("reportId")Long reportId);

    List<TLoopWork> findImproveTaskListByUser(@Param("opptId") Long opptId, @Param("reportId") Long reportId, @Param("excuteUserId")Long excuteUserId);

    void markExpiredAuditWorks(Date dt);

    void markExpiredExecutionWorks(Date dt);

    TaskCommonStatement fetchCommonTaskStatement(Long loopWorkId);

    List<TaskCommonItemVo> fetchTaskCommonItem(Long loopWorkId);

    List<TLoopWork> filterExecuteRemindWorks(@Param("deadTimeLeft") Date exeDeadTimeLeft, @Param("deadTimeRight") Date exeDeadTimeRight, @Param("remindTimeLeft") Date exeRemindTimeLeft, @Param("remindTimeRight") Date exeRemindTimeRight);

    List<TLoopWork> filterAuditRemindWorks(@Param("deadTimeLeft") Date deadTimeLeft, @Param("deadTimeRight") Date deadTimeRight, @Param("remindTimeLeft") Date remindTimeLeft, @Param("remindTimeRight") Date remindTimeRight);

    int isLoopWorkExist(@Param("taskId") Long taskId, @Param("dt") Date dt, @Param("executorId") Long executorId);
}