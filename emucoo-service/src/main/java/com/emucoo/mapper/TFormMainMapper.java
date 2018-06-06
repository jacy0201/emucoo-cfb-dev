package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormMain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFormMainMapper extends MyMapper<TFormMain> {

    List<TFormMain> findFormsByName(@Param("keyword") String keyword, @Param("startRow") int startRow, @Param("size") int pageSz,
                                    @Param("formType")Integer formType);

    Integer countFormsByName(@Param("keyword") String keyword, @Param("formType")Integer formType);

    void removeByIds(List<Long> ids);

    void changeIsUse(@Param("ids") List<Long> ids, @Param("state") int state);

    TFormMain fetchOneById(Long id);

    void upsert(TFormMain formMain);

    List<TFormMain> findAvailableFormsUseForPlan(Long planId);
}