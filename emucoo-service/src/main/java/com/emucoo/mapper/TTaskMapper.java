package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TTaskMapper extends MyMapper<TTask> {
    List<TTask> findImproveTaskList(@Param("opptId")Long opptId, @Param("reportId")Long reportId);

    int countCommonTaskByName(String keyword);

    List<TTask> listCommonTaskByName(@Param("keyword") String keyword, @Param("pageStart") int pageSt, @Param("pageSize") int pageSz);

    int judgeExistCommonTask(String taskName);

    void removeCommonTaskByIds(List<Long> ids);

    void switchCommonTaskByIds(@Param("ids") List<Long> data, @Param("sw") boolean sw);
}