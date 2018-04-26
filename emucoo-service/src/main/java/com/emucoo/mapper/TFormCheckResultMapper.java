package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormCheckResult;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface TFormCheckResultMapper extends MyMapper<TFormCheckResult> {

    void saveSummaryToFormResult(@Param("summary") String summary, @Param("arrangeId") Long patrolShopArrangeID,
                                 @Param("formId") Long checklistID, @Param("modifyDate") Date date);
}