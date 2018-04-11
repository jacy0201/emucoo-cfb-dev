package com.emucoo.service.task.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.ComponentTypeMapper;
import com.emucoo.model.ComponentType;
import com.emucoo.service.task.ComponentTypeService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;
@Service
public class ComponentTypeServiceImpl extends BaseServiceImpl<ComponentType> implements ComponentTypeService {

    @Resource
    private ComponentTypeMapper componentTypeMapper;

    @Override
    public List<ComponentType> listCheckedComponentType(List<String> compDetailIdList){
        return componentTypeMapper.listCheckedComponentType(compDetailIdList);
    }
}
