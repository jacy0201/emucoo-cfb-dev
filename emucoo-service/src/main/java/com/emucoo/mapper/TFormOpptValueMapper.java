package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormOpptValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFormOpptValueMapper extends MyMapper<TFormOpptValue> {

    List<TFormOpptValue> selectUnionFromOpptsByPbmIds(@Param("pbmValueIds")List<Long> formAllPbmValueIds,
                                                      @Param("subPbmValueIds")List<Long> formAllSubPbmValueIds);

    List<TFormOpptValue> selectUnionFromOpptsByPbmIdsAndOppt(@Param("pbmValueIds")List<Long> formAllPbmValueIds,
                                                             @Param("subPbmValueIds")List<Long> formAllSubPbmValueIds,
                                                             @Param("opptId")Long opptId);
}