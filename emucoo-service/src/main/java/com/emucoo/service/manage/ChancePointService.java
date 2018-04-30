package com.emucoo.service.manage;

import com.emucoo.dto.modules.form.OpptDetailOut;
import com.emucoo.model.TOpportunity;

import java.util.List;

public interface ChancePointService {


    void createChancePoint(TOpportunity opportunity, Long userId);

    void updateChancePoint(TOpportunity opportunity, Long userId);

    void deleteChancePoints(List<Long> ids);

    void enableChancePoints(List<Long> ids);

    void disableChancePoints(List<Long> ids);

    List<TOpportunity> listChancePointsByNameKeyword(String keyword, int pageNm, int pageSz);

    int countChancePointsByNameKeyword(String keyword);

    TOpportunity fetchChancePointById(Long id);

    OpptDetailOut fetchDetail(TOpportunity data);

    boolean judgeExisted(TOpportunity opportunity);
}
