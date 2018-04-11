package com.emucoo.service.sys.imp;

import com.google.common.collect.Lists;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.user.UserVo_Level;
import com.emucoo.mapper.UserOfUserMapper;
import com.emucoo.model.UserOfUser;
import com.emucoo.service.sys.UserOfUserService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserOfUserServiceImpl extends BaseServiceImpl<UserOfUser> implements UserOfUserService {
    @Resource
    private UserOfUserMapper userOfUserMapper;

    @Override
    public int deleteByExample(Object example){
        return userOfUserMapper.deleteByExample(example);
    }
    @Override
    public int insertList(List<UserOfUser> recordList){
        return userOfUserMapper.insertList(recordList);
    }

    @Override
    public void updateUserLevle(Long userId , List<UserVo_Level> userVoLevelList){
        Example delExam = new Example(UserOfUser.class);
        delExam.createCriteria().andEqualTo("uParentId",userId);
        userOfUserMapper.deleteByExample(delExam);
        List userOfUserList = Lists.newArrayList();
        userVoLevelList.forEach(userVo_level -> {
            if(CollectionUtils.isEmpty(userVo_level.getUserList())){
                return;
            }
            userVo_level.getUserList().forEach(t->{
                UserOfUser userOfUser = new UserOfUser();
                userOfUser.setRlatName(userVo_level.getLevelName());
                userOfUser.setUParentId(userId);
                userOfUser.setUChildId(t.getUserId());
                userOfUserList.add(userOfUser);
            });
        });

        if(userOfUserList.isEmpty()){
            return;
        }
        userOfUserMapper.insertList(userOfUserList);
    }
}
