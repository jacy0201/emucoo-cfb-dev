package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.comment.CommentSelectOut;
import com.emucoo.model.TTaskComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TTaskCommentMapper extends MyMapper<TTaskComment> {
    List<TTaskComment> fetchByLoopWorkId(Long loopWorkId);

    List<CommentSelectOut> selectByPages(@Param("workId") Long workId, @Param("workType") Integer workType, @Param("pageStart") long pageStart, @Param("pageSize") long pageSize);

}