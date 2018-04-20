package com.emucoo.service.manage.impl;

import com.emucoo.mapper.TOpportunityMapper;
import com.emucoo.model.TOpportunity;
import com.emucoo.service.manage.ChancePointService;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChancePointServiceImpl implements ChancePointService {

    @Autowired
    private TOpportunityMapper opportunityMapper;

    @Override
    public List<TOpportunity> listChancePointsByNameKeyword(String keyword, int pageNm, int pageSz) {
       return opportunityMapper.findChancePointsByName(keyword, (pageNm - 1) * pageSz, pageSz);
    }

    @Override
    public int countChancePointsByNameKeyword(String keyword) {
        return opportunityMapper.countChancePointsByName(keyword);
    }

    @Override
    public TOpportunity fetchChancePointById(Long id) {
        return opportunityMapper.selectByPrimaryKey(id);
    }

    @Override
    public void createChancePoint(TOpportunity opportunity, Long userId) {
        opportunity.setCreateTime(DateUtil.currentDate());
        opportunity.setModifyTime(DateUtil.currentDate());
        opportunity.setCreateUserId(userId);
        opportunity.setModifyUserId(userId);
        opportunity.setCreateType(1);
        opportunity.setIsUse(false);
        opportunityMapper.upsert(opportunity);
    }

    @Override
    public void enableChancePoints(List<Long> ids) {
        opportunityMapper.changeIsUse(ids, true);
    }

    @Override
    public void disableChancePoints(List<Long> ids) {
        opportunityMapper.changeIsUse(ids, false);
    }

    @Override
    public void updateChancePoint(TOpportunity opportunity, Long userId) {
        opportunity.setCreateType(1);
        opportunity.setModifyUserId(userId);
        opportunityMapper.upsert(opportunity);
    }

    @Override
    public void deleteChancePoints(List<Long> ids) {
        opportunityMapper.removeByIds(ids);
    }
}
