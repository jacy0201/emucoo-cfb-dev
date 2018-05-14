package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TTaskPerson;

import java.util.List;

public interface TTaskPersonMapper extends MyMapper<TTaskPerson> {
    List<TTaskPerson> fetchByTaskId(Long id);
}