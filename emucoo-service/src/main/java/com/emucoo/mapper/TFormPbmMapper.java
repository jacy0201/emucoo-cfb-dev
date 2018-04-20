package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormPbm;

import java.util.List;

public interface TFormPbmMapper extends MyMapper<TFormPbm> {

    List<TFormPbm> findFormPbmsByFormTypeId(Long id);

    void upsert(TFormPbm problem);
}