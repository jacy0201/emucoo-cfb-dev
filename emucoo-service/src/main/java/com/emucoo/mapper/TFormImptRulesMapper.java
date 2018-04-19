package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormImptRules;

import java.util.List;

public interface TFormImptRulesMapper extends MyMapper<TFormImptRules> {

    List<TFormImptRules> findFormImptRulesByFormMainId(Long id);

    void upsertMulti(List<TFormImptRules> formImptRuless);
}