package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormOpptValue;
import org.apache.ibatis.annotations.Param;

public interface TFormOpptValueMapper extends MyMapper<TFormOpptValue> {

    TFormOpptValue fetchOneSubPbmOpptValue(@Param("subProblemValueId") Long subProblemValueId, @Param("subProblemId") Long subProblemId, @Param("subProblemHeaderId") Long subProblemHeaderId, @Param("problemType") int problemType);

    TFormOpptValue fetchOnePbmOpptValue(@Param("problemValueId") Long problemValueId, @Param("opptId") Long opptId, @Param("problemType") int problemType);
}