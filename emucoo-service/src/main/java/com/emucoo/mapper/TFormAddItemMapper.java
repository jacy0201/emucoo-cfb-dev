package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormAddItem;

import java.util.List;

public interface TFormAddItemMapper extends MyMapper<TFormAddItem> {

    List<TFormAddItem> findFormAddItemsByFormMainId(Long id);

    void upsertMulti(List<TFormAddItem> formAddItems);
}