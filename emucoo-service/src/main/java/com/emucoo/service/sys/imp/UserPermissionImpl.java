package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.UserPermissionMapper;
import com.emucoo.model.UserPermission;
import com.emucoo.service.sys.UserPermissionService;
import com.emucoo.utils.DateUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserPermissionImpl  extends BaseServiceImpl<UserPermission> implements UserPermissionService {

    @Resource
    private UserPermissionMapper userPermissionMapper;

    @Override
    public List<UserPermission> listUserPermissionGroup(Long userId){
        return userPermissionMapper.listUserPermissionGroup(userId);
    }


    @Override
    public int addUserPermissionGroup(Long userId, List<Long> groupIds) throws Exception {
        if(groupIds == null){
            throw new Exception(" list shouldn't null");
        }
        Example queryPrmtExam = new Example(UserPermission.class);
        queryPrmtExam.createCriteria().andEqualTo("userId",userId);
        //删除user之前的所有权限
        userPermissionMapper.deleteByExample(queryPrmtExam);
        //添加用户权限组
        List<UserPermission> userPermissionList = Lists.newArrayList();
        Date currentDate = DateUtil.currentDate();
        groupIds.forEach(t->{
            UserPermission userPermission = new UserPermission();
            userPermission.setUserId(userId);
            userPermission.setGroupId(t);
            userPermission.setCreateTime(currentDate);
            userPermission.setModifyTime(currentDate);
            userPermissionList.add(userPermission);
        });
        if(userPermissionList.isEmpty()){
            return 0;
        }
        return userPermissionMapper.insertList(userPermissionList);
    }
}
