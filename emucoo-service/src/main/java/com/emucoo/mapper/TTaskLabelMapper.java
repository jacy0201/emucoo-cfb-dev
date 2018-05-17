package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TTaskLabel;

public interface TTaskLabelMapper extends MyMapper<TTaskLabel> {
    void dropByTaskId(Long id);
}