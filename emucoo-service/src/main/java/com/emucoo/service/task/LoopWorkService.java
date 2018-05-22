package com.emucoo.service.task;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysUser;
import com.emucoo.model.TLoopWork;
import com.emucoo.model.TTask;

import java.util.Date;
import java.util.List;

/**
 * @author river
 * @date 2018/3/14 14:18
 */
public interface LoopWorkService extends BaseService<TLoopWork> {

    List<TLoopWork> listPendingExecute(Long submitUserId, Date date);

    List<TLoopWork> listPendingReview(Long auditUserId);

    int fetchPendingExecuteWorkNum(Long submitUserId, Date today);

    int fetchPendingReviewWorkNum(Long submitUserId);

    void add(TLoopWork loopWork);

    void modify(TLoopWork loopWork);

    TLoopWork fetchByWorkId(String workId, String subWorkId, int workType);

    void submitAssignTask(AssignTaskSubmitVo_I voi);

    void auditAssignTask(SysUser user, AssignTaskAuditVo_I atai);

    AssignTaskDetailVo_O viewAssignTaskDetail(String workId, String subWorkId, int workType, SysUser loginUser);
    
    void createAssignTask(AssignTaskCreationVo_I assignTaskCreationVo_i, long userId);

    ExeHistoryVo_O viewTaskExeHistory(String workType, String workId);

    TTask fetchTaskById(long id);

    AssignTaskHistoryVo_O viewAssignTaskHistory(int workType, String workId, String subWorkId);

    void markExpiredWorks();

    List<TLoopWork> filterNeedExecuteRemindWorks(Date currentDate, int aheadMinutes, int cycleMinutes);
}
