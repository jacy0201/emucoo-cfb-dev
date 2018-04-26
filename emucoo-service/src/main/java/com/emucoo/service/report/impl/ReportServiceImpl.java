package com.emucoo.service.report.impl;

import com.emucoo.common.exception.ApiException;
import com.emucoo.common.exception.ServiceException;
import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.modules.report.AdditionItemVo;
import com.emucoo.dto.modules.report.ChancePointVo;
import com.emucoo.dto.modules.report.ChecklistKindScoreVo;
import com.emucoo.dto.modules.report.FormRulesVo;
import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.ReportVo;
import com.emucoo.dto.modules.report.SaveReportIn;
import com.emucoo.enums.Constant;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

    public ReportVo getReport(SysUser user, GetReportIn reportIn) {
        ReportVo reportOut = new ReportVo();
        // 查询店铺名
        TShopInfo shop = tShopInfoMapper.selectByPrimaryKey(reportIn.getShopID());
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
            postStr += post.getPostName();
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
        List<TFormPbm> tFormPbms = tFormPbmMapper.findFormPbmsByFormMainId(reportIn.getChecklistID());
        List<Long> formImportPbmIds = new ArrayList<>();
        for(TFormPbm tFormPbm : tFormPbms) {
            // 重点项归类
            if(tFormPbm.getImportant()) {
                formImportPbmIds.add(tFormPbm.getId());
            }

        }
        int importNum = 0;
        if(formImportPbmIds.size() > 0) {
            // 查询重要项结果值列表
            List<TFormPbmVal> tFormImportPbmVals = tFormPbmValMapper.findImportPbmValsByFormAndArrange(reportIn.getChecklistID(),
                    reportIn.getPatrolShopArrangeID(), formImportPbmIds);
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
        }

        // 统计重点项得分为0的项
        /*formPbmValueExp.createCriteria().andIn("formProblemId", formPbmIds).andEqualTo("score", 0);
        List<TFormPbmVal> tFormPbmVals = tFormPbmValMapper.selectByExample(formPbmValueExp);*/
        List<FormRulesVo> formRulesVos = new ArrayList<>();
        FormRulesVo formRulesVo = new FormRulesVo();
        formRulesVo.setItemID(1);
        formRulesVo.setName("importItem");
        formRulesVo.setValue("本检查表共出现" + importNum  + "次重点检查项失分");
        formRulesVos.add(formRulesVo);
        // 统计N/A项

        /*Example formPbmValueExp = new Example(TFormPbmValMapper.class);
        formPbmValueExp.clear();
        formPbmValueExp.createCriteria().andIn("formProblemValueId", formAllPbmValueIds).andEqualTo("isNa", true);
        List<TFormPbmVal> naFormPbms = tFormPbmValMapper.selectByExample(formPbmValueExp);*/
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
            }
            actualScore += tFormPbmVal.getScore();
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

        /*Example formOpptValExp = new Example(TFormOpptValue.class);
        formOpptValExp.createCriteria().andIn("problemValueId", formAllPbmValueIds).orIn("subProblemValueId", formAllSubPbmValueIds)
                .andEqualTo("isPick", true);
        List<TFormOpptValue> tFormOpptValues = tFormOpptValueMapper.selectByExample(formOpptValExp);*/
            // 根据题项id或子题项id查询关联的机会点信息
            if(formAllSubPbmValueIds.size() > 0) {
                List<TFormOpptValue> tFormOpptValues = tFormOpptValueMapper.selectUnionFormOpptsByPbmIds(formAllPbmValueIds, formAllSubPbmValueIds);
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
                    } else if (ProblemType.SAMPLING.getCode().equals(tFormOpptValue.getProblemType().intValue())) {
                        subPbmValForThisOppt = tFormSubPbmValMapper.selectByPrimaryKey(tFormOpptValue.getSubProblemValueId());
                        pbmValForThisOppt = tFormPbmValMapper.selectByPrimaryKey(subPbmValForThisOppt.getProblemValueId());
                    }
                    // 查询所属题项类型
                    TFormValue formValue = tFormValueMapper.selectByPrimaryKey(pbmValForThisOppt.getFormValueId());
                    StringBuilder pbmCascadingRelation = new StringBuilder();
                    if (subPbmValForThisOppt == null) {
                        pbmCascadingRelation.append(formValue.getFormTypeName()).append("-").append(pbmValForThisOppt.getProblemName());
                    } else {
                        pbmCascadingRelation.append(formValue.getFormTypeName()).append("-").append(pbmValForThisOppt.getProblemName())
                                .append("-").append(subPbmValForThisOppt.getSubProblemName());
                    }
                    chancePointVo.setChanceContent(pbmCascadingRelation.toString());
                    TOpportunity tOpportunity = tOpportunityMapper.selectByPrimaryKey(tFormOpptValue.getOpptId());
                    chancePointVo.setChanceDescription(tOpportunity.getDescription());
                    chancePointVos.add(chancePointVo);
                }
                reportOut.setChancePointArr(chancePointVos);
            }

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
        float totalScoreRate = lastScore / realTotal * 100;
        // 计分项规则
        Example formScoreExp = new Example(TFormScoreItem.class);
        formScoreExp.createCriteria().andEqualTo("formMainId", reportIn.getChecklistID());
        formScoreExp.setOrderByClause("score_begin desc");
        List<TFormScoreItem> tFormScoreItems = tFormScoreItemMapper.selectByExample(formScoreExp);
        if(tFormScoreItems.size() > 0) {
            String summaryImgUrl = "";
            hasHit = false;
            for (TFormScoreItem tFormScoreItem : tFormScoreItems) {
                if (totalScoreRate > tFormScoreItem.getScoreBegin()) {
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
        Example formTypeValueExp = new Example(TFormValue.class);
        formTypeValueExp.createCriteria().andEqualTo("frontPlanId", reportIn.getPatrolShopArrangeID()).andEqualTo("formMainId", reportIn.getChecklistID());
        List<TFormValue> formTypeValues = tFormValueMapper.selectByExample(formTypeValueExp);
        List<ChecklistKindScoreVo> checklistKindScoreVos = new ArrayList<>();
        for(TFormValue tFormValue : formTypeValues) {
            ChecklistKindScoreVo checklistKindScoreVo = new ChecklistKindScoreVo();
            checklistKindScoreVo.setRealScore(tFormValue.getScore());
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
        Example arrangeExp = new Example(TFrontPlan.class);
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
            Example subPbmValExp = new Example(TFormSubPbmVal.class);
            subPbmValExp.createCriteria().andIn("problemValueId", formAllPbmValueIds);
            List<TFormSubPbmVal> tFormSubPbmVals = tFormSubPbmValMapper.selectByExample(subPbmValExp);
            List<Long> formAllSubPbmValueIds = new ArrayList<>();
            for (TFormSubPbmVal tFormSubPbmVal : tFormSubPbmVals) {
                formAllSubPbmValueIds.add(tFormSubPbmVal.getId());
            }
            List<TFormOpptValue> tFormOpptValues = tFormOpptValueMapper.selectUnionFormOpptsByPbmIdsAndOppt(formAllPbmValueIds, formAllSubPbmValueIds, opptId);
            return tFormOpptValues;
        } else {
            return null;
        }
    }

    @Transactional
    public void saveReport(SysUser user, ReportVo reportIn) {

        try{
            // 更新总结
            tFormCheckResultMapper.saveSummaryToFormResult(reportIn.getSummary(), reportIn.getPatrolShopArrangeID(),
                    reportIn.getChecklistID(), new Date());

            /*Example formResultExp = new Example(TFormCheckResult.class);
            formResultExp.createCriteria().andEqualTo("formMainId", reportIn.getChecklistID())
                    .andEqualTo("frontPlanId", reportIn.getPatrolShopArrangeID());*/
            TFormCheckResult result = findFormResult(reportIn.getPatrolShopArrangeID(), reportIn.getChecklistID());
            if (result == null) {
                throw new ApiException("打表结果不存在！");
            }
            List<TFormAddItemValue> tFormAddItemValues = new ArrayList<>();
            for (AdditionItemVo additionItemVo : reportIn.getAdditionArray()) {
                TFormAddItemValue formAddItemValue = new TFormAddItemValue();
                formAddItemValue.setId(additionItemVo.getItemID());
                formAddItemValue.setValue(additionItemVo.getValue());
                tFormAddItemValues.add(formAddItemValue);
            }
            // 添加额外项值
            tFormAddItemValueMapper.addAdditionItemList(tFormAddItemValues, new Date(), result.getId());

            // 保存报告
            TReport report = new TReport();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            report.setCheckFormTime(sdf.parse(reportIn.getCheckDate()));
            Date now = new Date();
            report.setCreateTime(now);
            report.setCreateUserId(user.getId());
            report.setOrgId(Constant.orgId);
            report.setReporterDptName(reportIn.getInspectorName());
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
                postStr += post.getPostName();
                postIdStr += post.getId();
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
            tReportMapper.saveReport(report);

            // 查询报告抄送部门
            TFormMain form = tFormMainMapper.selectByPrimaryKey(reportIn.getChecklistID());
            String[] ccDptId = form.getCcDptIds().split(",");
            Example dptUserExp = new Example(SysUser.class);
            dptUserExp.createCriteria().andIn("dptId", Arrays.asList(ccDptId)).andEqualTo("status", 0).andEqualTo("isDel", false);
            List<SysUser> ccUsers = sysUserMapper.selectByExample(dptUserExp);
            tReportUserMapper.addReportToUser(ccUsers, report.getId());

            // 整理关联的机会点，并存入报告与机会点关联表
            List<TFormOpptValue> tFormOpptValues = tFormOpptValueMapper.findOpptsByResultId(result.getId());
            List<Long> opptIds = new ArrayList<>();
            for(TFormOpptValue tFormOpptValue : tFormOpptValues) {
                opptIds.add(tFormOpptValue.getOpptId());
            }
            tReportOpptMapper.addReportOpptRelation(opptIds, report.getId(), new Date());

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

        }catch (Exception e){
            logger.error("保存报告错误！");
            if(StringUtils.isNotBlank(e.getMessage())) {
                try {
                    throw e;
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
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

}
