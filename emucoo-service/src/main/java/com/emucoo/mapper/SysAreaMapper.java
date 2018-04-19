package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysArea;

import java.util.HashMap;
import java.util.List;

public interface SysAreaMapper extends MyMapper<SysArea> {

    /**
     * 根据机构查询分区信息
     */
    List<SysArea> listByDpt(HashMap paramMap);

    List<SysArea> findAreaListByUserId(Long userId);
}