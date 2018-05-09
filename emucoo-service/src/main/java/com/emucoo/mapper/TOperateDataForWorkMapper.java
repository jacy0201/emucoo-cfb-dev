package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TOperateDataForWork;

public interface TOperateDataForWorkMapper extends MyMapper<TOperateDataForWork> {

    TOperateDataForWork fetchOneByLoopWorkId(Long loopWorkId);

    void auditOperateData(TOperateDataForWork toof);

    TOperateDataForWork fetchOneByWorkIds(String workId, String subWorkId);

    TOperateDataForWork fetchOneByTaskItemIdAndLoopWorkId(Long loopWorkId, Long taskItemID);
}