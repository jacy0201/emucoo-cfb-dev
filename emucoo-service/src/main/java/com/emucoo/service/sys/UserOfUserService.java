package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.user.UserVo_Level;
import com.emucoo.model.UserOfUser;

import java.util.List;

public interface UserOfUserService extends BaseService<UserOfUser> {
    int deleteByExample(Object example);

    int insertList(List<UserOfUser> recordList);

    void updateUserLevle(Long userId, List<UserVo_Level> userVoLevelList);
}
