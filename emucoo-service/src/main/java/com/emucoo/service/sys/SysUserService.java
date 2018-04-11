package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.SysUser;
import com.github.pagehelper.PageInfo;

/**
 * Created by fujg on 2017/1/19.
 */
public interface SysUserService extends BaseService<SysUser> {
    /**
     * @param pageNum  当前页码
     * @param pageSize  每页显示条数
     * @param username 用户名
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @throws Exception
     */
    PageInfo<SysUser> findPage(Integer pageNum ,Integer pageSize ,String username, String startTime, String endTime) throws Exception;

}
