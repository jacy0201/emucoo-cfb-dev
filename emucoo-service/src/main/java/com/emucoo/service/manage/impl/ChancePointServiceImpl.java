package com.emucoo.service.manage.impl;

import com.emucoo.mapper.TOpportunityMapper;
import com.emucoo.model.TOpportunity;
import com.emucoo.service.manage.ChancePointService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ChancePointServiceImpl implements ChancePointService {

    @Autowired
    private TOpportunityMapper opportunityMapper;

    @Override
    public List<TOpportunity> listChancePointsByNameKeyword(String keyword, int pageNm, int pageSz) {
        Example example = new Example(TOpportunity.class);
        if(StringUtils.isNotBlank(keyword)) {
            example.createCriteria().andLike("name", "%" + keyword + "%");
        }

        return opportunityMapper.selectByExampleAndRowBounds(example, new RowBounds(pageNm-1, pageSz));
    }

    @Override
    public TOpportunity fetchChancePointById(Long id) {
        return opportunityMapper.selectByPrimaryKey(id);
    }

    @Override
    public void createChancePoint(TOpportunity opportunity) {
        opportunityMapper.insert(opportunity);
    }

    @Override
    public void enableChancePoints(List<Long> ids) {
        opportunityMapper.enableByIds(ids);
    }

    @Override
    public void disableChancePoints(List<Long> ids) {
        opportunityMapper.disableByIds(ids);
    }

    @Override
    public void updateChancePoint(TOpportunity opportunity) {
        opportunityMapper.updateByPrimaryKey(opportunity);
    }

    @Override
    public void deleteChancePoints(List<Long> ids) {
        opportunityMapper.removeByIds(ids);
    }
}
