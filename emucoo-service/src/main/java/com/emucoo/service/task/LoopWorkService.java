package com.emucoo.service.task;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysUser;
import com.emucoo.model.TLoopWork;

import java.util.Date;
import java.util.List;

/**
 * @author river
 * @date 2018/3/14 14:18
 */
public interface LoopWorkService extends BaseService<TLoopWork> {

    /**
     * 当前用户当天任务数量
     *
     * @param submitUserId
     * @param today
     * @return
     */
    int fetchPendingExecuteWorkNum(Long submitUserId, Date today);

    /**
     * 当天用户当天待审核任务数量
     *
     * @param submitUserId
     * @param today
     * @return
     */
    int fetchPendingReviewWorkNum(Long submitUserId, Date today);

    /**
     * 当前用户当天任务列表
     * @param needDate
     * @param submitUserId
     * @return
     */
    WorkVo_O viewPendingExecuteWorks(Date needDate, Long submitUserId);

    /**
     * 当前用户当天待审核任务列表
     * @param auditUserId
     * @return
     */
    WorkVo_O viewPendingReviewWorks(Long auditUserId);

    /**
     * 提交任务执行结果
     *
     * @param voi
     */
    void submitAssignTask(AssignTaskSubmitVo_I voi);

    /**
     * 审核指派任务
     *
     * @param user
     * @param atai
     */
    void auditAssignTask(SysUser user, AssignTaskAuditVo_I atai);

    /**
     * 查看指派任务详情
     *
     * @param workId
     * @param subWorkId
     * @param workType
     * @param loginUser
     * @return
     */
    AssignTaskDetailVo_O viewAssignTaskDetail(String workId, String subWorkId, int workType, SysUser loginUser);

    /**
     * 工作备忘详情查询
     *
     * @param workId
     * @param subWorkId
     * @param workType
     * @param loginUser
     * @return
     */
    MemoDetailVo_O viewMemoDetail(String workId, String subWorkId, Integer workType, SysUser loginUser);


    /**
     * 创建指派任务
     *
     * @param assignTaskCreationVo_i
     * @param userId
     */
    void createAssignTask(AssignTaskCreationVo_I assignTaskCreationVo_i, long userId);

    /**
     * 创建工作备忘
     *
     * @param memoCreationVo_I
     * @param userId
     */
    void createMemo(MemoCreationVo_I  memoCreationVo_I, Long userId);

    /**
     * 修改工作备忘状态
     *
     * @param memoFinishVo_I
     * @param userId
     */
    void finishMemo(MemoFinishVo_I  memoFinishVo_I, Long userId);

    /**
     * 编辑工作备忘
     *
     * @param memoEditVo_I
     * @param userId
     */
    void editMemo(MemoEditVo_I  memoEditVo_I, Long userId);

    /**
     * 删除工作备忘
     *
     * @param memoDeleteVo_I
     * @param userId
     */
    void deleteMemo(MemoDeleteVo_I  memoDeleteVo_I, Long userId);

    /**
     * 查看任务历史
     * @param workType
     * @param workId
     * @return
     */
    ExeHistoryVo_O viewTaskExeHistory(String workType, String workId);

    /**
     * 查看指派任务历史
     *
     * @param workType
     * @param workId
     * @param subWorkId
     * @return
     */
    AssignTaskHistoryVo_O viewAssignTaskHistory(int workType, String workId, String subWorkId);

    /**
     * 定时任务标注过期任务，自动审核
     * @param dt
     */
    void dealWithExpiredWorks(Date dt);

    /**
     * 过滤出需要执行提醒的任务
     *
     * @param currentDate
     * @param cycleMinutes
     * @return
     */
    boolean notifyNeedExecuteRemindLoopWorks(Date currentDate, int cycleMinutes);

    /**
     * 过滤出需要审核提醒的任务
     *
     * @param currentDate
     * @param aheadMinutes
     * @param cycleMinutes
     * @return
     */
    List<TLoopWork> filterNeedAuditRemindWorks(Date currentDate, int aheadMinutes, int cycleMinutes);

    /**
     * 创建指派任务实例
     */
    void buildAssignTaskInstance();

}
