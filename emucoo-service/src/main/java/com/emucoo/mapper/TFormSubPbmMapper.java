package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormSubPbm;

import java.util.List;

public interface TFormSubPbmMapper extends MyMapper<TFormSubPbm> {

    List<TFormSubPbm> findFormSubPbmsByFormPbmId(Long id);

    void upsertMulti(List<TFormSubPbm> subProblems);

    void dropByProblemIds(List<Long> probIds);
}