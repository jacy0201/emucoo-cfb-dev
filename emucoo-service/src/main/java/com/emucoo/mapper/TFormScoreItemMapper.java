package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormScoreItem;

import java.util.List;

public interface TFormScoreItemMapper extends MyMapper<TFormScoreItem> {

    List<TFormScoreItem> findFormScoreItemsByFormMainId(Long id);

    void upsertMulti(List<TFormScoreItem> formScoreItems);
}