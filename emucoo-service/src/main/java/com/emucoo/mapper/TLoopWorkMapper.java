package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.task.TaskImproveStatement;
import com.emucoo.dto.modules.task.TaskImproveSubmit;
import com.emucoo.model.TLoopWork;

import java.util.Date;
import java.util.List;

public interface TLoopWorkMapper extends MyMapper<TLoopWork> {
    TLoopWork fetchOneTaskByWorkIds(String workId, String subWorkId);

    void updateWorkStatus(TLoopWork lw);

    void auditLoopWork(TLoopWork loopWork);

    TLoopWork fetchByWorkIdAndType(String workId, String subWorkId, int workType);

    List<TLoopWork> listPendingExecute(Long executeUserId, Date date);

    List<TLoopWork> listPendingReview(Long auditUserId, Date ldt, Date rdt);

    int countPendingExecuteWorkNum(Long submitUserId, Date today);

    int countPendingReviewWorkNum(Long submitUserId);

    List<TLoopWork> fetchTaskExeHistory(String workType, String workId);

    TaskImproveSubmit getTaskImproveSubmit(Long loopWorkId);

    TaskImproveStatement getTaskImproveStatement(Long loopWorkId);
}