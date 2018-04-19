package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysArea;

import java.util.List;

public interface SysAreaMapper extends MyMapper<SysArea> {

    List<SysArea> findAreaListByUserId(Long userId);
}