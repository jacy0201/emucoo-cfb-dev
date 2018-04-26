package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormPbmVal;
import org.apache.ibatis.annotations.Param;

public interface TFormPbmValMapper extends MyMapper<TFormPbmVal> {
    TFormPbmVal fetchOnePbmValue(@Param("formValueId") Long formValueId, @Param("problemId") Long problemId);
}