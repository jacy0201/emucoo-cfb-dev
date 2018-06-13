package com.emucoo.service.task;

import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysPost;
import com.emucoo.model.SysUser;

import java.util.List;

/**
 * @author Shayne.Wang
 * @date 2018-06-12
 *
 */

public interface TaskCommonService {

    /**
     * App获取常规任务详情
     * @param vo
     * @return
     */
    TaskCommonDetailOut detail(TaskCommonDetailIn vo);

    /**
     * 执行常规任务
     * @param vo
     * @param user
     */
    void submitTask(TaskCommonSubmitIn vo, SysUser user);

    /**
     * 审核常规任务
     * @param vo
     * @param user
     */
    void auditTask(TaskCommonAuditIn vo, SysUser user);

    /**
     * 涂鸦
     * @param vo
     * @param user
     */
    void editExcImgs(ExecuteImgIn vo, SysUser user);

    /**
     * 查询常规任务
     * @param keyword
     * @param usage
     * @return
     */
    int countCommonTaskByName(String keyword, Boolean usage);

    /**
     * 查询常规任务列表
     * @param keyword
     * @param usage
     * @param pageNm
     * @param pageSz
     * @return
     */
    List<TaskParameterVo> listCommonTaskByName(String keyword, Boolean usage, int pageNm, int pageSz);

    /**
     * 中台创建常规任务
     * @param data
     */
    void createCommonTask(TaskParameterVo data);

    /**
     * 中台判断同名常规任务
     * @param data
     * @return
     */
    boolean judgeExistCommonTask(TaskParameterVo data);

    /**
     * 中台保存常规任务
     * @param data
     */
    void saveCommonTask(TaskParameterVo data);

    /**
     * 中台删除常规任务
     * @param ids
     */
    void removeCommonTask(List<Long> ids);

    /**
     * 启用/停用常规任务
     * @param data
     * @param b
     */
    void switchCommonTask(List<Long> data, boolean b);

    /**
     * 中台查看常规任务详情
     * @param taskId
     * @return
     */
    TaskParameterVo detailCommonTask(Long taskId);

    /**
     * 中台配置常规任务详情
     * @param data
     */
    void configCommonTask(TaskParameterVo data);

    /**
     * 定时任务创建常规任务
     */
    void buildCommonTaskInstance();

    /**
     * 部门职位列表
     * @param deptId
     * @return
     */
    List<SysPost> listPositionsOfDept(Long deptId);
}
