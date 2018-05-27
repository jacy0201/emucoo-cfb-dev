package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TOperateDataForWork;
import org.apache.ibatis.annotations.Param;

public interface TOperateDataForWorkMapper extends MyMapper<TOperateDataForWork> {

    TOperateDataForWork fetchOneByLoopWorkId(Long loopWorkId);

    void auditOperateData(TOperateDataForWork toof);

    TOperateDataForWork fetchOneByWorkIds(String workId, String subWorkId);

    TOperateDataForWork fetchOneByTaskItemIdAndLoopWorkId(@Param("loopWorkId") Long loopWorkId, @Param("taskItemId") Long taskItemId);
}