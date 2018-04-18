package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TTaskComment;

import java.util.List;

public interface TTaskCommentMapper extends MyMapper<TTaskComment> {
    List<TTaskComment> fetchByLoopWorkId(Long loopWorkId);

}