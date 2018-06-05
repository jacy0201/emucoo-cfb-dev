package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TTask;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TTaskMapper extends MyMapper<TTask> {
    List<TTask> findImproveTaskList(@Param("opptId")Long opptId, @Param("reportId")Long reportId);

    int countCommonTaskByName(@Param("keyword") String keyword, @Param("usage") Boolean usage);

    List<TTask> listCommonTaskByName(@Param("keyword") String keyword, @Param("usage") Boolean usage, @Param("pageStart") int pageSt, @Param("pageSize") int pageSz);

    int judgeExistCommonTask(@Param("taskName") String taskName);

    void removeCommonTaskByIds(List<Long> ids);

    void switchCommonTaskById(@Param("taskId") Long taskId, @Param("sw") boolean sw);

    List<TTask> filterAvailableCommonTask(Date theDay);

    List<TTask> filterAvailableAssignTask(Date theDay);

    List<TTask> filterAvailableImproveTask(Date theDay);
}