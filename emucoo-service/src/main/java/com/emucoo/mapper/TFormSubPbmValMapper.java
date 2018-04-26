package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormSubPbmVal;
import org.apache.ibatis.annotations.Param;

public interface TFormSubPbmValMapper extends MyMapper<TFormSubPbmVal> {
    TFormSubPbmVal fetchOneSubPbmValue(@Param("problemValueId") Long problemValueId, @Param("subProblemId") Long subProblemId);
}