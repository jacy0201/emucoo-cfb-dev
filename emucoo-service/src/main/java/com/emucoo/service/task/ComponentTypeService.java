package com.emucoo.service.task;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.ComponentType;

import java.util.List;

public interface ComponentTypeService extends BaseService<ComponentType> {
    List<ComponentType> listCheckedComponentType(List<String> compDetailIdList);
}
