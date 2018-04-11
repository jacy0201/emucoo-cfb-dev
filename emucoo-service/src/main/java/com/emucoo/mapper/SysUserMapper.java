package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysUser;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {

    /**
     * 根据用户的id取得对应的push token.
     * @param asList
     * @return
     */
    List<String> findPushTokenByIds(List<Integer> asList);

    List<SysUser> listUserVo(String username, String realName, String mobile, String email, List<Long> labels, Integer status);
}