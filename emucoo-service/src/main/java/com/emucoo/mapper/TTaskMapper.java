package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TTaskMapper extends MyMapper<TTask> {
    List<TTask> findImproveTaskList(@Param("opptId")Long opptId, @Param("reportId")Long reportId);
}