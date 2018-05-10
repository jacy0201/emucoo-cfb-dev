package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.task.ContactsVo_I;
import com.emucoo.dto.modules.task.ContactsVo_O;
import com.emucoo.model.SysUser;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by fujg on 2017/1/19.
 */
public interface UserService extends BaseService<SysUser> {
    /**
     *
     * @param pageNum  当前页码
     * @param pageSize  每页显示条数
     * @param username 用户名
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @throws Exception
     */
    PageInfo<SysUser> findPage(Integer pageNum, Integer pageSize, String username, String startTime, String endTime) throws Exception;


    /**
     * 保存用户信息和关联用户和角色
     * @param user    用户对象
     * @param roleId  角色ID
     */
    Boolean saveUserAndUserRole(SysUser user, Long roleId) throws Exception;

    /**
     * 更新用户信息和关联用户和角色
     * @param user      用户对象
     * @param oldRoleId 旧角色ID
     * @param roleId    角色ID
     * @return
     * @throws Exception
     */
    Boolean updateUserAndUserRole(SysUser user, Long oldRoleId, Long roleId) throws Exception;

    int updateLabels(Long id, String labels);

    Integer updateByExampleSelective(SysUser record, Example example);

    SysUser findUserByMobile(String mobile);

    ContactsVo_O fetchContacts(ContactsVo_I contactsVo_i);
}
