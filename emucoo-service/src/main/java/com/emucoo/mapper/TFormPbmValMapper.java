package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormPbm;
import com.emucoo.model.TFormPbmVal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFormPbmValMapper extends MyMapper<TFormPbmVal> {

    List<TFormPbmVal> findImportPbmValsByFormAndArrange(@Param("formId") Long formId, @Param("arrangeId") Long arrangeId,
                                                      @Param("formPbmIds") List<Long> formPbmIds);

    List<TFormPbmVal> findFormPbmValsByFormAndArrange(@Param("formId")Long checklistID, @Param("arrangeId")Long patrolShopArrangeID);

    List<TFormPbmVal> findFormPbmValsByFormAndArrangeList(@Param("formId")Long formId, @Param("arrangeIds")List<Long> arrangeIds);
}