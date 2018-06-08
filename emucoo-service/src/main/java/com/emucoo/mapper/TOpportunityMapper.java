package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.form.OpptDetailOut;
import com.emucoo.model.TOpportunity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TOpportunityMapper extends MyMapper<TOpportunity> {

    void removeByIds(List<Long> ids);

    void changeIsUse(@Param("ids") List<Long> ids, @Param("state") Boolean state);

    List<TOpportunity> findChancePointsByName(@Param("keyword") String keyword, @Param("ctype") int ctype, @Param("isUsed") int isUsed, @Param("startRow") int startRow, @Param("size") int pageSz);

    Integer countChancePointsByName(@Param("keyword") String keyword, @Param("ctype") int ctype, @Param("isUsed") int isUsed);

    void upsert(TOpportunity opportunity);

    void dropByIds(List<Long> opptIds);

    List<TOpportunity> findOpptsByPbmId(Long id);

    void cleanOpptsByResultId(Long id);

    Integer judgeExistsByName(@Param("opptId") Long opptId, @Param("opptName") String name);

    OpptDetailOut sumChancePointReport(Long id);
}