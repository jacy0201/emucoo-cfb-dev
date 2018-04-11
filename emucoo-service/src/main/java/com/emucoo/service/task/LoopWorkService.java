package com.emucoo.service.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.*;
import com.emucoo.model.*;
import org.apache.commons.lang3.StringUtils;

/**
 * @author river
 * @date 2018/3/14 14:18
 */
public interface LoopWorkService extends BaseService<TLoopWork> {
    List<TLoopWork> list(Long submitUserId , Date date);

    List<TLoopWork> listPendingReview(Long auditUserId);
    int fetchPendingExecuteWorkNum(Long submitUserId, Date today);
    int fetchPendingReviewWorkNum(Long submitUserId);

    void add(TLoopWork loopWork);

    void modify(TLoopWork loopWork);

    TLoopWork fetchByWorkId(String workId, String subWorkId, int workType);
    List<WorkImgAppend> fetchSubmitImgs(String workId, String subWorkId);

    WorkAudit fetchAssignWorkAudit(String workId, String subWorkId);

    List<TTaskComment> fetchAssignWorkAnswers(String workId, String subWorkId);

    List<WorkDataAppend> fetchAssignTaskHistory(String workId, String subWorkId, int workType);

    void submitAssignTask(AssignTaskSubmitVo_I voi);
    void auditAssignTask(SysUser user, AssignTaskAuditVo_I atai);
    
    TaskImproveSubmit getTaskImproveSubmit(String workId, String subWorkId);
    
    TaskImproveStatement getTaskImproveStatement(String workId, String subWorkId);

    TaskType fetchTaskType(long id);

    void createAssignTask(AssignTaskCreationVo_I assignTaskCreationVo_i);

    List<TLoopWork> fetchExeHistory(String workType, String workId);

    TTask fetchTaskById(long id);
}
