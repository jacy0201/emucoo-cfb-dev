package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TOpportunity;

import java.util.List;

public interface TOpportunityMapper extends MyMapper<TOpportunity> {
    void removeByIds(List<Long> ids);
    void enableByIds(List<Long> ids);
    void disableByIds(List<Long> ids);

}