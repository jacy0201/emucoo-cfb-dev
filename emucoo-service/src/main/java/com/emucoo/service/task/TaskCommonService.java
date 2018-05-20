package com.emucoo.service.task;

import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysPost;
import com.emucoo.model.SysUser;

import java.util.List;

public interface TaskCommonService {

    TaskCommonDetailOut detail(TaskCommonDetailIn vo);

    void submitTask(TaskCommonSubmitIn vo, SysUser user);

    void auditTask(TaskCommonAuditIn vo, SysUser user);

    void editExcImgs(ExecuteImgIn vo, SysUser user);

    int countCommonTaskByName(String keyword);

    List<TaskParameterVo> listCommonTaskByName(String keyword, int pageNm, int pageSz);

    void createCommonTask(TaskParameterVo data);

    boolean judgeExistCommonTask(TaskParameterVo data);

    void saveCommonTask(TaskParameterVo data);

    void removeCommonTask(List<Long> ids);

    void switchCommonTask(List<Long> data, boolean b);

    TaskParameterVo detailCommonTask(Long taskId);

    void configCommonTask(TaskParameterVo data);

    void buildCommonTaskInstance();

    List<SysPost> listPositionsOfDept(Long deptId);
}
