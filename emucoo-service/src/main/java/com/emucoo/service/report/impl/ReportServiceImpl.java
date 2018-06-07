package com.emucoo.service.report.impl;

import com.emucoo.common.exception.ApiException;
import com.emucoo.common.exception.BaseException;
import com.emucoo.dto.modules.abilityForm.AbilitySubForm;
import com.emucoo.dto.modules.abilityForm.AbilitySubFormKind;
import com.emucoo.dto.modules.abilityForm.ProblemImg;
import com.emucoo.dto.modules.abilityForm.ProblemVo;
import com.emucoo.dto.modules.abilityForm.SubProblemVo;
import com.emucoo.dto.modules.abilityReport.AbilityReportVo;
import com.emucoo.dto.modules.report.AdditionItemVo;
import com.emucoo.dto.modules.report.ChancePointVo;
import com.emucoo.dto.modules.report.ChecklistKindScoreVo;
import com.emucoo.dto.modules.report.FormRulesVo;
import com.emucoo.dto.modules.report.GetOpptIn;
import com.emucoo.dto.modules.report.GetOpptOut;
import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.ReportVo;
import com.emucoo.dto.modules.report.ReportWorkVo;
import com.emucoo.enums.Constant;
import com.emucoo.enums.FormType;
import com.emucoo.enums.ProblemType;
import com.emucoo.enums.ShopArrangeStatus;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.report.ReportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Service
public class ReportServiceImpl implements ReportService {

    private Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private TShopInfoMapper tShopInfoMapper;

    @Autowired
    private SysPostMapper sysPostMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    @Autowired
    private TFormAddItemMapper tFormAddItemMapper;


    @Autowired
    private TFormPbmValMapper tFormPbmValMapper;

    @Autowired
    private TFormSubPbmValMapper tFormSubPbmValMapper;

    @Autowired
    private TFormOpptValueMapper tFormOpptValueMapper;

    @Autowired
    private TFrontPlanMapper tFrontPlanMapper;

    @Autowired
    private TFormTypeMapper tFormTypeMapper;

    @Autowired
    private TFormMainMapper tFormMainMapper;

    @Autowired
    private TFormImptRulesMapper tFormImptRulesMapper;

    @Autowired
    private TFormPbmMapper tFormPbmMapper;

    @Autowired
    private TFormScoreItemMapper tFormScoreItemMapper;

    @Autowired
    private TFormValueMapper tFormValueMapper;

    @Autowired
    private TOpportunityMapper tOpportunityMapper;

    @Autowired
    private TFormCheckResultMapper tFormCheckResultMapper;

    @Autowired
    private TFormAddItemValueMapper tFormAddItemValueMapper;

    @Autowired
    private TReportMapper tReportMapper;

    @Autowired
    private TReportUserMapper tReportUserMapper;

    @Autowired
    private TReportOpptMapper tReportOpptMapper;

    @Autowired
    private TFrontPlanFormMapper tFrontPlanFormMapper;

    @Autowired
    private TTaskMapper tTaskMapper;

    @Autowired
    private TLoopWorkMapper tLoopWorkMapper;

    @Autowired
    private TFileMapper tFileMapper;

    public ReportVo getReportPreview(SysUser user, GetReportIn reportIn) {
        ReportVo reportOut = new ReportVo();
        // 查询店铺名
        TShopInfo shop = tShopInfoMapper.selectByPrimaryKey(reportIn.getShopID());
        if(shop == null) {
            return null;
        }
        reportOut.setShopName(shop.getShopName());
        // 查询店长
        SysUser shopOwer = sysUserMapper.selectByPrimaryKey(shop.getShopManagerId());
        reportOut.setShopownerName(shopOwer.getRealName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        reportOut.setCheckDate(date);
        // 查询打表人所属部门
        SysDept department = sysDeptMapper.selectByPrimaryKey(user.getDptId());
        reportOut.setCheckDepartmentName(department.getDptName());
        reportOut.setInspectorName(user.getRealName());
        List<SysPost> postions = sysPostMapper.findPositionByUserId(user.getId());
        String postStr = "";
        for(SysPost post : postions) {
            postStr += post.getPostName() + ",";
        }
        if(StringUtils.isNotBlank(postStr)) {
            postStr = postStr.substring(0, postStr.length() - 1);
        }
        reportOut.setInspectorPosition(postStr);

        // 获取额外项
        List<AdditionItemVo> additionItemVos = new ArrayList<>();
        Example formAddTimeExp = new Example(TFormAddItem.class);
        formAddTimeExp.createCriteria().andEqualTo("formMainId", reportIn.getChecklistID());
        List<TFormAddItem> tFormAddItems = tFormAddItemMapper.selectByExample(formAddTimeExp);
        for(TFormAddItem tFormAddItem : tFormAddItems) {
            AdditionItemVo additionItemVo = new AdditionItemVo();
            additionItemVo.setItemID(tFormAddItem.getId());
            additionItemVo.setName(tFormAddItem.getName());
            additionItemVo.setValue("");
            additionItemVos.add(additionItemVo);
        }
        reportOut.setAdditionArray(additionItemVos);

        //获取规则
        // 重点项失分统计
        /*List<TFormPbm> tFormPbms = tFormPbmMapper.findFormPbmsByFormMainId(reportIn.getChecklistID());
        List<Long> formImportPbmIds = new ArrayList<>();
        for(TFormPbm tFormPbm : tFormPbms) {
            // 重点项归类
            if(tFormPbm.getImportant()) {
                formImportPbmIds.add(tFormPbm.getId());
            }

        }*/
        int importNum = 0;
        /*if(formImportPbmIds.size() > 0) {
            // 查询重要项结果值列表
            List<TFormPbmVal> tFormImportPbmVals = tFormPbmValMapper.findImportPbmValsByFormAndArrange(reportIn.getChecklistID(),
                    reportIn.getPatrolShopArrangeID(), formImportPbmIds);
            List<TFormOpptValue> tFormImportOpptValues = tFormOpptValueMapper.findImportOpptValList(tFormImportPbmVals);
            for (TFormPbmVal tFormPbmVal : tFormImportPbmVals) {
                for (TFormPbm tFormPbm : tFormPbms) {
                    if(tFormPbm.getId().equals(tFormPbmVal.getFormProblemId())) {
                        // 抽样项题项中只要有一个子题单元被否，就出发重点项规则
                        if (tFormPbmVal.getScore() < tFormPbm.getScore()) {
                            importNum++;
                        }
                        break;
                    }
                }
            }
        }*/
        // 查询重点题列表
        List<TFormPbmVal> tFormImportPbmVals = tFormPbmValMapper.findImportPbmValsByFormAndArrange(reportIn.getChecklistID(),
                reportIn.getPatrolShopArrangeID());
        if(CollectionUtils.isNotEmpty(tFormImportPbmVals)) {
            // 查询重点项失分的机会点情况
            List<TFormOpptValue> tFormImportOpptValues = tFormOpptValueMapper.findImportOpptValList(tFormImportPbmVals);
            if (CollectionUtils.isNotEmpty(tFormImportOpptValues)) {
                importNum = tFormImportOpptValues.size();
            }
        }

        // 统计重点项得分为0的项
        List<FormRulesVo> formRulesVos = new ArrayList<>();
        FormRulesVo formRulesVo = new FormRulesVo();
        formRulesVo.setItemID(1);
        formRulesVo.setName("importItem");
        formRulesVo.setValue("本检查表共出现" + importNum  + "次重点检查项失分");
        formRulesVos.add(formRulesVo);
        // 统计N/A项

        List<TFormPbmVal> tFormPbmVals = tFormPbmValMapper.findFormPbmValsByFormAndArrange(reportIn.getChecklistID(),
                reportIn.getPatrolShopArrangeID());
        int naNum = 0;
        int naTotalScore = 0;
        List<Long> formAllPbmValueIds = new ArrayList<>();
        int actualScore = 0;
        for (TFormPbmVal tFormPbmVal : tFormPbmVals) {
            formAllPbmValueIds.add(tFormPbmVal.getId());
            if (tFormPbmVal.getIsNa().equals(Boolean.TRUE)) {
                naNum++;
                TFormPbm tFormPbm = tFormPbmMapper.selectByPrimaryKey(tFormPbmVal.getFormProblemId());
                naTotalScore += tFormPbm.getScore();
            } else {
                actualScore += tFormPbmVal.getScore();
            }

        }
        FormRulesVo formNa = new FormRulesVo();
        formNa.setItemID(2);
        formNa.setName("N/A");
        formNa.setValue("本检查表共有" + naNum + "个N/A项");
        formRulesVos.add(formNa);
        reportOut.setRulesArray(formRulesVos);
        // 统计触发的机会点
        if(formAllPbmValueIds.size() > 0) {
            Example subPbmValExp = new Example(TFormSubPbmVal.class);
            subPbmValExp.createCriteria().andIn("problemValueId", formAllPbmValueIds);
            List<TFormSubPbmVal> tFormSubPbmVals = tFormSubPbmValMapper.selectByExample(subPbmValExp);
            List<Long> formAllSubPbmValueIds = new ArrayList<>();
            for (TFormSubPbmVal tFormSubPbmVal : tFormSubPbmVals) {
                formAllSubPbmValueIds.add(tFormSubPbmVal.getId());
            }

            // 根据题项id或子题项id查询关联的机会点信息
            List<TFormOpptValue> tFormOpptValues = tFormOpptValueMapper.selectUnionFormOpptsByPbmIds(formAllPbmValueIds, formAllSubPbmValueIds.size() > 0 ? formAllSubPbmValueIds : null);
            reportOut.setChancePointNum(tFormOpptValues.size() != 0 ? String.valueOf(tFormOpptValues.size()) : "0");
            List<ChancePointVo> chancePointVos = new ArrayList<>();
            for (TFormOpptValue tFormOpptValue : tFormOpptValues) {
                ChancePointVo chancePointVo = new ChancePointVo();
                chancePointVo.setChancePointID(tFormOpptValue.getOpptId());
                chancePointVo.setChancePointTitle(tFormOpptValue.getOpptName());
                /*Example formOpptValExp = new Example(TFormOpptValue.class);
                formOpptValExp.createCriteria().andEqualTo("opptId", tFormOpptValue.getOpptId()).andEqualTo("isPick", true);
                List<TFormOpptValue> certainFormOpptValues = tFormOpptValueMapper.selectByExample(formOpptValExp);*/
                // 计算该机会点使用情况
                List<TFormOpptValue> opptListUseInCertainShopAndForm = opptListInShopAndForm(reportIn.getShopID(), reportIn.getChecklistID(), tFormOpptValue.getOpptId());
                chancePointVo.setChancePointFrequency(opptListUseInCertainShopAndForm == null ? 0 : opptListUseInCertainShopAndForm.size());
                // 查询该机会点关联的题项信息
                TFormPbmVal pbmValForThisOppt = null;
                TFormSubPbmVal subPbmValForThisOppt = null;
                if (ProblemType.NOT_SAMPLE.getCode().equals(tFormOpptValue.getProblemType().intValue())) {
                    pbmValForThisOppt = tFormPbmValMapper.selectByPrimaryKey(tFormOpptValue.getProblemValueId());
                    if (StringUtils.isNotBlank(pbmValForThisOppt.getDescImgIds())) {
                        List<TFile> descImgs = tFileMapper.selectByIds(pbmValForThisOppt.getDescImgIds());
                        List<ProblemImg> problemImgs = new ArrayList<>();
                        for (TFile file : descImgs) {
                            ProblemImg problemImg = new ProblemImg();
                            problemImg.setImgUrl(file.getImgUrl());
                            problemImgs.add(problemImg);
                        }
                        chancePointVo.setDescImgArr(problemImgs);
                    }
                } else if (ProblemType.SAMPLING.getCode().equals(tFormOpptValue.getProblemType().intValue())) {
                    subPbmValForThisOppt = tFormSubPbmValMapper.selectByPrimaryKey(tFormOpptValue.getSubProblemValueId());
                    pbmValForThisOppt = tFormPbmValMapper.selectByPrimaryKey(subPbmValForThisOppt.getProblemValueId());
                }
                // 查询所属题项类型
                TFormValue formValue = tFormValueMapper.selectByPrimaryKey(pbmValForThisOppt.getFormValueId());
                StringBuilder pbmCascadingRelation = new StringBuilder();
                if (subPbmValForThisOppt == null) {
                    pbmCascadingRelation.append(formValue.getFormTypeName()).append("#").append(pbmValForThisOppt.getProblemName());
                } else {
                    pbmCascadingRelation.append(formValue.getFormTypeName()).append("#").append(pbmValForThisOppt.getProblemName())
                            .append("#").append(subPbmValForThisOppt.getSubProblemName());
                }
                chancePointVo.setChanceContent(pbmCascadingRelation.toString());
                //TOpportunity tOpportunity = tOpportunityMapper.selectByPrimaryKey(tFormOpptValue.getOpptId());
                //chancePointVo.setChanceDescription(tOpportunity.getDescription());
                chancePointVo.setChanceDescription(tFormOpptValue.getOpptDesc());
                chancePointVos.add(chancePointVo);
            }
            reportOut.setChancePointArr(chancePointVos);


        }

        TFormMain tFormMain = tFormMainMapper.selectByPrimaryKey(reportIn.getChecklistID());
        // 预设的表单总分
        int preTotalScore = tFormMain.getTotalScore();
        // 计算最终得分，去除重点项失分
        Example imptRuleExp = new Example(TFormImptRules.class);
        imptRuleExp.createCriteria().andEqualTo("formMainId", reportIn.getChecklistID());
        imptRuleExp.setOrderByClause("count_num desc");
        List<TFormImptRules> formImptRules = tFormImptRulesMapper.selectByExample(imptRuleExp);
        int discountNumber = 0;
        boolean hasHit = false;
        for(TFormImptRules formImptRule : formImptRules) {
            if (importNum >= formImptRule.getCountNum()) {
                discountNumber = formImptRule.getDiscountNum();
                hasHit = true;
                break;
            }
        }
        if(!hasHit) {
            discountNumber = 100;
        }
        int realTotal = (preTotalScore - naTotalScore);
        float fLastScore = actualScore * ((float)discountNumber / 100);
        int lastScore = Math.round(fLastScore);
        reportOut.setRealTotal(realTotal);
        reportOut.setRealScore(lastScore);
        float totalScoreRate = (float)lastScore / realTotal;
        // 计分项规则
        Example formScoreExp = new Example(TFormScoreItem.class);
        formScoreExp.createCriteria().andEqualTo("formMainId", reportIn.getChecklistID());
        formScoreExp.setOrderByClause("score_begin desc");
        List<TFormScoreItem> tFormScoreItems = tFormScoreItemMapper.selectByExample(formScoreExp);
        if(tFormScoreItems.size() > 0) {
            String summaryImgUrl = "";
            hasHit = false;
            for (TFormScoreItem tFormScoreItem : tFormScoreItems) {
                if (totalScoreRate * 100 >= tFormScoreItem.getScoreBegin()) {
                    summaryImgUrl = tFormScoreItem.getName();
                    hasHit = true;
                    break;
                }
            }
            if (!hasHit) {
                summaryImgUrl = tFormScoreItems.get(tFormScoreItems.size() - 1).getName();
            }
            reportOut.setScoreSummaryImg(summaryImgUrl);
        }

        // 统计模块情况
        /*Example formTypeValueExp = new Example(TFormValue.class);
        formTypeValueExp.createCriteria().andEqualTo("frontPlanId", reportIn.getPatrolShopArrangeID()).andEqualTo("formMainId", reportIn.getChecklistID());
        List<TFormValue> formTypeValues = tFormValueMapper.(formTypeValueExp);*/
        List<TFormValue> formTypeValues = tFormValueMapper.findTypeValueListByFormAndArrange(reportIn.getChecklistID(), reportIn.getPatrolShopArrangeID());
        List<ChecklistKindScoreVo> checklistKindScoreVos = new ArrayList<>();
        for(TFormValue tFormValue : formTypeValues) {
            ChecklistKindScoreVo checklistKindScoreVo = new ChecklistKindScoreVo();
            checklistKindScoreVo.setRealScore(tFormValue.getScore());
            checklistKindScoreVo.setRealTotal(tFormValue.getTotal());
            checklistKindScoreVo.setScoreRate(tFormValue.getScoreRate());
            checklistKindScoreVo.setKindID(tFormValue.getFromTypeId());
            checklistKindScoreVo.setKindName(tFormValue.getFormTypeName());
            checklistKindScoreVos.add(checklistKindScoreVo);
        }
        reportOut.setChecklistKindScoreArr(checklistKindScoreVos);

        return reportOut;
    }

    /**
     * 根据店铺和表单查询该机会点的使用情况
     * @param shopId
     * @param formId
     * @param opptId
     * @return
     */
    private List<TFormOpptValue> opptListInShopAndForm(Long shopId, Long formId, Long opptId) {
        // 根据店铺查询巡店安排
        /*Example arrangeExp = new Example(TFrontPlan.class);
        arrangeExp.createCriteria().andEqualTo("shopId", shopId).andEqualTo("status", ShopArrangeStatus.FINISH_CHECK.getCode()).andEqualTo("isDel", false);
        List<TFrontPlan> tFrontPlans = tFrontPlanMapper.selectByExample(arrangeExp);
        if(tFrontPlans.size() > 0) {
            List<Long> arrangeIds = new ArrayList<>();
            for (TFrontPlan tFrontPlan : tFrontPlans) {
                arrangeIds.add(tFrontPlan.getId());
            }
            // 根据巡店安排和表单查询题项
            List<TFormPbmVal> tFormPbmVals = tFormPbmValMapper.findFormPbmValsByFormAndArrangeList(formId, arrangeIds);
            List<Long> formAllPbmValueIds = new ArrayList<>();
            for (TFormPbmVal tFormPbmVal : tFormPbmVals) {
                formAllPbmValueIds.add(tFormPbmVal.getId());
            }
            if(CollectionUtils.isEmpty(formAllPbmValueIds)) {
                return null;
            }
            Example subPbmValExp = new Example(TFormSubPbmVal.class);
            subPbmValExp.createCriteria().andIn("problemValueId", formAllPbmValueIds);
            List<TFormSubPbmVal> tFormSubPbmVals = tFormSubPbmValMapper.selectByExample(subPbmValExp);
            List<Long> formAllSubPbmValueIds = new ArrayList<>();
            for (TFormSubPbmVal tFormSubPbmVal : tFormSubPbmVals) {
                formAllSubPbmValueIds.add(tFormSubPbmVal.getId());
            }
            List<TFormOpptValue> tFormOpptValues = tFormOpptValueMapper.selectUnionFormOpptsByPbmIdsAndOppt(formAllPbmValueIds, formAllSubPbmValueIds, opptId);
            return  tFormOpptValues;
            } else {
            return null;
            }
            */


        Example arrangeExp = new Example(TFrontPlan.class);
        arrangeExp.createCriteria().andEqualTo("shopId", shopId).andEqualTo("status", ShopArrangeStatus.FINISH_CHECK.getCode()).andEqualTo("isDel", false);
        List<TFrontPlan> tFrontPlans = tFrontPlanMapper.selectByExample(arrangeExp);
        if(CollectionUtils.isEmpty(tFrontPlans)) {
            return null;
        }
        List<Long> arrangeIds = new ArrayList<>();
        for(TFrontPlan frontPlan : tFrontPlans) {
            arrangeIds.add(frontPlan.getId());
        }
        Example resultExp = new Example(TFormCheckResult.class);
        resultExp.createCriteria().andEqualTo("formMainId", formId).andIn("frontPlanId", arrangeIds);
        List<TFormCheckResult> results = tFormCheckResultMapper.selectByExample(resultExp);
        if(CollectionUtils.isEmpty(results)) {
            return null;
        }
        List<Long> resultIds = new ArrayList<>();
        for(TFormCheckResult result : results) {
            resultIds.add(result.getId());
        }
        List<TFormOpptValue> tFormOpptValues = tFormOpptValueMapper.findOpptValuesByOpptIdAndResult(resultIds, opptId);

        return tFormOpptValues;

    }

    @Transactional
    public Long saveReport(SysUser user, ReportVo reportIn) {

        try{
            Example planFormExp = new Example(TFrontPlanForm.class);
            planFormExp.createCriteria().andEqualTo("frontPlanId", reportIn.getPatrolShopArrangeID()).andEqualTo("formMainId", reportIn.getChecklistID())
                    .andEqualTo("reportStatus", 1).andEqualTo("isDel", false);
            List<TFrontPlanForm> planForms = tFrontPlanFormMapper.selectByExample(planFormExp);
            if(CollectionUtils.isNotEmpty(planForms)) {
                throw new BaseException("报告请勿重复保存！");
            }
            //获取规则
            // 重点项失分统计
            /*List<TFormPbm> tFormPbms = tFormPbmMapper.findFormPbmsByFormMainId(reportIn.getChecklistID());
            List<Long> formImportPbmIds = new ArrayList<>();
            for (TFormPbm tFormPbm : tFormPbms) {
                // 重点项归类
                if (tFormPbm.getImportant()) {
                    formImportPbmIds.add(tFormPbm.getId());
                }
            }*/
            int importNum = 0;
            /*if (formImportPbmIds.size() > 0) {
                // 查询重要项结果值列表
                List<TFormPbmVal> tFormImportPbmVals = tFormPbmValMapper.findImportPbmValsByFormAndArrange(reportIn.getChecklistID(),
                        reportIn.getPatrolShopArrangeID());
                for (TFormPbmVal tFormPbmVal : tFormImportPbmVals) {
                    for (TFormPbm tFormPbm : tFormPbms) {
                        if (tFormPbm.getId().equals(tFormPbmVal.getFormProblemId())) {
                            // 抽样项题项中只要有一个子题单元被否，就出发重点项规则
                            if (tFormPbmVal.getScore() < tFormPbm.getScore()) {
                                importNum++;
                            }
                            break;
                        }
                    }

                }
            }*/
            // 查询重点题列表
            List<TFormPbmVal> tFormImportPbmVals = tFormPbmValMapper.findImportPbmValsByFormAndArrange(reportIn.getChecklistID(),
                    reportIn.getPatrolShopArrangeID());
            if (CollectionUtils.isNotEmpty(tFormImportPbmVals)) {
                // 查询重点项失分的机会点情况
                List<TFormOpptValue> tFormImportOpptValues = tFormOpptValueMapper.findImportOpptValList(tFormImportPbmVals);
                if (CollectionUtils.isNotEmpty(tFormImportOpptValues)) {
                    importNum = tFormImportOpptValues.size();
                }
            }
            List<TFormPbmVal> tFormPbmVals = tFormPbmValMapper.findFormPbmValsByFormAndArrange(reportIn.getChecklistID(),
                    reportIn.getPatrolShopArrangeID());
            int naNum = 0;
            List<Long> formAllPbmValueIds = new ArrayList<>();
            for (TFormPbmVal tFormPbmVal : tFormPbmVals) {
                formAllPbmValueIds.add(tFormPbmVal.getId());
                if (tFormPbmVal.getIsNa().equals(Boolean.TRUE)) {
                    naNum++;
                }
            }
            TFormCheckResult checkResult = new TFormCheckResult();
            checkResult.setSummary(reportIn.getSummary());
            checkResult.setScore(reportIn.getRealScore());
            if(reportIn.getRealTotal() == 0) {
                throw new BaseException("分值统计异常！");
            }
            checkResult.setActualTotal(reportIn.getRealTotal());
            checkResult.setScoreRate((float)reportIn.getRealScore() / reportIn.getRealTotal() * 100);
            checkResult.setFrontPlanId(reportIn.getPatrolShopArrangeID());
            checkResult.setFormMainId(reportIn.getChecklistID());
            checkResult.setModifyTime(new Date());
            checkResult.setImptItemDenyNum(importNum);
            checkResult.setNaNum(naNum);
            checkResult.setSummaryImg(reportIn.getScoreSummaryImg());
            // 更新结果
            tFormCheckResultMapper.saveFormResult(checkResult);

            TFormCheckResult result = findFormResult(reportIn.getPatrolShopArrangeID(), reportIn.getChecklistID());
            if (result == null) {
                throw new BaseException("打表结果不存在！");
            }
            List<TFormAddItemValue> tFormAddItemValues = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(reportIn.getAdditionArray())) {
                for (AdditionItemVo additionItemVo : reportIn.getAdditionArray()) {
                    TFormAddItemValue formAddItemValue = new TFormAddItemValue();
                    formAddItemValue.setId(additionItemVo.getItemID());
                    formAddItemValue.setValue(additionItemVo.getValue());
                    formAddItemValue.setFormAdditionItemName(additionItemVo.getName());
                    tFormAddItemValues.add(formAddItemValue);
                }
                // 添加额外项值
                tFormAddItemValueMapper.addAdditionItemList(tFormAddItemValues, new Date(), result.getId());
            }

            // 保存报告
            TReport report = new TReport();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            report.setCheckFormTime(sdf.parse(reportIn.getCheckDate()));
            Date now = new Date();
            report.setCreateTime(now);
            report.setCreateUserId(user.getId());
            report.setOrgId(Constant.orgId);

            // 查询店铺名
            TShopInfo shop = tShopInfoMapper.selectByPrimaryKey(reportIn.getShopID());
            report.setShopId(shop.getId());
            report.setShopName(shop.getShopName());
            // 查询店长
            SysUser shopOwer = sysUserMapper.selectByPrimaryKey(shop.getShopManagerId());
            report.setShopkeeperId(shopOwer.getId());
            report.setShopkeeperName(shopOwer.getRealName());
            // 查询打表人所属部门
            SysDept department = sysDeptMapper.selectByPrimaryKey(user.getDptId());
            report.setReporterDptId(department.getId());
            report.setReporterDptName(department.getDptName());
            // 打表人信息
            report.setReporterId(user.getId());
            report.setReporterName(user.getRealName());
            // 打表人职位
            List<SysPost> postions = sysPostMapper.findPositionByUserId(user.getId());
            String postStr = "";
            String postIdStr = "";
            for (SysPost post : postions) {
                postStr += post.getPostName() + ",";
                postIdStr += post.getId() + ",";
            }
            if (StringUtils.isNotBlank(postStr)) {
                postStr = postStr.substring(0, postStr.length() - 1);
            }
            if (StringUtils.isNotBlank(postIdStr)) {
                postIdStr = postIdStr.substring(0, postIdStr.length() - 1);
            }
            report.setReporterPositionId(postIdStr);
            report.setReporterPosition(postStr);
            report.setFormResultId(result.getId());
            report.setFormType(FormType.RVR_TYPE.getCode().byteValue());
            tReportMapper.saveReport(report);

            // 查询报告抄送部门
            TFormMain form = tFormMainMapper.selectByPrimaryKey(reportIn.getChecklistID());
            if(StringUtils.isNotBlank(form.getCcDptIds())) {
                String[] ccDptId = form.getCcDptIds().split(",");
                Example dptUserExp = new Example(SysUser.class);
                dptUserExp.createCriteria().andIn("dptId", Arrays.asList(ccDptId)).andEqualTo("status", 0).andEqualTo("isDel", false);
                List<SysUser> ccUsers = sysUserMapper.selectByExample(dptUserExp);
                tReportUserMapper.addReportToUser(ccUsers, report.getId());
            }

            // 整理关联的机会点，并存入报告与机会点关联表
            List<TFormOpptValue> tFormOpptValues = tFormOpptValueMapper.findOpptsByResultId(result.getId());
            List<TReportOppt> tReportOppts = new ArrayList<>();
            for(TFormOpptValue tFormOpptValue : tFormOpptValues) {
                TReportOppt tReportOppt = new TReportOppt();
                tReportOppt.setOpptId(tFormOpptValue.getOpptId());
                tReportOppt.setOpptName(tFormOpptValue.getOpptName());
                tReportOppt.setOpptDesc(tFormOpptValue.getOpptDesc());
                tReportOppts.add(tReportOppt);
            }
            if(CollectionUtils.isNotEmpty(tReportOppts)) {
                tReportOpptMapper.addReportOpptRelation(tReportOppts, report.getId(), new Date());
            }

            // 更新安排与表单完成状态
            tFrontPlanFormMapper.updateStatusByFormAndArrange(1, reportIn.getPatrolShopArrangeID(), reportIn.getChecklistID(), report.getId());

            //更新巡店安排状态
            Example tPlanFormExp = new Example(TFrontPlanForm.class);
            tPlanFormExp.createCriteria().andEqualTo("frontPlanId", reportIn.getPatrolShopArrangeID()).andEqualTo("isDel", false)
                .andEqualTo("reportStatus", 2);
            List<TFrontPlanForm> tFrontPlanForms = tFrontPlanFormMapper.selectByExample(tPlanFormExp);
            if(CollectionUtils.isEmpty(tFrontPlanForms)) {
                tFrontPlanMapper.updateFrontPlanStatus(ShopArrangeStatus.FINISH_CHECK.getCode(), reportIn.getPatrolShopArrangeID());

            }
            return report.getId();

        }catch (Exception e){
            logger.error("保存报告错误！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("保存报告错误！");
        }

    }

    private TFormCheckResult findFormResult(Long arrangeId, Long formId) {
        TFormCheckResult tFormCheckResult = new TFormCheckResult();
        tFormCheckResult.setFormMainId(formId);
        tFormCheckResult.setFrontPlanId(arrangeId);
        TFormCheckResult result = tFormCheckResultMapper.selectOne(tFormCheckResult);
        return result;
    }

    @Transactional
    public ReportVo findReportInfoById(SysUser user, GetReportIn reportIn) {
        ReportVo reportVo = new ReportVo();

        Long reportId = reportIn.getReportID();
        TReport report = tReportMapper.selectByPrimaryKey(reportId);
        if(report == null) {
            throw new BaseException("报告不存在！");
        }
        reportVo.setReportID(reportId);
        reportVo.setShopID(report.getShopId());
        reportVo.setShopName(report.getShopName());
        reportVo.setShopownerName(report.getShopkeeperName());
        reportVo.setInspectorName(report.getReporterName());
        reportVo.setInspectorPosition(report.getReporterPosition());
        reportVo.setCheckDepartmentName(report.getReporterDptName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        reportVo.setCheckDate(sdf.format(report.getCheckFormTime()));
        TFormCheckResult result = tFormCheckResultMapper.selectByPrimaryKey(report.getFormResultId());
        reportVo.setPatrolShopArrangeID(result.getFrontPlanId());
        reportVo.setRealTotal(result.getActualTotal());
        reportVo.setRealScore(result.getScore());
        reportVo.setScoreSummaryImg(result.getSummaryImg());
        reportVo.setSummary(result.getSummary());
        // 获取额外项
        List<AdditionItemVo> additionItemVos = new ArrayList<>();
        Example formAddItemValExp = new Example(TFormAddItemValue.class);
        formAddItemValExp.createCriteria().andEqualTo("formResultId", report.getFormResultId());
        List<TFormAddItemValue> tFormAddItemValues = tFormAddItemValueMapper.selectByExample(formAddItemValExp);
        for (TFormAddItemValue tFormAddItemValue : tFormAddItemValues) {
            AdditionItemVo additionItemVo = new AdditionItemVo();
            additionItemVo.setItemID(tFormAddItemValue.getFormAdditionItemId());
            additionItemVo.setName(tFormAddItemValue.getFormAdditionItemName());
            additionItemVo.setValue(tFormAddItemValue.getValue());
            additionItemVos.add(additionItemVo);
        }
        reportVo.setAdditionArray(additionItemVos);
        // 备注信息
        List<FormRulesVo> formRulesVos = new ArrayList<>();
        FormRulesVo formRulesVo = new FormRulesVo();
        formRulesVo.setItemID(1);
        formRulesVo.setName("importItem");
        formRulesVo.setValue("本检查表共出现" + result.getImptItemDenyNum() + "次重点检查项失分");
        formRulesVos.add(formRulesVo);
        FormRulesVo formNa = new FormRulesVo();
        formNa.setItemID(2);
        formNa.setName("N/A");
        formNa.setValue("本检查表共有" + result.getNaNum() + "个N/A项");
        formRulesVos.add(formNa);
        reportVo.setRulesArray(formRulesVos);
        // 统计触发的机会点
        Example reportOpptExp = new Example(TReportOppt.class);
        reportOpptExp.createCriteria().andEqualTo("reportId", reportId);
        List<TReportOppt> tReportOppts = tReportOpptMapper.selectByExample(reportOpptExp);
        reportVo.setChancePointNum(tReportOppts.size() != 0 ? String.valueOf(tReportOppts.size()) : "0");
        List<ChancePointVo> chancePointVos = new ArrayList<>();
        for (TReportOppt tReportOppt : tReportOppts) {
            ChancePointVo chancePointVo = new ChancePointVo();
            chancePointVo.setChancePointID(tReportOppt.getOpptId());
            chancePointVo.setChancePointTitle(tReportOppt.getOpptName());
            // 计算该机会点使用情况
            List<TFormOpptValue> opptListUseInCertainShopAndForm = opptListInShopAndForm(result.getShopId(), result.getFormMainId(), tReportOppt.getOpptId());
            chancePointVo.setChancePointFrequency(opptListUseInCertainShopAndForm == null ? 0 : opptListUseInCertainShopAndForm.size());
            // 查询该机会点关联的题项信息
            TFormPbmVal pbmValForThisOppt = null;
            TFormSubPbmVal subPbmValForThisOppt = null;
            Example formOpptValExp = new Example(TFormOpptValue.class);
            formOpptValExp.createCriteria().andEqualTo("opptId", tReportOppt.getOpptId()).andEqualTo("isPick", true)
                .andEqualTo("formResultId", result.getId());
            List<TFormOpptValue> certainFormOpptValues = tFormOpptValueMapper.selectByExample(formOpptValExp);
            TFormOpptValue tFormOpptValue = certainFormOpptValues.get(0);
            if (ProblemType.NOT_SAMPLE.getCode().equals(tFormOpptValue.getProblemType().intValue())) {
                pbmValForThisOppt = tFormPbmValMapper.selectByPrimaryKey(tFormOpptValue.getProblemValueId());
                if (StringUtils.isNotBlank(pbmValForThisOppt.getDescImgIds())) {
                    List<TFile> descImgs = tFileMapper.selectByIds(pbmValForThisOppt.getDescImgIds());
                    List<ProblemImg> problemImgs = new ArrayList<>();
                    for (TFile file : descImgs) {
                        ProblemImg problemImg = new ProblemImg();
                        problemImg.setImgUrl(file.getImgUrl());
                        problemImgs.add(problemImg);
                    }
                    chancePointVo.setDescImgArr(problemImgs);
                }
            } else if (ProblemType.SAMPLING.getCode().equals(tFormOpptValue.getProblemType().intValue())) {
                subPbmValForThisOppt = tFormSubPbmValMapper.selectByPrimaryKey(tFormOpptValue.getSubProblemValueId());
                pbmValForThisOppt = tFormPbmValMapper.selectByPrimaryKey(subPbmValForThisOppt.getProblemValueId());
            }
            // 查询所属题项类型
            TFormValue formValue = tFormValueMapper.selectByPrimaryKey(pbmValForThisOppt.getFormValueId());
            StringBuilder pbmCascadingRelation = new StringBuilder();
            if (subPbmValForThisOppt == null) {
                pbmCascadingRelation.append(formValue.getFormTypeName()).append("#").append(pbmValForThisOppt.getProblemName());
            } else {
                pbmCascadingRelation.append(formValue.getFormTypeName()).append("#").append(pbmValForThisOppt.getProblemName())
                        .append("#").append(subPbmValForThisOppt.getSubProblemName());
            }
            chancePointVo.setChanceContent(pbmCascadingRelation.toString());
            //TOpportunity tOpportunity = tOpportunityMapper.selectByPrimaryKey(tFormOpptValue.getOpptId());
            //chancePointVo.setChanceDescription(tOpportunity.getDescription());
            chancePointVo.setChanceDescription(tReportOppt.getOpptDesc());
            List<ReportWorkVo> workArr = findImproveByOppt(tReportOppt.getOpptId(), report.getId());
            chancePointVo.setWorkArr(workArr);
            chancePointVos.add(chancePointVo);
        }
        reportVo.setChancePointArr(chancePointVos);
        // 统计模块情况
        Example formTypeValueExp = new Example(TFormValue.class);
        formTypeValueExp.createCriteria().andEqualTo("formResultId", result.getId());
        List<TFormValue> formTypeValues = tFormValueMapper.selectByExample(formTypeValueExp);
        List<ChecklistKindScoreVo> checklistKindScoreVos = new ArrayList<>();
        for (TFormValue tFormValue : formTypeValues) {
            ChecklistKindScoreVo checklistKindScoreVo = new ChecklistKindScoreVo();
            checklistKindScoreVo.setRealTotal(tFormValue.getTotal());
            checklistKindScoreVo.setRealScore(tFormValue.getScore());
            checklistKindScoreVo.setScoreRate(tFormValue.getScoreRate());
            checklistKindScoreVo.setKindID(tFormValue.getFromTypeId());
            checklistKindScoreVo.setKindName(tFormValue.getFormTypeName());
            checklistKindScoreVos.add(checklistKindScoreVo);
        }
        reportVo.setChecklistKindScoreArr(checklistKindScoreVos);
        // 更新报告已读状态
        tReportUserMapper.updateReadStatus(user.getId(), report.getId());

        return reportVo;
    }

    /**
     * 根据机会点查找改善任务
     * @param opptId
     * @param reportId
     * @return
     */
    private List<ReportWorkVo> findImproveByOppt(Long opptId, Long reportId) {

        List<TLoopWork> works = tLoopWorkMapper.findImproveTaskList(opptId, reportId);
        //List<TTask> tasks = tTaskMapper.findImproveTaskList(opptId, reportId);
        if(CollectionUtils.isEmpty(works)) {
            return null;
        }
        List<ReportWorkVo> reportWorkVos = new ArrayList<>();

        for(TLoopWork work : works) {
            int doneNum = 0;
            int passNum = 0;
            int allNum = 0;
            ReportWorkVo reportWork = new ReportWorkVo();
            reportWork.setExecutorID(work.getExcuteUserId());
            reportWork.setExecutorName(work.getExcuteUserName());
            reportWork.setExecutorHeadImgUrl(work.getExecuteUserHeadImgUrl());
            reportWork.setTaskTitle(work.getTask().getName());
            reportWork.setWorkType(work.getType());
            List<TLoopWork> userWorks = tLoopWorkMapper.findImproveTaskListByUser(opptId, reportId, work.getExcuteUserId());
            for(TLoopWork userwork : userWorks) {
                if (userwork.getWorkStatus() != null && userwork.getWorkStatus().equals(2)) {
                    doneNum++;
                    if (userwork.getWorkResult() != null && userwork.getWorkResult().equals(1)) {
                        passNum++;
                    }
                }
                allNum++;
            }
            reportWork.setAllNum(allNum);
            reportWork.setDoneNum(doneNum);
            float passRate = (float) passNum / allNum;
            reportWork.setPassRate(passRate);

            reportWorkVos.add(reportWork);
        }

        return reportWorkVos;
    }

    public GetOpptOut findOpptInfoById(SysUser user, GetOpptIn getOpptIn) {
        GetOpptOut getOpptOut = new GetOpptOut();
        List<ReportWorkVo> workArr = findImproveByOppt(getOpptIn.getChanceID(), getOpptIn.getReportID());
        getOpptOut.setWorkArr(workArr);
        return getOpptOut;
    }

    public AbilityReportVo findAbilityReportInfo(SysUser user, GetReportIn reportIn) {
        try {
            AbilityReportVo reportVo = new AbilityReportVo();

            Long reportId = reportIn.getReportID();
            TReport report = tReportMapper.selectByPrimaryKey(reportId);
            if (report == null) {
                throw new BaseException("报告不存在！");
            }
            reportVo.setShopName(report.getShopName());
            reportVo.setShopownerName(report.getShopkeeperName());
            reportVo.setInspectorName(report.getReporterName());
            reportVo.setInspectorPosition(report.getReporterPosition());
            reportVo.setCheckDepartmentName(report.getReporterDptName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            reportVo.setCheckDate(sdf.format(report.getCheckFormTime()));
            TFormCheckResult result = tFormCheckResultMapper.selectByPrimaryKey(report.getFormResultId());
            reportVo.setResultName(result.getSummary());

            Example subResultExp = new Example(TFormCheckResult.class);
            subResultExp.createCriteria().andEqualTo("parentResultId", result.getId());
            List<TFormCheckResult> topSubFormResults = tFormCheckResultMapper.selectByExample(subResultExp);
            List<AbilitySubForm> resultSubForm = new ArrayList<>();
            List<Long> subResultIds = new ArrayList<>();
            int formCnt = 0;
            for(TFormCheckResult subResult : topSubFormResults) {
                formCnt++;
                // 只筛选经过打分且结果可用的子表结果
                if(subResult.getIsDone().equals(true) && subResult.getResultCanUse().equals(true) && (subResult.getIsPass().equals(false) || formCnt == 4)) {
                    subResultIds.add(subResult.getId());
                    AbilitySubForm subForm = new AbilitySubForm();
                    subForm.setSubFormID(subResult.getFormMainId());
                    subForm.setSubFormName(subResult.getFormMainName());
                    subForm.setIsPass(subResult.getIsPass());
                    subForm.setIsUsable(subResult.getResultCanUse());
                    subForm.setIsDone(subResult.getIsDone());
                    List<TFormValue> formTypeValues = tFormValueMapper.findTypeTreeValueListUntilSubPbm(subResult.getId());
                    if(CollectionUtils.isNotEmpty(formTypeValues)) {
                        List<AbilitySubFormKind> subFormKinds = new ArrayList<>();
                        for(TFormValue formtypeVal : formTypeValues) {
                            AbilitySubFormKind subFormKind = new AbilitySubFormKind();
                            subFormKind.setKindName(formtypeVal.getFormTypeName());
                            subFormKind.setKindID(formtypeVal.getFromTypeId());
                            subFormKind.setIsDone(formtypeVal.getIsDone());
                            subFormKind.setIsPass(formtypeVal.getIsPass());
                            List<TFormPbmVal> formPbmVals = formtypeVal.getFormPbmValList();
                            if(CollectionUtils.isNotEmpty(formPbmVals)) {
                                List<ProblemVo> problemArray = new ArrayList<>();
                                for(TFormPbmVal pbmVal : formPbmVals) {
                                    // 只需要未通过的题项
                                    if(Boolean.TRUE.equals(pbmVal.getIsScore()) && Boolean.FALSE.equals(pbmVal.getIsPass()) && Boolean.FALSE.equals(pbmVal.getIsNa())) {
                                        ProblemVo problemVo = new ProblemVo();
                                        problemVo.setProblemID(pbmVal.getFormProblemId());
                                        problemVo.setProblemName(pbmVal.getProblemName());
                                        problemVo.setIsPass(pbmVal.getIsPass());
                                        problemVo.setIsDone(pbmVal.getIsScore());
                                        problemVo.setCheckMode(pbmVal.getCheckMethod());
                                        problemVo.setNotes(pbmVal.getNotes());
                                        problemVo.setProblemDescription(pbmVal.getProblemDescription());
                                        if (StringUtils.isNotBlank(pbmVal.getDescImgIds())) {
                                            List<TFile> descImgs = tFileMapper.selectByIds(pbmVal.getDescImgIds());
                                            List<ProblemImg> problemImgs = new ArrayList<>();
                                            for (TFile file : descImgs) {
                                                ProblemImg problemImg = new ProblemImg();
                                                problemImg.setImgUrl(file.getImgUrl());
                                                problemImgs.add(problemImg);
                                            }
                                            problemVo.setDescImgArr(problemImgs);
                                        }

                                        List<TFormSubPbmVal> subPbmVals = pbmVal.getFormSubPbmValList();
                                        if (CollectionUtils.isNotEmpty(subPbmVals)) {
                                            problemVo.setIsSubProblem(true);
                                            List<SubProblemVo> subProblemVos = new ArrayList<>();
                                            for (TFormSubPbmVal subPbmVal : subPbmVals) {
                                                // 只读取未通过的子题项
                                                if(Boolean.TRUE.equals(subPbmVal.getIsScore()) && Boolean.FALSE.equals(subPbmVal.getIsPass()) && Boolean.FALSE.equals(subPbmVal.getIsNa())) {
                                                    SubProblemVo subProblemVo = new SubProblemVo();
                                                    subProblemVo.setSubProblemID(subPbmVal.getSubProblemId());
                                                    subProblemVo.setProblemDescription(subPbmVal.getProblemDescription());
                                                    subProblemVo.setSubProblemName(subPbmVal.getSubProblemName());
                                                    subProblemVo.setIsPass(subPbmVal.getIsPass());
                                                    subProblemVo.setIsDone(subPbmVal.getIsScore());
                                                    subProblemVo.setCheckMode(subPbmVal.getCheckMethod());
                                                    subProblemVo.setNotes(subPbmVal.getNotes());
                                                    if (subPbmVal.getSubFormId() != null) {
                                                        subProblemVo.setIsSubList(true);
                                                        AbilitySubForm subFormReport = findSubAbilityReportInfo(subPbmVal.getSubFormId(), subResult.getId());
                                                        subProblemVo.setSubListObject(subFormReport);
                                                    } else {
                                                        subProblemVo.setIsSubList(false);
                                                    }
                                                    if (StringUtils.isNotBlank(subPbmVal.getDescImgIds())) {
                                                        List<TFile> descImgs = tFileMapper.selectByIds(subPbmVal.getDescImgIds());
                                                        List<ProblemImg> problemImgs = new ArrayList<>();
                                                        for (TFile file : descImgs) {
                                                            ProblemImg problemImg = new ProblemImg();
                                                            problemImg.setImgUrl(file.getImgUrl());
                                                            problemImgs.add(problemImg);
                                                        }
                                                        subProblemVo.setDescImgArr(problemImgs);
                                                    }
                                                    subProblemVos.add(subProblemVo);
                                                }
                                            }
                                            problemVo.setSubProblemArray(subProblemVos);
                                        } else {
                                            problemVo.setIsSubProblem(false);
                                        }
                                        if (pbmVal.getSubFormId() != null) {
                                            problemVo.setIsSubList(true);
                                            AbilitySubForm subFormReport = findSubAbilityReportInfo(pbmVal.getSubFormId(), subResult.getId());
                                            problemVo.setSubListObject(subFormReport);
                                        } else {
                                            problemVo.setIsSubList(false);
                                        }
                                        problemArray.add(problemVo);
                                    }
                                }
                                subFormKind.setProblemArray(problemArray);
                            }
                            subFormKinds.add(subFormKind);
                        }
                        subForm.setSubFormKindArray(subFormKinds);
                    }
                    resultSubForm.add(subForm);
                    break;
                }
            }
            reportVo.setResultKindArray(resultSubForm);
            // 查询关联的机会点信息
            Example reportOpptExp = new Example(TReportOppt.class);
            reportOpptExp.createCriteria().andEqualTo("reportId", reportId);
            List<TReportOppt> tReportOppts = tReportOpptMapper.selectByExample(reportOpptExp);
            List<ChancePointVo> chancePointVos = new ArrayList<>();
            for (TReportOppt tReportOppt : tReportOppts) {
                ChancePointVo chancePointVo = new ChancePointVo();
                chancePointVo.setChancePointID(tReportOppt.getOpptId());
                chancePointVo.setChancePointTitle(tReportOppt.getOpptName());
                // 计算该机会点使用情况
                List<TFormOpptValue> opptListUseInCertainShopAndForm = opptListInShopAndForm(result.getShopId(), result.getFormMainId(), tReportOppt.getOpptId());
                chancePointVo.setChancePointFrequency(opptListUseInCertainShopAndForm == null ? 0 : opptListUseInCertainShopAndForm.size());
                // 查询该机会点关联的题项信息
                TFormPbmVal pbmValForThisOppt = null;
                TFormSubPbmVal subPbmValForThisOppt = null;
                Example formOpptValExp = new Example(TFormOpptValue.class);
                formOpptValExp.createCriteria().andEqualTo("opptId", tReportOppt.getOpptId()).andEqualTo("isPick", true)
                        .andIn("formResultId", subResultIds);
                List<TFormOpptValue> certainFormOpptValues = tFormOpptValueMapper.selectByExample(formOpptValExp);
                TFormOpptValue tFormOpptValue = certainFormOpptValues.get(0);
                if (ProblemType.NOT_SAMPLE.getCode().equals(tFormOpptValue.getProblemType().intValue())) {
                    pbmValForThisOppt = tFormPbmValMapper.selectByPrimaryKey(tFormOpptValue.getProblemValueId());
                    if (StringUtils.isNotBlank(pbmValForThisOppt.getDescImgIds())) {
                        List<TFile> descImgs = tFileMapper.selectByIds(pbmValForThisOppt.getDescImgIds());
                        List<ProblemImg> problemImgs = new ArrayList<>();
                        for (TFile file : descImgs) {
                            ProblemImg problemImg = new ProblemImg();
                            problemImg.setImgUrl(file.getImgUrl());
                            problemImgs.add(problemImg);
                        }
                        chancePointVo.setDescImgArr(problemImgs);
                    }
                } else if (ProblemType.SAMPLING.getCode().equals(tFormOpptValue.getProblemType().intValue())) {
                    subPbmValForThisOppt = tFormSubPbmValMapper.selectByPrimaryKey(tFormOpptValue.getSubProblemValueId());
                    if (StringUtils.isNotBlank(subPbmValForThisOppt.getDescImgIds())) {
                        List<TFile> descImgs = tFileMapper.selectByIds(subPbmValForThisOppt.getDescImgIds());
                        List<ProblemImg> problemImgs = new ArrayList<>();
                        for (TFile file : descImgs) {
                            ProblemImg problemImg = new ProblemImg();
                            problemImg.setImgUrl(file.getImgUrl());
                            problemImgs.add(problemImg);
                        }
                        chancePointVo.setDescImgArr(problemImgs);
                    }
                    pbmValForThisOppt = tFormPbmValMapper.selectByPrimaryKey(subPbmValForThisOppt.getProblemValueId());
                }
                // 查询所属题项类型
                TFormValue formValue = tFormValueMapper.selectByPrimaryKey(pbmValForThisOppt.getFormValueId());
                StringBuilder pbmCascadingRelation = new StringBuilder();
                if (subPbmValForThisOppt == null) {
                    pbmCascadingRelation.append(formValue.getFormTypeName()).append("#").append(pbmValForThisOppt.getProblemName());
                } else {
                    pbmCascadingRelation.append(formValue.getFormTypeName()).append("#").append(pbmValForThisOppt.getProblemName())
                            .append("#").append(subPbmValForThisOppt.getSubProblemName());
                }
                TFormCheckResult subCheckResult = tFormCheckResultMapper.selectByPrimaryKey(formValue.getFormResultId());
                if(subCheckResult.getParentResultId() != null) {
                    pbmCascadingRelation.insert(0, subCheckResult.getFormMainName() + "#");
                    Example subPbmValExp = new Example(TFormSubPbmVal.class);
                    Example pbmValExp = new Example(TFormPbmVal.class);
                    if(subCheckResult.getSubjectType() != null) {
                        if (subCheckResult.getSubjectType().equals(2)) {
                            subPbmValExp.createCriteria().andEqualTo("subFormId", subCheckResult.getFormMainId()).andEqualTo("formResultId", subCheckResult.getParentResultId());
                            subPbmValForThisOppt = tFormSubPbmValMapper.selectOneByExample(subPbmValExp);
                            chancePointVo.setChanceDescription(subPbmValForThisOppt.getProblemDescription());
                            pbmValForThisOppt = tFormPbmValMapper.selectByPrimaryKey(subPbmValForThisOppt.getProblemValueId());
                            pbmCascadingRelation.insert(0, pbmValForThisOppt.getProblemName() + "#" + subPbmValForThisOppt.getSubProblemName() + "#");
                        } else {
                            pbmValExp.createCriteria().andEqualTo("subFormId", subCheckResult.getFormMainId()).andEqualTo("formResultId", subCheckResult.getParentResultId());
                            pbmValForThisOppt = tFormPbmValMapper.selectByPrimaryKey(pbmValForThisOppt);
                            chancePointVo.setChanceDescription(pbmValForThisOppt.getProblemDescription());
                            pbmCascadingRelation.insert(0, pbmValForThisOppt.getProblemName() + "#");
                        }
                        formValue = tFormValueMapper.selectByPrimaryKey(pbmValForThisOppt.getFormValueId());
                        subCheckResult = tFormCheckResultMapper.selectByPrimaryKey(formValue.getFormResultId());
                        pbmCascadingRelation.insert(0, subCheckResult.getFormMainName() + "#" + formValue.getFormTypeName() + "#");
                    }
                }
                chancePointVo.setChanceContent(pbmCascadingRelation.toString());
                List<ReportWorkVo> workArr = findImproveByOppt(tReportOppt.getOpptId(), report.getId());
                chancePointVo.setWorkArr(workArr);
                chancePointVos.add(chancePointVo);
            }
            reportVo.setChancePointArr(chancePointVos);
            return reportVo;
        } catch (Exception e) {
            logger.error("读取能力模型报告错误！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("读取能力模型报告错误！");
        }
    }

    /**
     * 读取子表结果
     * @param subFormId
     * @param parentResultId
     * @return
     */
    private AbilitySubForm findSubAbilityReportInfo(Long subFormId, Long parentResultId) {
        TFormCheckResult param = new TFormCheckResult();
        param.setFormMainId(subFormId);
        param.setParentResultId(parentResultId);
        TFormCheckResult subResult = tFormCheckResultMapper.selectOne(param);
        AbilitySubForm subForm = null;
        // 只筛选经过打分且结果可用的子表结果
        if (subResult.getIsDone().equals(true)) {
            subForm = new AbilitySubForm();
            subForm.setSubFormID(subResult.getFormMainId());
            subForm.setSubFormName(subResult.getFormMainName());
            subForm.setIsPass(subResult.getIsPass());
            subForm.setIsUsable(subResult.getResultCanUse());
            subForm.setIsDone(subResult.getIsDone());
            List<TFormValue> formTypeValues = tFormValueMapper.findTypeTreeValueListUntilSubPbm(subResult.getId());
            if (CollectionUtils.isNotEmpty(formTypeValues)) {
                List<AbilitySubFormKind> subFormKinds = new ArrayList<>();
                for (TFormValue formtypeVal : formTypeValues) {
                    AbilitySubFormKind subFormKind = new AbilitySubFormKind();
                    subFormKind.setKindName(formtypeVal.getFormTypeName());
                    subFormKind.setKindID(formtypeVal.getFromTypeId());
                    subFormKind.setIsDone(formtypeVal.getIsDone());
                    subFormKind.setIsPass(formtypeVal.getIsPass());
                    List<TFormPbmVal> formPbmVals = formtypeVal.getFormPbmValList();
                    if (CollectionUtils.isNotEmpty(formPbmVals)) {
                        List<ProblemVo> problemArray = new ArrayList<>();
                        for (TFormPbmVal pbmVal : formPbmVals) {
                            // 只需要未通过的题项
                            if (Boolean.TRUE.equals(pbmVal.getIsScore()) && Boolean.FALSE.equals(pbmVal.getIsPass()) && Boolean.FALSE.equals(pbmVal.getIsNa())) {
                                ProblemVo problemVo = new ProblemVo();
                                problemVo.setProblemID(pbmVal.getFormProblemId());
                                problemVo.setProblemName(pbmVal.getProblemName());
                                problemVo.setIsPass(pbmVal.getIsPass());
                                problemVo.setIsDone(pbmVal.getIsScore());
                                problemVo.setCheckMode(pbmVal.getCheckMethod());
                                problemVo.setNotes(pbmVal.getNotes());
                                problemVo.setProblemDescription(pbmVal.getProblemDescription());
                                if (StringUtils.isNotBlank(pbmVal.getDescImgIds())) {
                                    List<TFile> descImgs = tFileMapper.selectByIds(pbmVal.getDescImgIds());
                                    List<ProblemImg> problemImgs = new ArrayList<>();
                                    for (TFile file : descImgs) {
                                        ProblemImg problemImg = new ProblemImg();
                                        problemImg.setImgUrl(file.getImgUrl());
                                        problemImgs.add(problemImg);
                                    }
                                    problemVo.setDescImgArr(problemImgs);
                                }

                                List<TFormSubPbmVal> subPbmVals = pbmVal.getFormSubPbmValList();
                                if (CollectionUtils.isNotEmpty(subPbmVals)) {
                                    problemVo.setIsSubProblem(true);
                                    List<SubProblemVo> subProblemVos = new ArrayList<>();
                                    for (TFormSubPbmVal subPbmVal : subPbmVals) {
                                        // 只读取未通过的子题项
                                        if (Boolean.TRUE.equals(subPbmVal.getIsScore()) && Boolean.FALSE.equals(subPbmVal.getIsPass()) && Boolean.FALSE.equals(subPbmVal.getIsNa())) {
                                            SubProblemVo subProblemVo = new SubProblemVo();
                                            subProblemVo.setSubProblemID(subPbmVal.getSubProblemId());
                                            subProblemVo.setProblemDescription(subPbmVal.getProblemDescription());
                                            subProblemVo.setSubProblemName(subPbmVal.getSubProblemName());
                                            subProblemVo.setIsPass(subPbmVal.getIsPass());
                                            subProblemVo.setIsDone(subPbmVal.getIsScore());
                                            subProblemVo.setCheckMode(subPbmVal.getCheckMethod());
                                            subProblemVo.setNotes(subPbmVal.getNotes());
                                            if (subPbmVal.getSubFormId() != null) {
                                                subProblemVo.setIsSubList(true);
                                            } else {
                                                subProblemVo.setIsSubList(false);
                                            }
                                            if (StringUtils.isNotBlank(subPbmVal.getDescImgIds())) {
                                                List<TFile> descImgs = tFileMapper.selectByIds(subPbmVal.getDescImgIds());
                                                List<ProblemImg> problemImgs = new ArrayList<>();
                                                for (TFile file : descImgs) {
                                                    ProblemImg problemImg = new ProblemImg();
                                                    problemImg.setImgUrl(file.getImgUrl());
                                                    problemImgs.add(problemImg);
                                                }
                                                subProblemVo.setDescImgArr(problemImgs);
                                            }
                                            subProblemVos.add(subProblemVo);
                                        }
                                    }
                                    problemVo.setSubProblemArray(subProblemVos);
                                } else {
                                    problemVo.setIsSubProblem(false);
                                }
                                if (pbmVal.getSubFormId() != null) {
                                    problemVo.setIsSubList(true);
                                } else {
                                    problemVo.setIsSubList(false);
                                }
                                problemArray.add(problemVo);
                            }
                        }
                        subFormKind.setProblemArray(problemArray);
                    }
                    subFormKinds.add(subFormKind);
                }
                subForm.setSubFormKindArray(subFormKinds);
            }
        }

        return subForm;
    }

}
