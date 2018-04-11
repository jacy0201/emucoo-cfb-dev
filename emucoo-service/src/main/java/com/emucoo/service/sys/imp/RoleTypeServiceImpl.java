package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.RoleTypeMapper;
import com.emucoo.model.RoleType;
import com.emucoo.service.sys.RoleTypeService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author river
 * @date 2018/3/19 17:00
 */
@Service
public class RoleTypeServiceImpl extends BaseServiceImpl<RoleType> implements RoleTypeService {
    @Resource
    private RoleTypeMapper roleTypeMapper;

    @Override
    public List<RoleType> list(String labelNames){
        return roleTypeMapper.list(labelNames);
    }
}
