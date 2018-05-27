package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TTask;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TTaskMapper extends MyMapper<TTask> {
    List<TTask> findImproveTaskList(@Param("opptId")Long opptId, @Param("reportId")Long reportId);

    int countCommonTaskByName(@Param("keyword") String keyword);

    List<TTask> listCommonTaskByName(@Param("keyword") String keyword, @Param("pageStart") int pageSt, @Param("pageSize") int pageSz);

    int judgeExistCommonTask(@Param("taskName") String taskName);

    void removeCommonTaskByIds(List<Long> ids);

    void switchCommonTaskByIds(@Param("ids") List<Long> data, @Param("sw") boolean sw);

    List<TTask> filterAvailableCommonTask(Date tomorrow);

    List<TTask> filterAvailableAssignTask(Date tomorrow);
}