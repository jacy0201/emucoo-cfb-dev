package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TOpportunity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TOpportunityMapper extends MyMapper<TOpportunity> {
    void removeByIds(List<Long> ids);
    void enableByIds(List<Long> ids);
    void disableByIds(List<Long> ids);

    List<TOpportunity> findChancePointsByName(@Param("keyword") String keyword, @Param("startRow") int startRow, @Param("size") int pageSz);
    Integer countChancePointsByName(@Param("keyword") String keyword);
}