package com.emucoo.service.manage.impl;

import com.emucoo.dto.modules.form.OpptDetailOut;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.manage.ChancePointService;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ChancePointServiceImpl implements ChancePointService {

    @Autowired
    private TOpportunityMapper opportunityMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TFormOpptValueMapper formOpptValueMapper;

    @Autowired
    private TFormCheckResultMapper formCheckResultMapper;

    @Autowired
    private TFormMainMapper formMainMapper;

    @Autowired
    private TShopInfoMapper shopInfoMapper;

    @Autowired
    private TFormPbmMapper formPbmMapper;

    @Override
    public List<TOpportunity> listChancePointsByNameKeyword(String keyword, int ctype, int isUsed, int pageNm, int pageSz) {
       return opportunityMapper.findChancePointsByName(keyword, ctype, isUsed, (pageNm - 1) * pageSz, pageSz);
    }

    @Override
    public int countChancePointsByNameKeyword(String keyword, int ctype, int isUsed) {
        return opportunityMapper.countChancePointsByName(keyword, ctype, isUsed);
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
        opportunity.setIsUse(true);
        opportunity.setIsDel(false);
        opportunity.setType(0);
        opportunity.setFrontCanCreate(false);
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
        opportunity.setModifyTime(DateUtil.currentDate());
        opportunityMapper.updateByPrimaryKeySelective(opportunity);
    }

    @Override
    public void deleteChancePoints(List<Long> ids) {
        opportunityMapper.removeByIds(ids);
    }

    @Override
    public OpptDetailOut fetchDetail(TOpportunity data) {
        OpptDetailOut out = new OpptDetailOut();
        TOpportunity oppt = opportunityMapper.selectByPrimaryKey(data.getId());
        SysUser user = sysUserMapper.selectByPrimaryKey(oppt.getCreateUserId());
        Example example = new Example(TFormOpptValue.class);
        example.setOrderByClause("create_time asc");
        example.createCriteria().andEqualTo("oppt_id", oppt.getId());
        TFormOpptValue fov = formOpptValueMapper.selectOneByExample(example);
        TFormPbm formPbm = formPbmMapper.selectByPrimaryKey(fov.getProblemId());
        TFormCheckResult fcr = formCheckResultMapper.selectByPrimaryKey(fov.getFormResultId());
        TFormMain formMain = formMainMapper.selectByPrimaryKey(fcr.getFormMainId());
        TShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(fcr.getShopId());

        out.setName(oppt.getName());
        out.setAreaName(shopInfo.getAreaName());
        out.setBrandName(shopInfo.getBrandName());
        out.setDate(DateUtil.dateToString(fcr.getCreateTime()));
        out.setDeptName(user.getDptName());
        out.setFormName(formMain.getName());
        out.setProblemName(formPbm.getName());

        return out;

    }

    @Override
    public boolean judgeExisted(TOpportunity opportunity) {
        if(opportunityMapper.judgeExistsByName(opportunity.getName()) > 0) {
            return true;
        }
        return false;
    }
}
