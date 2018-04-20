package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormType;

import java.util.List;

public interface TFormTypeMapper extends MyMapper<TFormType> {

    List<TFormType> findFormTypesByFormMainId(Long id);

    void upsert(TFormType formType);
}