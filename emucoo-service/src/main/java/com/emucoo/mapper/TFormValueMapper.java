package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFormValueMapper extends MyMapper<TFormValue> {

    void cleanByResultId(Long id);

    List<TFormValue> findTypeTreeValueListUntilSubPbm(Long id);

    List<TFormValue> findTypeValueListByFormAndArrange(@Param("formId")Long checklistID, @Param("arrangeId")Long patrolShopArrangeID);
}