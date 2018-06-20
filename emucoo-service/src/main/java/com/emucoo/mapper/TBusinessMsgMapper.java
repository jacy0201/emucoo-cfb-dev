package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.MsgReceiveSummary;
import com.emucoo.model.TBusinessMsg;

import java.util.List;

public interface TBusinessMsgMapper extends MyMapper<TBusinessMsg> {

    List<MsgReceiveSummary> findMsgSummaryByUserId(Long userId);

    void updateReadStatusByIds(List<Long> ids);

    MsgReceiveSummary findNewestReadedMsgByFunctionType(Integer functionType);
}