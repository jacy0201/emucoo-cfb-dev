package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormOpptValue;
import com.emucoo.model.TFormPbmVal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFormOpptValueMapper extends MyMapper<TFormOpptValue> {

    TFormOpptValue fetchOneSubPbmOpptValue(@Param("subProblemValueId") Long subProblemValueId, @Param("subProblemId") Long subProblemId, @Param("subProblemHeaderId") Long subProblemHeaderId, @Param("problemType") int problemType);

    TFormOpptValue fetchOnePbmOpptValue(@Param("problemValueId") Long problemValueId, @Param("opptId") Long opptId, @Param("problemType") int problemType);

    List<TFormOpptValue> selectUnionFormOpptsByPbmIds(@Param("pbmValueIds") List<Long> formAllPbmValueIds,
                                                      @Param("subPbmValueIds") List<Long> formAllSubPbmValueIds);

    List<TFormOpptValue> selectUnionFormOpptsByPbmIdsAndOppt(@Param("pbmValueIds") List<Long> formAllPbmValueIds,
                                                             @Param("subPbmValueIds") List<Long> formAllSubPbmValueIds,
                                                             @Param("opptId") Long opptId);

    List<TFormOpptValue> findOpptsByResultId(Long formResultId);

    void cleanByResultId(Long id);

    List<TFormOpptValue> findOpptValuesByOpptIdAndResult(@Param("resultIds")List<Long> resultIds, @Param("opptId")Long opptId);

    List<TFormOpptValue> findImportOpptValList(List<TFormPbmVal> tFormImportPbmVals);
}