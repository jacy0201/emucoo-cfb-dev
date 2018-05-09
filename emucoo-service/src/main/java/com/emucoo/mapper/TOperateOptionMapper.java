package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TOperateOption;

import java.util.List;

public interface TOperateOptionMapper extends MyMapper<TOperateOption> {

    TOperateOption fetchOneByTaskId(Long taskId);

    List<TOperateOption> fetchOptionsByTaskId(Long taskId);
}