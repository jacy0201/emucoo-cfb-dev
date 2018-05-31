package com.emucoo.service.calendar;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.calendar.WorkTargetDelVO;
import com.emucoo.dto.modules.calendar.WorkTargetQueryVO;
import com.emucoo.dto.modules.calendar.WorkTargetVO;
import com.emucoo.model.SysPost;
import com.emucoo.model.TWorkTarget;
import com.emucoo.service.sys.SysPostService;

/**
 * 工作目标
 * @author Jacy
 *
 */
public interface WorkTargetService extends BaseService<TWorkTarget> {

    /**
     * 查询用户工作目标
     * @param workTargetQueryVO
     * @return
     */
    WorkTargetVO getWorkTarget(WorkTargetQueryVO workTargetQueryVO);

    /**
     * 添加工作目标
     * @param workTargetVO
     * @param currentUserId
     * @return
     */
    void addWorkTarget(WorkTargetVO workTargetVO,Long currentUserId);



    /**
     * 编辑工作目标
     * @param workTargetVO
     * @param currentUserId
     */
    void editWorkTarget(WorkTargetVO workTargetVO,Long currentUserId);

    /**
     * 删除工作目标
     * @param workTargetDelVO
     * @param currentUserId
     */
    void deleteWorkTarget(WorkTargetDelVO workTargetDelVO);

}
