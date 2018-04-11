package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.SysLog;
import com.github.pagehelper.PageInfo;

public interface SysLogService extends BaseService<SysLog> {
    /**
     * 分页查询日志列表
     * @param pageNum
     * @param pageSize
     * @param username
     * @param startTime
     * @param endTime
     * @return
     */
    PageInfo<SysLog> findPage(Integer pageNum, Integer pageSize, String username, String startTime, String endTime);

}
