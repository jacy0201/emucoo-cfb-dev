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

    int fetchPendingExecuteWorkNum(Long submitUserId, Date today);

    int fetchPendingReviewWorkNum(Long submitUserId);

    void add(TLoopWork loopWork);

    void modify(TLoopWork loopWork);

    TLoopWork fetchByWorkId(String workId, String subWorkId, int workType);

    void submitAssignTask(AssignTaskSubmitVo_I voi);

    void auditAssignTask(SysUser user, AssignTaskAuditVo_I atai);

    AssignTaskDetailVo_O viewAssignTaskDetail(String workId, String subWorkId, int workType, SysUser loginUser);

    /**
     * 工作备忘详情查询
     * @param workId
     * @param subWorkId
     * @param workType
     * @param loginUser
     * @return
     */
    MemoDetailVo_O viewMemoDetail(String workId, String subWorkId, Integer workType, SysUser loginUser);


    void createAssignTask(AssignTaskCreationVo_I assignTaskCreationVo_i, long userId);

    /**
     * 创建工作备忘
     * @param memoCreationVo_I
     * @param userId
     */
    void createMemo(MemoCreationVo_I  memoCreationVo_I, Long userId);

    /**
     * 修改工作备忘状态
     * @param memoFinishVo_I
     * @param userId
     */
    void finishMemo(MemoFinishVo_I  memoFinishVo_I, Long userId);

    /**
     * 编辑工作备忘
     * @param memoEditVo_I
     * @param userId
     */
    void editMemo(MemoEditVo_I  memoEditVo_I, Long userId);

    /**
     * 删除工作备忘
     * @param memoDeleteVo_I
     * @param userId
     */
    void deleteMemo(MemoDeleteVo_I  memoDeleteVo_I, Long userId);


    ExeHistoryVo_O viewTaskExeHistory(String workType, String workId);

    TTask fetchTaskById(long id);

    AssignTaskHistoryVo_O viewAssignTaskHistory(int workType, String workId, String subWorkId);

    void markExpiredWorks();

    List<TLoopWork> filterNeedExecuteRemindWorks(Date currentDate, int aheadMinutes, int cycleMinutes);

    List<TLoopWork> filterNeedAuditRemindWorks(Date currentDate, int aheadMinutes, int cycleMinutes);

    void buildAssingTaskInstance();

    WorkVo_O viewPendingExecuteWorks(Date needDate, Long submitUserId);

    WorkVo_O viewPendingReviewWorks(Long auditUserId);
}
