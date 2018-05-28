package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormOppt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFormOpptMapper extends MyMapper<TFormOppt> {
    List<Long> fetchOpptIdsByProblemIds(@Param("probIds") List<Long> probIds, @Param("problemType") Integer problemType);

    void dropByProblemIds(List<Long> probIds);

    void cleanFormOpptRelationByResultId(Long id);

    List<TFormOppt> findFormOpptListByPbmId(@Param("subPbmIds") List<Long> subPbmIds, @Param("pbmIds")List<Long> pbmIds, @Param("type") int type, @Param("userId") Long userId);
}