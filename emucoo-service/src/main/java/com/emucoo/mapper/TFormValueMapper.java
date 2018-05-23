package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFormValueMapper extends MyMapper<TFormValue> {

    TFormValue fetchOneFormValue(@Param("formId") Long formId, @Param("moduleId") Long moduleId, @Param("arrangeId") Long arrangeId);

    void cleanByResultId(Long id);

    List<TFormValue> findTypeTreeValueListUntilSubPbm(Long id);
}