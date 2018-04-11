package com.emucoo.service.task;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.TaskType;
import com.emucoo.model.TaskUnionUserList;

import java.util.List;

/**
 * @author river
 * @date 2018/2/23 17:36
 */
public interface TaskTypeService extends BaseService<TaskType> {
    Long batchUpdateIsUse(List<String> idList, Boolean isUse);
    List<TaskUnionUserList> findContacts(Integer contactsType);
}
