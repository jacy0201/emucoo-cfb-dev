package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TLabel;

import java.util.List;

public interface TLabelMapper extends MyMapper<TLabel> {
    List<TLabel> listLabelByTaskId(Long id);
}