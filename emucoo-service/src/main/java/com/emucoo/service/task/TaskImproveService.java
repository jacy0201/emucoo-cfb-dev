package com.emucoo.service.task;

import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysUser;

/**
 * 改善任务
 *
 * @author zhangxq
 * @date 2018-03-20
 */
public interface TaskImproveService {

    /**
     * 创建改善任务
     *
     * @param vo
     * @param user
     */
    void createImproveTask(TaskImproveVo vo, SysUser user);

    /**
     * 执行改善任务
     * @param taskImproveSubmitIn
     * @param user
     */
    void submitImproveTask(TaskImproveSubmitIn taskImproveSubmitIn, SysUser user);

    /**
     * 审核改善任务
     * @param taskImproveAuditIn
     * @param user
     */
    void auditImproveTask(TaskImproveAuditIn taskImproveAuditIn, SysUser user);

    /**
     * 改善任务详情
     * @param taskImproveDetailIn
     * @return
     */
    TaskImproveDetailOut viewImproveTaskDetail(TaskImproveDetailIn taskImproveDetailIn);

    /**
     * 定时任务创建改善任务实例
     */
    void buildImproveTaskInstance();
}
