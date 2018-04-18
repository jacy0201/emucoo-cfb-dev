package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
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

    int countPendingExecuteWorkNum(@Param("submitUserId") Long submitUserId, @Param("today") Date today);

    int countPendingReviewWorkNum(@Param("auditUserId") Long auditUserId);

    List<TLoopWork> fetchTaskExeHistory(@Param("workType") String workType, @Param("workId") String workId);

    TaskImproveSubmit getTaskImproveSubmit(@Param("loopWorkId") Long loopWorkId);

    TaskImproveStatement getTaskImproveStatement(@Param("loopWorkId") Long loopWorkId);
}