package com.emucoo.service.log;

import com.github.pagehelper.PageInfo;
import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.Log;

/**
 * @author fujg
 * Created by fujg on 2017/4/27.
 */
public interface LogService extends BaseService<Log> {

    /**
     * 分页查询日志列表
     * @param pageNum
     * @param pageSize
     * @param username
     * @param startTime
     * @param endTime
     * @return
     */
    PageInfo<Log> findPage(Integer pageNum, Integer pageSize, String username, String startTime, String endTime);
}
