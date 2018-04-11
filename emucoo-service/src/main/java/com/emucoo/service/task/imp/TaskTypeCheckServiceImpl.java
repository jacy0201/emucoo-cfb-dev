package com.emucoo.service.task.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.TaskTypeCheckMapper;
import com.emucoo.mapper.TaskTypeMapper;
import com.emucoo.model.TaskType;
import com.emucoo.model.TaskTypeCheck;
import com.emucoo.service.task.TaskTypeCheckService;
import com.emucoo.service.task.TaskTypeService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author river
 * @date 2018/2/23 17:33
 */
@Service
public class TaskTypeCheckServiceImpl extends BaseServiceImpl<TaskTypeCheck> implements TaskTypeCheckService {
    @Resource
    private TaskTypeCheckMapper taskTypeCheckMapper;


}
