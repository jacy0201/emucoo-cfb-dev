package com.emucoo.service.task.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.TaskTypeMapper;
import com.emucoo.mapper.TaskUnionUserListMapper;
import com.emucoo.model.TaskType;
import com.emucoo.model.TaskUnionUserList;
import com.emucoo.service.task.TaskTypeService;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author river
 * @date 2018/2/23 17:33
 */
@Service
public class TaskTypeServiceImpl extends BaseServiceImpl<TaskType> implements TaskTypeService {
    @Resource
    private TaskTypeMapper taskTypeMapper;

    @Resource
    private TaskUnionUserListMapper taskUnionUserListMapper;

    @Override
    public Long batchUpdateIsUse(List<String> idList, Boolean isUse) {
        return taskTypeMapper.batchUpdateIsUse(idList, isUse);
    }

    @Override
    public List<TaskUnionUserList> findContacts(Integer contactsType) {
        return taskUnionUserListMapper.findContacts(contactsType);
    }

}
