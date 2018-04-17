package com.emucoo.service.manage;

import com.emucoo.model.TOpportunity;

import java.util.List;

public interface ChancePointService {


    void createChancePoint(TOpportunity opportunity);

    void updateChancePoint(TOpportunity opportunity);

    void deleteChancePoints(List<Long> ids);

    void enableChancePoints(List<Long> ids);

    void disableChancePoints(List<Long> ids);

    List<TOpportunity> listChancePointsByNameKeyword(String keyword, int pageNm, int pageSz);

    TOpportunity fetchChancePointById(Long id);
}
