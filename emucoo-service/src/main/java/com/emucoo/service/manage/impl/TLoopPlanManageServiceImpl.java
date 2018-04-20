package com.emucoo.service.manage.impl;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.enums.Constant;
import com.emucoo.enums.DeleteStatus;
import com.emucoo.enums.WorkStatus;
import com.emucoo.mapper.TFormMainMapper;
import com.emucoo.mapper.TLoopPlanMapper;
import com.emucoo.mapper.TPlanFormRelationMapper;
import com.emucoo.model.TFormMain;
import com.emucoo.model.TLoopPlan;
import com.emucoo.model.TPlanFormRelation;
import com.emucoo.service.manage.TLoopPlanManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by sj on 2018/4/15.
 */
@Service
public class TLoopPlanManageServiceImpl extends BaseServiceImpl<TLoopPlan> implements TLoopPlanManageService {

    @Autowired
    private TLoopPlanMapper tLoopPlanMapper;

    @Autowired
    private TPlanFormRelationMapper tPlanFormRelationMapper;

    @Autowired
    private TFormMainMapper tFormMainMapper;

    @Transactional
    public void addPlan(TLoopPlan plan) {
        Date now = new Date();
        plan.setCreateTime(now);
        plan.setModifyTime(now);
        plan.setPlanStartDate(plan.getPlanStartDate());
        plan.setPlanEndDate(plan.getPlanEndDate());
        plan.setIsUse(WorkStatus.STOP_USE.getCode());
        plan.setIsDel(DeleteStatus.COMMON.getCode());
        plan.setOrgId(Constant.orgId);
        // 保存计划信息
        tLoopPlanMapper.addPlan(plan);
        for(TPlanFormRelation planFormRelation : plan.getPlanFormRelationList()) {
            planFormRelation.setPlanId(plan.getId());
            planFormRelation.setCreateTime(now);
            planFormRelation.setModifyTime(now);
        }
        // 保存计划与表单对应关系
        tPlanFormRelationMapper.addPlanFormRelation(plan.getPlanFormRelationList());
    }

    @Transactional
    public void updatePlanById(TLoopPlan plan) {
        Date now = new Date();
        plan.setModifyTime(now);
        // 根据id更新对应的计划数据
        tLoopPlanMapper.updatePlanById(plan);
        // 删除旧的表单与计划对应关系
        Example example = new Example(TPlanFormRelation.class);
        example.createCriteria().andEqualTo("planId", plan.getId());
        tPlanFormRelationMapper.deleteByExample(example);
        // 保存新的表单与计划关系
        for (TPlanFormRelation planFormRelation : plan.getPlanFormRelationList()) {
            planFormRelation.setPlanId(plan.getId());
            planFormRelation.setCreateTime(now);
            planFormRelation.setModifyTime(now);
        }
        tPlanFormRelationMapper.addPlanFormRelation(plan.getPlanFormRelationList());
    }

    @Transactional
    public void modifyPlanUseById(TLoopPlan plan) {
        Date now = new Date();
        plan.setModifyTime(now);
        tLoopPlanMapper.modifyPlanStatusById(plan);
        tPlanFormRelationMapper.modifyUseStatus(plan);
    }

    @Transactional
    public void deletePlanById(TLoopPlan plan) {
        Date now = new Date();
        plan.setIsDel(DeleteStatus.DELETED.getCode());
        plan.setModifyTime(now);
        tLoopPlanMapper.deletePlanById(plan);

        tPlanFormRelationMapper.deleteById(plan.getId());
    }

    public List<TLoopPlan> findPlanListByCondition(TLoopPlan plan) {
        List<TLoopPlan> planList = tLoopPlanMapper.findPlanListByCondition(plan);
        return planList;
    }

    public TLoopPlan findPlanById(TLoopPlan plan) {
        TLoopPlan tLoopPlan = tLoopPlanMapper.findPlanById(plan);
        if(tLoopPlan != null && tLoopPlan.getPlanFormRelationList() != null) {
            for(TPlanFormRelation tPlanFormRelation : tLoopPlan.getPlanFormRelationList()) {
                TFormMain tFormMain = tFormMainMapper.selectByPrimaryKey(tPlanFormRelation.getFormMainId());
                tPlanFormRelation.setName(tFormMain.getName());
            }
        }
        return tLoopPlan;
    }
}
