package com.emucoo.service.form.impl;

import com.emucoo.common.exception.ApiException;
import com.emucoo.common.exception.BaseException;
import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.modules.abilityForm.AbilityFormMain;
import com.emucoo.dto.modules.abilityForm.AbilitySubForm;
import com.emucoo.dto.modules.abilityForm.AbilitySubFormKind;
import com.emucoo.dto.modules.abilityForm.ProblemChanceVo;
import com.emucoo.dto.modules.abilityForm.ProblemImg;
import com.emucoo.dto.modules.abilityForm.ProblemVo;
import com.emucoo.dto.modules.abilityForm.SubProblemVo;
import com.emucoo.dto.modules.form.*;
import com.emucoo.dto.modules.shop.PlanArrangeGetFormIn;
import com.emucoo.enums.Constant;
import com.emucoo.enums.DeleteStatus;
import com.emucoo.enums.FormType;
import com.emucoo.enums.ImgSourceType;
import com.emucoo.enums.ShopArrangeStatus;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.form.FormService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sj on 2018/4/20.
 */
@Service
public class FormServiceImpl implements FormService {

    private Logger logger = LoggerFactory.getLogger(FormServiceImpl.class);
    @Autowired
    private  TFormMainMapper formMainMapper;

    @Autowired
    private TShopInfoMapper shopInfoMapper;

    @Autowired
    private TBrandInfoMapper brandInfoMapper;

    @Autowired
    private TFormTypeMapper formTypeMapper;

    @Autowired
    private TFormPbmMapper formPbmMapper;

    @Autowired
    private TOpportunityMapper opportunityMapper;

    @Autowired
    private TFormSubPbmHeaderMapper formSubPbmHeaderMapper;

    @Autowired
    private TFormSubPbmMapper formSubPbmMapper;

    @Autowired
    private TFormPbmValMapper formPbmValMapper;

    @Autowired
    private TFormValueMapper formValueMapper;

    @Autowired
    private TFormOpptValueMapper formOpptValueMapper;

    @Autowired
    private TFormSubPbmValMapper formSubPbmValMapper;

    @Autowired
    private TFormCheckResultMapper formCheckResultMapper;

    @Autowired
    private SysPostMapper sysPostMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TFormOpptMapper formOpptMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private TReportMapper reportMapper;

    @Autowired
    private TReportUserMapper reportUserMapper;

    @Autowired
    private TReportOpptMapper reportOpptMapper;

    @Autowired
    private TFrontPlanFormMapper frontPlanFormMapper;

    @Autowired
    private TFrontPlanMapper frontPlanMapper;

    @Autowired
    private TFileMapper fileMapper;

    @Autowired
    private TPlanFormRelationMapper planFormRelationMapper;

    @Autowired
    private TLoopSubPlanMapper loopSubPlanMapper;

    public List<TFormMain> listForm(PlanArrangeGetFormIn getFormIn) {

        try {
            TLoopSubPlan subPlan = loopSubPlanMapper.findSubPlanByArrangeId(getFormIn.getPatrolShopArrangeID());
            if (subPlan == null) {
                throw new BaseException("计划数据异常！");
            }
            List<TFormMain> tFormMains = formMainMapper.findAvailableFormsUseForPlan(subPlan.getPlanId());
            /*Example example = new Example(TFormMain.class);
            example.createCriteria().andEqualTo("isDel", false).andEqualTo("isUse", true);
            List<TFormMain> tFormMains = formMainMapper.selectByExample(example);*/
            return tFormMains;
        } catch (Exception e) {
            logger.error("读取检查表列表失败！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("读取检查表列表失败！");
        }

    }

    @Override
    public TShopInfo findShopInfo(FormIn formIn) {
        return shopInfoMapper.selectByPrimaryKey(formIn.getShopID());
    }

    @Override
    public TBrandInfo findShopBrand(Long brandId) {
        return brandInfoMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public FormOut checkoutFormInfo(SysUser user, Long formId) {

        FormOut formOut = new FormOut();
        TFormMain formMain = formMainMapper.fetchOneById(formId);
        if(formMain == null) {
            return null;
        }
        if(!formMain.getIsUse() || formMain.getIsDel()) {
            return null;
        }

        formOut.setFormId(formMain.getId());
        formOut.setFormName(formMain.getName());
        List<FormKindVo> formKindVos = new ArrayList<>();
        formOut.setKindArray(formKindVos);

        // 循环里面查数据库是比较弱智的方法，现在先这样吧，有时间再改。
        List<TFormType> modules = formTypeMapper.findFormTypesByFormMainId(formMain.getId());
        for(TFormType module : modules) {
            FormKindVo formKindVo = new FormKindVo();
            formKindVo.setIsDone(false);
            formKindVo.setKindID(module.getId());
            formKindVo.setKindName(module.getTypeName());

            List<FormProblemVo> problemVos = new ArrayList<>();

            List<TFormPbm> pbms = formPbmMapper.findFormPbmsByFormTypeId(module.getId());
            for(TFormPbm pbm : pbms) {
//                TFormPbmVal pbmVal = formPbmValMapper.fetchOnePbmValue(fv.getId(), pbm.getId());

                FormProblemVo pbmVo = new FormProblemVo();
                pbmVo.setProblemID(pbm.getId());
                pbmVo.setProblemName(pbm.getName());
                pbmVo.setProblemDescription(pbm.getDescriptionHit());
                pbmVo.setIsImportant(pbm.getIsImportant());
                pbmVo.setIsNA(false);
                pbmVo.setIsScore(false);
                pbmVo.setProblemScore(pbm.getScore());
                pbmVo.setProblemTotal(pbm.getScore());
                pbmVo.setProblemType(pbm.getProblemSchemaType());

                if(pbm.getProblemSchemaType() == 2) {
                    List<TFormSubPbmHeader> subPbmHeaders = formSubPbmHeaderMapper.findFormSubPbmHeadersByFormPbmId(pbm.getId());
                    List<FormSubProblemUnitVo> subProblemUnitVos = new ArrayList<>();
                    for(TFormSubPbmHeader subPbmHeader : subPbmHeaders){
                        FormSubProblemUnitVo formSubProblemUnitVo = new FormSubProblemUnitVo();
                        formSubProblemUnitVo.setSubProblemUnitID(subPbmHeader.getId());
                        formSubProblemUnitVo.setSubProblemUnitName(subPbmHeader.getName());
                        subProblemUnitVos.add(formSubProblemUnitVo);
                    }

                    List<TFormSubPbm> subPbms = formSubPbmMapper.findFormSubPbmsByFormPbmId(pbm.getId());
                    List<FormSubProblemVo> subProblemVos = new ArrayList<>();
                    for(TFormSubPbm subPbm : subPbms) {
//                        TFormSubPbmVal subPbmVal = formSubPbmValMapper.fetchOneSubPbmValue(pbmVal.getId(), subPbm.getId());
                        FormSubProblemVo subProblemVo = new FormSubProblemVo();
                        subProblemVo.setSubProblemID(subPbm.getId());
                        subProblemVo.setSubProblemName(subPbm.getSubProblemName());
                        subProblemVo.setSubProblemScore(subPbm.getSubProblemScore() * subProblemUnitVos.size());
                        subProblemVo.setSubProblemTotal(subPbm.getSubProblemScore() * subProblemUnitVos.size());

                        List<FormChanceVo> subProblemChanceVos = new ArrayList<>();
                        subProblemVo.setSubProblemChanceArray(subProblemChanceVos);

                        if(subPbm.getOpportunity() != null){
                            for(TFormSubPbmHeader subPbmHeader : subPbmHeaders){
                                FormChanceVo subProblemChanceVo = new FormChanceVo();
                                subProblemChanceVo.setChanceID(subPbm.getOpportunity().getId());
                                subProblemChanceVo.setChanceName(subPbm.getOpportunity().getName());
//                                TFormOpptValue opptVal = formOpptValueMapper.fetchOneSubPbmOpptValue(subPbmVal.getId(), subPbm.getId(), subPbmHeader.getId(), 2);
                                subProblemChanceVo.setIsPick(false);
                                subProblemChanceVos.add(subProblemChanceVo);
                            }
                        }
                        subProblemVos.add(subProblemVo);
                    }

                    pbmVo.setSubProblemArray(subProblemVos);
                    pbmVo.setSubProblemUnitArray(subProblemUnitVos);
                    pbmVo.setProblemScore(subProblemVos.stream().mapToInt(p -> p.getSubProblemScore()).sum());
                    pbmVo.setProblemTotal(pbmVo.getProblemScore());
                } else {
                    List<TOpportunity> oppts = opportunityMapper.findOpptsByPbmId(pbm.getId());
                    List<FormChanceVo> formChanceVos = new ArrayList<>();
                    if(oppts != null && oppts.size() > 0) {
                        for(TOpportunity oppt : oppts) {
                            // 前端创建的机会点，只有在创建它的用户再次使用该表单打表的时候才会显示出来，其他用户，其他表单都需要过滤掉。
                            if(oppt.getCreateType() == 2 && oppt.getCreateUserId() != user.getId())
                                continue;
//                            TFormOpptValue opptValue = formOpptValueMapper.fetchOnePbmOpptValue(pbmVal.getId(), oppt.getId(), 1);
                            FormChanceVo formChanceVo = new FormChanceVo();
                            formChanceVo.setChanceID(oppt.getId());
                            formChanceVo.setChanceName(oppt.getName());
                            formChanceVo.setIsPick(false);
                            formChanceVos.add(formChanceVo);
                        }
                    }
                    pbmVo.setChanceArray(formChanceVos);
                    pbmVo.setOtherChanceArray(new ArrayList<>());
                }
                problemVos.add(pbmVo);
            }

            formKindVo.setProblemNum(problemVos.size());
            formKindVo.setProblemArray(problemVos);
            formKindVo.setScoreRate(0.0f);
            formKindVo.setWrongNum(0);

            formKindVos.add(formKindVo);
        }
        return formOut;
    }

    private void cleanOldValueByCheckResultId(SysUser user, FormIn formIn) {
        Long frontPlanId = formIn.getPatrolShopArrangeID();
        Long formMainId = formIn.getChecklistID();
        Long shopId = formIn.getShopID();
        Long userId = user.getId();

        Example example = new Example(TFormCheckResult.class);
        example.createCriteria().andEqualTo("formMainId", formMainId)
                .andEqualTo("frontPlanId", frontPlanId)
                .andEqualTo("shopId", shopId)
                .andEqualTo("createUserId", userId);
        List<TFormCheckResult> results = formCheckResultMapper.selectByExample(example);
        if(results != null && results.size() > 0) {
            results.forEach(result -> {
                // 要先删除关系，再删除机会点，这里删的是模版里的关系和机会点数据，因为是app创建的，所以每次打表保存都要删。
                formOpptMapper.cleanFormOpptRelationByResultId(result.getId());
                opportunityMapper.cleanOpptsByResultId(result.getId());

                // 这里删除的是value表里的数据，模版结构不需要变，所以不用删，删的是本次打表的得分数据。
                formCheckResultMapper.deleteByPrimaryKey(result.getId());
                formValueMapper.cleanByResultId(result.getId());
                fileMapper.cleanFileById(result.getId());
                formPbmValMapper.cleanByResultId(result.getId());
                formSubPbmValMapper.cleanByResultId(result.getId());
                formOpptValueMapper.cleanByResultId(result.getId());

            });
        }
    }

    @Override
    public boolean checkinFormResult(SysUser user, FormIn formIn) {
        boolean checkinWithAppCreatedOppts = false;
        // 每次存之前把旧数据删掉
        cleanOldValueByCheckResultId(user, formIn);

        Long frontPlanId = formIn.getPatrolShopArrangeID();
        Long formMainId = formIn.getChecklistID();
        Long shopId = formIn.getShopID();

        TFormMain formMain = formMainMapper.fetchOneById(formMainId);
        TShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(shopId);
        List<SysPost> positions = sysPostMapper.findPositionByUserId(user.getId());
        List<String> pstns = positions.stream().map(p -> p.getPostName()).collect(Collectors.toList());
        String opptDescription = "前端创建-" + formMain.getName() + "-" + user.getDptName() + "-" + StringUtils.join(pstns, "|") + "-" +user.getRealName();

        TFormCheckResult formCheckResult = new TFormCheckResult();
        formCheckResult.setCreateTime(DateUtil.currentDate());
        formCheckResult.setModifyTime(DateUtil.currentDate());
        formCheckResult.setCreateUserId(user.getId());
        formCheckResult.setFormMainId(formMainId);
        formCheckResult.setFormMainName(formMain.getName());
        formCheckResult.setFrontPlanId(frontPlanId);
        formCheckResult.setShopId(shopId);
        formCheckResult.setShopName(shopInfo.getShopName());

        formCheckResultMapper.insert(formCheckResult);

        int score = 0;
        int total = 0;
        List<FormKindVo> modules = formIn.getKindArray();
        for(FormKindVo module : modules) {

            TFormValue formValue = new TFormValue();
            formValue.setCreateTime(DateUtil.currentDate());
            formValue.setFormResultId(formCheckResult.getId());
            formValue.setFormTypeName(module.getKindName());
            formValue.setFromTypeId(module.getKindID());
            formValue.setIsDone(module.getIsDone());
            formValue.setModifyTime(DateUtil.currentDate());
            score += module.getRealScore();
            total += module.getRealTotal();
            formValue.setScore(module.getRealScore());
            formValue.setTotal(module.getRealTotal());
            formValue.setScoreRate(module.getScoreRate());
            formValueMapper.insert(formValue);

            List<FormProblemVo> problemVos = module.getProblemArray();
            for(FormProblemVo problemVo : problemVos) {
                TFormPbmVal formPbmVal = new TFormPbmVal();
                formPbmVal.setCreateTime(DateUtil.currentDate());
                formPbmVal.setModifyTime(DateUtil.currentDate());
                formPbmVal.setFormProblemId(problemVo.getProblemID());
                formPbmVal.setFormResultId(formCheckResult.getId());
                formPbmVal.setFormValueId(formValue.getId());
                formPbmVal.setIsNa(problemVo.getIsNA());
                formPbmVal.setIsScore(problemVo.getIsScore());
                formPbmVal.setIsImportant(problemVo.getIsImportant());
                formPbmVal.setProblemDescription(problemVo.getProblemDescription());
                formPbmVal.setProblemName(problemVo.getProblemName());
                formPbmVal.setProblemSchemaType(problemVo.getProblemType());
                formPbmVal.setScore(problemVo.getProblemScore());
                // 保存题项照片数据
                String imgIds = "";
                List<ProblemImg> descImgArr = problemVo.getDescImgArr();
                if (CollectionUtils.isNotEmpty(descImgArr)) {
                    List<TFile> imgs = new ArrayList<>();
                    for (ProblemImg problemImg : descImgArr) {
                        TFile descImg = new TFile();
                        descImg.setImgUrl(problemImg.getImgUrl());
                        descImg.setCreateTime(DateUtil.currentDate());
                        descImg.setModifyTime(DateUtil.currentDate());
                        descImg.setIsDel(DeleteStatus.COMMON.getCode());
                        descImg.setCreateUserId(user.getId());
                        descImg.setSource(ImgSourceType.FROM_FRONT.getCode().byteValue());
                        imgs.add(descImg);
                    }
                    fileMapper.insertList(imgs);

                    for (TFile file : imgs) {
                        imgIds += file.getId() + ",";
                    }
                    if (StringUtil.isNotBlank(imgIds)) {
                        imgIds = imgIds.substring(0, imgIds.length() - 1);
                    }
                }
                formPbmVal.setDescImgIds(imgIds);

                formPbmValMapper.insert(formPbmVal);

                List<FormChanceVo> chanceVos = problemVo.getChanceArray();
                if(chanceVos != null && chanceVos.size()>0) {
                    List<TFormOpptValue> opptVals = new ArrayList<>();
                    for (FormChanceVo formChanceVo : chanceVos) {
                        TFormOpptValue formOpptValue = new TFormOpptValue();
                        formOpptValue.setCreateTime(DateUtil.currentDate());
                        formOpptValue.setModifyTime(DateUtil.currentDate());
                        formOpptValue.setFormResultId(formCheckResult.getId());
                        formOpptValue.setIsPick(formChanceVo.getIsPick());
                        formOpptValue.setOpptId(formChanceVo.getChanceID());
                        formOpptValue.setOpptName(formChanceVo.getChanceName());
                        TOpportunity tOpportunity = opportunityMapper.selectByPrimaryKey(formChanceVo.getChanceID());
                        if(tOpportunity != null) {
                            formOpptValue.setOpptDesc(tOpportunity.getDescription());
                        }

                        formOpptValue.setProblemId(problemVo.getProblemID());
                        formOpptValue.setProblemType(problemVo.getProblemType().byteValue());
                        formOpptValue.setProblemValueId(formPbmVal.getId());

                        opptVals.add(formOpptValue);
                    }
                    formOpptValueMapper.insertList(opptVals);
                }

                List<FormChanceVo> otherChanceVos = problemVo.getOtherChanceArray();
                if(otherChanceVos != null && otherChanceVos.size() > 0) {
                    checkinWithAppCreatedOppts = true;
                    for (FormChanceVo fcv : otherChanceVos) {
                        // 这里的机会点都是前端创建的，所以要先把机会点创建进数据库。为了机会点id
                        TOpportunity opportunity = new TOpportunity();
                        opportunity.setName(fcv.getChanceName());
                        opportunity.setDescription(opptDescription);
                        opportunity.setIsUse(true);
                        opportunity.setIsDel(false);
                        opportunity.setFrontCanCreate(true);
                        opportunity.setType(0);
                        opportunity.setCreateType(2);
                        opportunity.setCreateTime(DateUtil.currentDate());
                        opportunity.setCreateUserId(user.getId());
                        opportunity.setModifyTime(DateUtil.currentDate());
                        opportunity.setModifyUserId(user.getId());
                        opportunityMapper.insert(opportunity);

                        // 把机会点和题目的关系保存起来
                        TFormOppt formOppt = new TFormOppt();
                        formOppt.setOpptId(opportunity.getId());
                        formOppt.setProblemId(problemVo.getProblemID());
                        formOppt.setProblemType(problemVo.getProblemType());
                        formOppt.setCreateTime(DateUtil.currentDate());
                        formOppt.setModifyTime(DateUtil.currentDate());
                        formOpptMapper.insert(formOppt);

                        TFormOpptValue formOpptValue = new TFormOpptValue();
//                    formOpptValue.setSubProblemValueId();
//                    formOpptValue.setSubProblemUnitScore();
//                    formOpptValue.setSubProblemId();
//                    formOpptValue.setSubHeaderId();
                        formOpptValue.setProblemValueId(formPbmVal.getId());
                        formOpptValue.setProblemType(problemVo.getProblemType().byteValue());
                        formOpptValue.setProblemId(problemVo.getProblemID());
                        formOpptValue.setOpptName(fcv.getChanceName());
                        formOpptValue.setOpptDesc(opptDescription);
                        formOpptValue.setOpptId(opportunity.getId());
                        formOpptValue.setIsPick(fcv.getIsPick());
                        formOpptValue.setFormResultId(formCheckResult.getId());
                        formOpptValue.setCreateTime(DateUtil.currentDate());
                        formOpptValue.setModifyTime(DateUtil.currentDate());

                        formOpptValueMapper.insert(formOpptValue);
                    }
                }

                List<FormSubProblemVo> subProblemVos = problemVo.getSubProblemArray();
                if(subProblemVos != null && subProblemVos.size() > 0) {
                    for (FormSubProblemVo subProblemVo : subProblemVos) {
                        TFormSubPbmVal subPbmVal = new TFormSubPbmVal();
                        subPbmVal.setCreateTime(DateUtil.currentDate());
                        subPbmVal.setModifyTime(DateUtil.currentDate());
                        subPbmVal.setFormResultId(formCheckResult.getId());
                        subPbmVal.setProblemValueId(formPbmVal.getId());
                        subPbmVal.setSubProblemId(subProblemVo.getSubProblemID());
                        subPbmVal.setSubProblemName(subProblemVo.getSubProblemName());
                        subPbmVal.setSubProblemScore(subProblemVo.getSubProblemScore());
                        formSubPbmValMapper.insert(subPbmVal);

                        List<FormChanceVo> subChanceVos = subProblemVo.getSubProblemChanceArray();
                        List<FormSubProblemUnitVo> subProblemUnitVos = problemVo.getSubProblemUnitArray();
                        List<TFormOpptValue> subOppts = new ArrayList<>();
                        for (FormChanceVo subChanceVo : subChanceVos) {
                            for (FormSubProblemUnitVo subProblemUnitVo : subProblemUnitVos) {
                                TFormOpptValue formOpptValue = new TFormOpptValue();
                                formOpptValue.setCreateTime(DateUtil.currentDate());
                                formOpptValue.setModifyTime(DateUtil.currentDate());
                                formOpptValue.setFormResultId(formCheckResult.getId());
                                formOpptValue.setIsPick(subChanceVo.getIsPick());
                                formOpptValue.setOpptId(subChanceVo.getChanceID());
                                formOpptValue.setOpptName(subChanceVo.getChanceName());
                                formOpptValue.setProblemId(problemVo.getProblemID());
                                formOpptValue.setProblemType(problemVo.getProblemType().byteValue());
                                formOpptValue.setSubHeaderId(subProblemUnitVo.getSubProblemUnitID());
                                formOpptValue.setSubProblemId(subProblemVo.getSubProblemID());
                                formOpptValue.setSubProblemUnitScore(subProblemVo.getSubProblemScore());
                                formOpptValue.setSubProblemValueId(subPbmVal.getId());
                                formOpptValue.setProblemValueId(formPbmVal.getId());

                                subOppts.add(formOpptValue);
                            }
                        }
                        formOpptValueMapper.insertList(subOppts);
                    }
                }
            }
        }

        formCheckResult.setScore(score);
        total = total == 0 ? 1 : total;
        formCheckResult.setScoreRate(1.0f * score / total);
        formCheckResultMapper.updateByPrimaryKey(formCheckResult);

        return checkinWithAppCreatedOppts;
    }

    /**
     * 保存能力模型打表结果
     * @param formIn
     * @param user
     * @return
     */
    @Override
    @Transactional
    public Long saveAbilityFormResult(AbilityFormMain formIn, SysUser user) {
        try {
            Example formResultExp = new Example(TFormCheckResult.class);
            formResultExp.createCriteria().andEqualTo("frontPlanId", formIn.getPatrolShopArrangeID()).andEqualTo("formMainId", formIn.getFormID());
            List<TFormCheckResult> formResults = formCheckResultMapper.selectByExample(formResultExp);
            if(CollectionUtils.isNotEmpty(formResults)) {
                throw new BaseException("请勿重复保存打表结果！");
            }
            Date now = new Date();
            TFormCheckResult formResult = new TFormCheckResult();
            // 统计最后结论
            if(CollectionUtils.isNotEmpty(formIn.getSubFormArray())) {
                int cnt = 0;
                int size = formIn.getSubFormArray().size();
                for(AbilitySubForm subForm : formIn.getSubFormArray()) {
                    cnt++;
                    if(subForm.getIsDone() && !subForm.getIsPass()) {
                        formResult.setSummary(subForm.getSubFormName());
                        break;
                    }
                    if(cnt == size) {
                        formResult.setSummary(subForm.getSubFormName());
                    }
                }
            } else {
                throw new BaseException("数据异常，打表数据缺失！");
            }
            formResult.setFormMainId(formIn.getFormID());
            formResult.setOrgId(Constant.orgId);
            formResult.setFormMainName(formIn.getFormName());
            formResult.setCreateTime(now);
            formResult.setModifyTime(now);
            formResult.setFrontPlanId(formIn.getPatrolShopArrangeID());
            formResult.setCreateUserId(user.getId());
            formResult.setShopId(formIn.getShopID());
            formResult.setShopName(formIn.getShopName());
            // 保存主打表结果
            formCheckResultMapper.insert(formResult);

            boolean checkinWithAppCreatedOppts = false;

            List<SysPost> positions = sysPostMapper.findPositionByUserId(user.getId());
            List<String> pstns = positions.stream().map(p -> p.getPostName()).collect(Collectors.toList());
            String opptDescription = "前端创建-" + formIn.getFormName() + "-" + user.getDptName() + "-" + StringUtils.join(pstns, "|") + "-" + user.getRealName();
            List<Long> subResultIds = new ArrayList<>();
            for (AbilitySubForm subForm : formIn.getSubFormArray()) {
                TFormCheckResult subFormResult = new TFormCheckResult();
                subFormResult.setIsPass(subForm.getIsPass());
                subFormResult.setIsDone(subForm.getIsDone());
                subFormResult.setFormMainId(subForm.getSubFormID());
                subFormResult.setFormMainName(subForm.getSubFormName());
                subFormResult.setFrontPlanId(formIn.getPatrolShopArrangeID());
                subFormResult.setResultCanUse(subForm.getIsUsable());
                subFormResult.setParentFormId(formIn.getFormID());
                subFormResult.setCreateTime(now);
                subFormResult.setModifyTime(now);
                subFormResult.setCreateUserId(user.getId());
                subFormResult.setOrgId(Constant.orgId);
                subFormResult.setParentResultId(formResult.getId());
                // 保存子表打表结果
                formCheckResultMapper.insert(subFormResult);
                subResultIds.add(subFormResult.getId());

                if(CollectionUtils.isNotEmpty(subForm.getSubFormKindArray())) {
                    for(AbilitySubFormKind formKind : subForm.getSubFormKindArray()) {
                        TFormValue formValue = new TFormValue();
                        formValue.setFormResultId(subFormResult.getId());
                        formValue.setIsDone(formKind.getIsDone());
                        formValue.setIsPass(formKind.getIsPass());
                        formValue.setCreateTime(now);
                        formValue.setModifyTime(now);
                        formValue.setFromTypeId(formKind.getKindID());
                        formValue.setFormTypeName(formKind.getKindName());
                        // 保存表单类型打表结果
                        formValueMapper.insert(formValue);
                        if(CollectionUtils.isNotEmpty(formKind.getProblemArray())) {
                            List<ProblemVo> problemVos = formKind.getProblemArray();
                            for(ProblemVo problemVo : problemVos) {
                                TFormPbmVal formPbmVal = new TFormPbmVal();
                                formPbmVal.setCreateTime(now);
                                formPbmVal.setModifyTime(now);
                                formPbmVal.setFormProblemId(problemVo.getProblemID());
                                formPbmVal.setIsPass(problemVo.getIsPass());
                                formPbmVal.setIsScore(problemVo.getIsDone());
                                formPbmVal.setIsNa(problemVo.getIsNA());
                                formPbmVal.setFormResultId(subFormResult.getId());
                                formPbmVal.setFormValueId(formValue.getId());
                                formPbmVal.setProblemDescription(problemVo.getProblemDescription());
                                formPbmVal.setProblemName(problemVo.getProblemName());
                                Integer problemType = 0;
                                if(CollectionUtils.isNotEmpty(problemVo.getSubProblemArray())) {
                                    problemType = 2;
                                    formPbmVal.setProblemSchemaType(problemType);
                                } else {
                                    problemType = 1;
                                    formPbmVal.setProblemSchemaType(problemType);
                                }
                                // 保存题项照片数据
                                String imgIds = "";
                                List<ProblemImg> descImgArr = problemVo.getDescImgArr();
                                if(CollectionUtils.isNotEmpty(descImgArr)) {
                                    List<TFile> imgs = new ArrayList<>();
                                    for(ProblemImg problemImg : descImgArr) {
                                        TFile descImg = new TFile();
                                        descImg.setImgUrl(problemImg.getImgUrl());
                                        descImg.setCreateTime(now);
                                        descImg.setModifyTime(now);
                                        descImg.setIsDel(DeleteStatus.COMMON.getCode());
                                        descImg.setCreateUserId(user.getId());
                                        descImg.setSource(ImgSourceType.FROM_FRONT.getCode().byteValue());
                                        imgs.add(descImg);
                                    }
                                    fileMapper.insertList(imgs);

                                    for(TFile file : imgs) {
                                        imgIds += file.getId() + ",";
                                    }
                                    if(StringUtil.isNotBlank(imgIds)) {
                                        imgIds = imgIds.substring(0, imgIds.length() - 1);
                                    }
                                }
                                formPbmVal.setDescImgIds(imgIds);
                                formPbmVal.setOrgId(Constant.orgId);
                                formPbmVal.setNotes(problemVo.getNotes());
                                formPbmVal.setCheckMethod(problemVo.getCheckMode());
                                if(problemVo.getIsSubList()) {
                                    formPbmVal.setSubFormId(problemVo.getSubListObject().getSubFormID());
                                }
                                // 保存题项结果
                                formPbmValMapper.insert(formPbmVal);

                                List<ProblemChanceVo> chanceVos = problemVo.getChanceArray();
                                if (CollectionUtils.isNotEmpty(chanceVos)) {
                                    List<TFormOpptValue> opptVals = new ArrayList<>();
                                    for (ProblemChanceVo formChanceVo : chanceVos) {
                                        TFormOpptValue formOpptValue = new TFormOpptValue();
                                        formOpptValue.setCreateTime(now);
                                        formOpptValue.setModifyTime(now);
                                        formOpptValue.setFormResultId(subFormResult.getId());
                                        formOpptValue.setIsPick(formChanceVo.getIsPick());
                                        formOpptValue.setOpptId(formChanceVo.getChanceID());
                                        formOpptValue.setOpptName(formChanceVo.getChanceName());
                                        TOpportunity tOpportunity = opportunityMapper.selectByPrimaryKey(formChanceVo.getChanceID());
                                        if (tOpportunity != null) {
                                            formOpptValue.setOpptDesc(tOpportunity.getDescription());
                                        }

                                        formOpptValue.setProblemId(problemVo.getProblemID());
                                        formOpptValue.setProblemType(problemType.byteValue());
                                        formOpptValue.setProblemValueId(formPbmVal.getId());

                                        opptVals.add(formOpptValue);
                                    }
                                    formOpptValueMapper.insertList(opptVals);
                                }

                                List<ProblemChanceVo> otherChanceVos = problemVo.getOtherChanceArray();
                                if (CollectionUtils.isNotEmpty(otherChanceVos)) {
                                    checkinWithAppCreatedOppts = true;
                                    for (ProblemChanceVo fcv : otherChanceVos) {
                                        // 这里的机会点都是前端创建的，所以要先把机会点创建进数据库。为了机会点id
                                        TOpportunity opportunity = new TOpportunity();
                                        opportunity.setName(fcv.getChanceName());
                                        opportunity.setDescription(opptDescription);
                                        opportunity.setIsUse(true);
                                        opportunity.setIsDel(false);
                                        opportunity.setFrontCanCreate(true);
                                        opportunity.setType(0);
                                        opportunity.setCreateType(2);
                                        opportunity.setCreateTime(now);
                                        opportunity.setCreateUserId(user.getId());
                                        opportunity.setModifyTime(now);
                                        opportunity.setModifyUserId(user.getId());
                                        opportunityMapper.insert(opportunity);

                                        // 把机会点和题目的关系保存起来
                                        TFormOppt formOppt = new TFormOppt();
                                        formOppt.setOpptId(opportunity.getId());
                                        formOppt.setProblemId(problemVo.getProblemID());
                                        formOppt.setProblemType(problemType);
                                        formOppt.setCreateTime(now);
                                        formOppt.setModifyTime(now);
                                        formOpptMapper.insert(formOppt);

                                        TFormOpptValue formOpptValue = new TFormOpptValue();
                                        formOpptValue.setProblemValueId(formPbmVal.getId());
                                        formOpptValue.setProblemType(problemType.byteValue());
                                        formOpptValue.setProblemId(problemVo.getProblemID());
                                        formOpptValue.setOpptName(fcv.getChanceName());
                                        formOpptValue.setOpptDesc(opptDescription);
                                        formOpptValue.setOpptId(opportunity.getId());
                                        formOpptValue.setIsPick(fcv.getIsPick());
                                        formOpptValue.setFormResultId(subFormResult.getId());
                                        formOpptValue.setCreateTime(now);
                                        formOpptValue.setModifyTime(now);

                                        formOpptValueMapper.insert(formOpptValue);
                                    }
                                }
                                // 保存子表
                                if(problemVo.getIsSubList().equals(true)) {
                                    checkinWithAppCreatedOppts |= saveSubAbilityForm(problemVo.getSubListObject(), subForm.getSubFormID(), subFormResult.getId(), user, opptDescription, 1, formIn
                                            .getPatrolShopArrangeID(), subResultIds);
                                }
                                List<SubProblemVo> subProblemVos = problemVo.getSubProblemArray();
                                if (CollectionUtils.isNotEmpty(subProblemVos)) {
                                    for (SubProblemVo subProblemVo : subProblemVos) {
                                        TFormSubPbmVal subPbmVal = new TFormSubPbmVal();
                                        subPbmVal.setCreateTime(now);
                                        subPbmVal.setModifyTime(now);
                                        subPbmVal.setFormResultId(subFormResult.getId());
                                        subPbmVal.setIsPass(subProblemVo.getIsPass());
                                        subPbmVal.setIsScore(subProblemVo.getIsDone());
                                        subPbmVal.setIsNa(subProblemVo.getIsNA());
                                        subPbmVal.setProblemValueId(formPbmVal.getId());
                                        subPbmVal.setSubProblemId(subProblemVo.getSubProblemID());
                                        subPbmVal.setSubProblemName(subProblemVo.getSubProblemName());
                                        subPbmVal.setNotes(subProblemVo.getNotes());
                                        subPbmVal.setCheckMethod(subProblemVo.getCheckMode());
                                        if (subProblemVo.getIsSubList()) {
                                            subPbmVal.setSubFormId(subProblemVo.getSubListObject().getSubFormID());
                                        }
                                        subPbmVal.setProblemDescription(subProblemVo.getProblemDescription());
                                        descImgArr = subProblemVo.getDescImgArr();
                                        if (CollectionUtils.isNotEmpty(descImgArr)) {
                                            List<TFile> imgs = new ArrayList<>();
                                            for (ProblemImg problemImg : descImgArr) {
                                                TFile descImg = new TFile();
                                                descImg.setImgUrl(problemImg.getImgUrl());
                                                descImg.setIsDel(DeleteStatus.COMMON.getCode());
                                                descImg.setCreateTime(now);
                                                descImg.setModifyTime(now);
                                                descImg.setCreateUserId(user.getId());
                                                descImg.setSource(ImgSourceType.FROM_FRONT.getCode().byteValue());
                                                imgs.add(descImg);
                                            }
                                            fileMapper.insertList(imgs);

                                            for (TFile file : imgs) {
                                                imgIds += file.getId() + ",";
                                            }
                                            if (StringUtil.isNotBlank(imgIds)) {
                                                imgIds = imgIds.substring(0, imgIds.length() - 1);
                                            }
                                        }
                                        subPbmVal.setDescImgIds(imgIds);
                                        formSubPbmValMapper.insert(subPbmVal);

                                        List<ProblemChanceVo> subChanceVos = subProblemVo.getSubProblemChanceArray();
                                        if(CollectionUtils.isNotEmpty(subChanceVos)) {
                                            List<TFormOpptValue> opptVals = new ArrayList<>();
                                            for (ProblemChanceVo formChanceVo : subChanceVos) {
                                                TFormOpptValue formOpptValue = new TFormOpptValue();
                                                formOpptValue.setCreateTime(now);
                                                formOpptValue.setModifyTime(now);
                                                formOpptValue.setFormResultId(subFormResult.getId());
                                                formOpptValue.setIsPick(formChanceVo.getIsPick());
                                                formOpptValue.setOpptId(formChanceVo.getChanceID());
                                                formOpptValue.setOpptName(formChanceVo.getChanceName());
                                                TOpportunity tOpportunity = opportunityMapper.selectByPrimaryKey(formChanceVo.getChanceID());
                                                if (tOpportunity != null) {
                                                    formOpptValue.setOpptDesc(tOpportunity.getDescription());
                                                }

                                                formOpptValue.setSubProblemId(subProblemVo.getSubProblemID());
                                                formOpptValue.setProblemType(problemType.byteValue());
                                                formOpptValue.setSubProblemValueId(subPbmVal.getId());

                                                opptVals.add(formOpptValue);
                                            }
                                            formOpptValueMapper.insertList(opptVals);
                                        }
                                        List<ProblemChanceVo> subOtherChanceVos = subProblemVo.getSubOtherChanceArray();
                                        if (CollectionUtils.isNotEmpty(subOtherChanceVos)) {
                                            checkinWithAppCreatedOppts = true;
                                            for (ProblemChanceVo fcv : otherChanceVos) {
                                                // 这里的机会点都是前端创建的，所以要先把机会点创建进数据库。为了机会点id
                                                TOpportunity opportunity = new TOpportunity();
                                                opportunity.setName(fcv.getChanceName());
                                                opportunity.setDescription(opptDescription);
                                                opportunity.setIsUse(true);
                                                opportunity.setIsDel(false);
                                                opportunity.setFrontCanCreate(true);
                                                opportunity.setType(0);
                                                opportunity.setCreateType(2);
                                                opportunity.setCreateTime(now);
                                                opportunity.setCreateUserId(user.getId());
                                                opportunity.setModifyTime(now);
                                                opportunity.setModifyUserId(user.getId());
                                                opportunityMapper.insert(opportunity);

                                                // 把机会点和题目的关系保存起来
                                                TFormOppt formOppt = new TFormOppt();
                                                formOppt.setOpptId(opportunity.getId());
                                                formOppt.setProblemId(problemVo.getProblemID());
                                                formOppt.setProblemType(problemType);
                                                formOppt.setCreateTime(now);
                                                formOppt.setModifyTime(now);
                                                formOpptMapper.insert(formOppt);

                                                TFormOpptValue formOpptValue = new TFormOpptValue();
                                                formOpptValue.setProblemValueId(formPbmVal.getId());
                                                formOpptValue.setProblemType(problemType.byteValue());
                                                formOpptValue.setProblemId(problemVo.getProblemID());
                                                formOpptValue.setOpptName(fcv.getChanceName());
                                                formOpptValue.setOpptDesc(opptDescription);
                                                formOpptValue.setOpptId(opportunity.getId());
                                                formOpptValue.setIsPick(fcv.getIsPick());
                                                formOpptValue.setFormResultId(subFormResult.getId());
                                                formOpptValue.setCreateTime(now);
                                                formOpptValue.setModifyTime(now);

                                                formOpptValueMapper.insert(formOpptValue);
                                            }
                                        }
                                        // 保存子表
                                        if (subProblemVo.getIsSubList().equals(true)) {
                                            checkinWithAppCreatedOppts |= saveSubAbilityForm(subProblemVo.getSubListObject(), subForm.getSubFormID(), subFormResult.getId(),
                                                    user, opptDescription, 2, formIn.getPatrolShopArrangeID(), subResultIds);
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            // 生成并保存报告
            Long reportId = saveAbilityReport(user, now, formResult.getId(), subResultIds, formIn.getShopID(), formIn.getFormID(), formIn.getPatrolShopArrangeID());
            return reportId;
        } catch (Exception e) {
            logger.error("保存能力模型打表结果失败！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("保存能力模型打表结果失败！");
        }
    }

    private boolean saveSubAbilityForm(AbilitySubForm subForm, Long parentFormId, Long resultFormId, SysUser user,
                                       String opptDescription, int subjectType, Long frontPlanId, List<Long> subResultIds) {
        Date now = new Date();
        boolean checkinWithAppCreatedOppts = false;
        TFormCheckResult subFormResult = new TFormCheckResult();
        subFormResult.setIsPass(subForm.getIsPass());
        subFormResult.setIsDone(subForm.getIsDone());
        subFormResult.setFormMainId(subForm.getSubFormID());
        subFormResult.setFormMainName(subForm.getSubFormName());
        subFormResult.setSubjectType(subjectType);
        subFormResult.setFrontPlanId(frontPlanId);
        subFormResult.setResultCanUse(subForm.getIsUsable());
        subFormResult.setParentFormId(parentFormId);
        subFormResult.setCreateTime(now);
        subFormResult.setModifyTime(now);
        subFormResult.setCreateUserId(user.getId());
        subFormResult.setOrgId(Constant.orgId);
        subFormResult.setParentResultId(resultFormId);
        // 保存子表打表结果
        formCheckResultMapper.insert(subFormResult);
        subResultIds.add(subFormResult.getId());

        if (CollectionUtils.isNotEmpty(subForm.getSubFormKindArray())) {
            for (AbilitySubFormKind formKind : subForm.getSubFormKindArray()) {
                TFormValue formValue = new TFormValue();
                formValue.setFormResultId(subFormResult.getId());
                formValue.setIsDone(formKind.getIsDone());
                formValue.setIsPass(formKind.getIsPass());
                formValue.setCreateTime(now);
                formValue.setModifyTime(now);
                formValue.setFromTypeId(formKind.getKindID());
                formValue.setFormTypeName(formKind.getKindName());
                // 保存表单类型打表结果
                formValueMapper.insert(formValue);
                if (CollectionUtils.isNotEmpty(formKind.getProblemArray())) {
                    List<ProblemVo> problemVos = formKind.getProblemArray();
                    for (ProblemVo problemVo : problemVos) {
                        TFormPbmVal formPbmVal = new TFormPbmVal();
                        formPbmVal.setCreateTime(now);
                        formPbmVal.setModifyTime(now);
                        formPbmVal.setFormProblemId(problemVo.getProblemID());
                        formPbmVal.setIsPass(problemVo.getIsPass());
                        formPbmVal.setIsScore(problemVo.getIsDone());
                        formPbmVal.setIsNa(problemVo.getIsNA());
                        formPbmVal.setFormResultId(subFormResult.getId());
                        formPbmVal.setFormValueId(formValue.getId());
                        formPbmVal.setProblemDescription(problemVo.getProblemDescription());
                        formPbmVal.setProblemName(problemVo.getProblemName());
                        Integer problemType = 0;
                        if (CollectionUtils.isNotEmpty(problemVo.getSubProblemArray())) {
                            problemType = 2;
                            formPbmVal.setProblemSchemaType(problemType);
                        } else {
                            problemType = 1;
                            formPbmVal.setProblemSchemaType(problemType);
                        }
                        String imgIds = "";
                        List<ProblemImg> descImgArr = problemVo.getDescImgArr();
                        if (CollectionUtils.isNotEmpty(descImgArr)) {
                            List<TFile> imgs = new ArrayList<>();
                            for (ProblemImg problemImg : descImgArr) {
                                TFile descImg = new TFile();
                                descImg.setImgUrl(problemImg.getImgUrl());
                                descImg.setIsDel(DeleteStatus.COMMON.getCode());
                                descImg.setCreateTime(now);
                                descImg.setModifyTime(now);
                                descImg.setCreateUserId(user.getId());
                                descImg.setSource(ImgSourceType.FROM_FRONT.getCode().byteValue());
                                imgs.add(descImg);
                            }
                            fileMapper.insertList(imgs);

                            for (TFile file : imgs) {
                                imgIds += file.getId() + ",";
                            }
                            if (StringUtil.isNotBlank(imgIds)) {
                                imgIds = imgIds.substring(0, imgIds.length() - 1);
                            }
                        }
                        formPbmVal.setDescImgIds(imgIds);
                        formPbmVal.setOrgId(Constant.orgId);
                        formPbmVal.setNotes(problemVo.getNotes());
                        formPbmVal.setCheckMethod(problemVo.getCheckMode());
                        if (problemVo.getIsSubList()) {
                            formPbmVal.setSubFormId(problemVo.getSubListObject().getSubFormID());
                        }
                        // 保存题项结果
                        formPbmValMapper.insert(formPbmVal);

                        List<ProblemChanceVo> chanceVos = problemVo.getChanceArray();
                        if (CollectionUtils.isNotEmpty(chanceVos)) {
                            List<TFormOpptValue> opptVals = new ArrayList<>();
                            for (ProblemChanceVo formChanceVo : chanceVos) {
                                TFormOpptValue formOpptValue = new TFormOpptValue();
                                formOpptValue.setCreateTime(now);
                                formOpptValue.setModifyTime(now);
                                formOpptValue.setFormResultId(subFormResult.getId());
                                formOpptValue.setIsPick(formChanceVo.getIsPick());
                                formOpptValue.setOpptId(formChanceVo.getChanceID());
                                formOpptValue.setOpptName(formChanceVo.getChanceName());
                                TOpportunity tOpportunity = opportunityMapper.selectByPrimaryKey(formChanceVo.getChanceID());
                                if (tOpportunity != null) {
                                    formOpptValue.setOpptDesc(tOpportunity.getDescription());
                                }

                                formOpptValue.setProblemId(problemVo.getProblemID());
                                formOpptValue.setProblemType(problemType.byteValue());
                                formOpptValue.setProblemValueId(formPbmVal.getId());

                                opptVals.add(formOpptValue);
                            }
                            formOpptValueMapper.insertList(opptVals);
                        }

                        List<ProblemChanceVo> otherChanceVos = problemVo.getOtherChanceArray();
                        if (CollectionUtils.isNotEmpty(otherChanceVos)) {
                            checkinWithAppCreatedOppts = true;
                            for (ProblemChanceVo fcv : otherChanceVos) {
                                // 这里的机会点都是前端创建的，所以要先把机会点创建进数据库。为了机会点id
                                TOpportunity opportunity = new TOpportunity();
                                opportunity.setName(fcv.getChanceName());
                                opportunity.setDescription(opptDescription);
                                opportunity.setIsUse(true);
                                opportunity.setIsDel(false);
                                opportunity.setFrontCanCreate(true);
                                opportunity.setType(0);
                                opportunity.setCreateType(2);
                                opportunity.setCreateTime(now);
                                opportunity.setCreateUserId(user.getId());
                                opportunity.setModifyTime(now);
                                opportunity.setModifyUserId(user.getId());
                                opportunityMapper.insert(opportunity);

                                // 把机会点和题目的关系保存起来
                                TFormOppt formOppt = new TFormOppt();
                                formOppt.setOpptId(opportunity.getId());
                                formOppt.setProblemId(problemVo.getProblemID());
                                formOppt.setProblemType(problemType);
                                formOppt.setCreateTime(now);
                                formOppt.setModifyTime(now);
                                formOpptMapper.insert(formOppt);

                                TFormOpptValue formOpptValue = new TFormOpptValue();
                                formOpptValue.setProblemValueId(formPbmVal.getId());
                                formOpptValue.setProblemType(problemType.byteValue());
                                formOpptValue.setProblemId(problemVo.getProblemID());
                                formOpptValue.setOpptName(fcv.getChanceName());
                                formOpptValue.setOpptDesc(opptDescription);
                                formOpptValue.setOpptId(opportunity.getId());
                                formOpptValue.setIsPick(fcv.getIsPick());
                                formOpptValue.setFormResultId(subFormResult.getId());
                                formOpptValue.setCreateTime(now);
                                formOpptValue.setModifyTime(now);

                                formOpptValueMapper.insert(formOpptValue);
                            }
                        }
                        List<SubProblemVo> subProblemVos = problemVo.getSubProblemArray();
                        if (CollectionUtils.isNotEmpty(subProblemVos)) {
                            for (SubProblemVo subProblemVo : subProblemVos) {
                                TFormSubPbmVal subPbmVal = new TFormSubPbmVal();
                                subPbmVal.setCreateTime(now);
                                subPbmVal.setModifyTime(now);
                                subPbmVal.setFormResultId(subFormResult.getId());
                                subPbmVal.setIsPass(subProblemVo.getIsPass());
                                subPbmVal.setIsScore(subProblemVo.getIsDone());
                                subPbmVal.setIsNa(subProblemVo.getIsNA());
                                subPbmVal.setProblemValueId(formPbmVal.getId());
                                subPbmVal.setSubProblemId(subProblemVo.getSubProblemID());
                                subPbmVal.setSubProblemName(subProblemVo.getSubProblemName());
                                subPbmVal.setNotes(subProblemVo.getNotes());
                                subPbmVal.setCheckMethod(subProblemVo.getCheckMode());
                                if (subProblemVo.getIsSubList()) {
                                    subPbmVal.setSubFormId(subProblemVo.getSubListObject().getSubFormID());
                                }
                                subPbmVal.setProblemDescription(subProblemVo.getProblemDescription());
                                descImgArr = subProblemVo.getDescImgArr();
                                if (CollectionUtils.isNotEmpty(descImgArr)) {
                                    List<TFile> imgs = new ArrayList<>();
                                    for (ProblemImg problemImg : descImgArr) {
                                        TFile descImg = new TFile();
                                        descImg.setImgUrl(problemImg.getImgUrl());
                                        descImg.setIsDel(DeleteStatus.COMMON.getCode());
                                        descImg.setCreateTime(now);
                                        descImg.setModifyTime(now);
                                        descImg.setCreateUserId(user.getId());
                                        descImg.setSource(ImgSourceType.FROM_FRONT.getCode().byteValue());
                                        imgs.add(descImg);
                                    }
                                    fileMapper.insertList(imgs);

                                    for (TFile file : imgs) {
                                        imgIds += file.getId() + ",";
                                    }
                                    if (StringUtil.isNotBlank(imgIds)) {
                                        imgIds = imgIds.substring(0, imgIds.length() - 1);
                                    }
                                }
                                subPbmVal.setDescImgIds(imgIds);
                                formSubPbmValMapper.insert(subPbmVal);

                                List<ProblemChanceVo> subChanceVos = subProblemVo.getSubProblemChanceArray();
                                if (CollectionUtils.isNotEmpty(subChanceVos)) {
                                    List<TFormOpptValue> opptVals = new ArrayList<>();
                                    for (ProblemChanceVo formChanceVo : subChanceVos) {
                                        TFormOpptValue formOpptValue = new TFormOpptValue();
                                        formOpptValue.setCreateTime(now);
                                        formOpptValue.setModifyTime(now);
                                        formOpptValue.setFormResultId(subFormResult.getId());
                                        formOpptValue.setIsPick(formChanceVo.getIsPick());
                                        formOpptValue.setOpptId(formChanceVo.getChanceID());
                                        formOpptValue.setOpptName(formChanceVo.getChanceName());
                                        TOpportunity tOpportunity = opportunityMapper.selectByPrimaryKey(formChanceVo.getChanceID());
                                        if (tOpportunity != null) {
                                            formOpptValue.setOpptDesc(tOpportunity.getDescription());
                                        }

                                        formOpptValue.setSubProblemId(subProblemVo.getSubProblemID());
                                        formOpptValue.setProblemType(problemType.byteValue());
                                        formOpptValue.setSubProblemValueId(subPbmVal.getId());

                                        opptVals.add(formOpptValue);
                                    }
                                    formOpptValueMapper.insertList(opptVals);
                                }
                                List<ProblemChanceVo> subOtherChanceVos = subProblemVo.getSubOtherChanceArray();
                                if (CollectionUtils.isNotEmpty(subOtherChanceVos)) {
                                    checkinWithAppCreatedOppts = true;
                                    for (ProblemChanceVo fcv : otherChanceVos) {
                                        // 这里的机会点都是前端创建的，所以要先把机会点创建进数据库。为了机会点id
                                        TOpportunity opportunity = new TOpportunity();
                                        opportunity.setName(fcv.getChanceName());
                                        opportunity.setDescription(opptDescription);
                                        opportunity.setIsUse(true);
                                        opportunity.setIsDel(false);
                                        opportunity.setFrontCanCreate(true);
                                        opportunity.setType(0);
                                        opportunity.setCreateType(2);
                                        opportunity.setCreateTime(now);
                                        opportunity.setCreateUserId(user.getId());
                                        opportunity.setModifyTime(now);
                                        opportunity.setModifyUserId(user.getId());
                                        opportunityMapper.insert(opportunity);

                                        // 把机会点和题目的关系保存起来
                                        TFormOppt formOppt = new TFormOppt();
                                        formOppt.setOpptId(opportunity.getId());
                                        formOppt.setProblemId(problemVo.getProblemID());
                                        formOppt.setProblemType(problemType);
                                        formOppt.setCreateTime(now);
                                        formOppt.setModifyTime(now);
                                        formOpptMapper.insert(formOppt);

                                        TFormOpptValue formOpptValue = new TFormOpptValue();
                                        formOpptValue.setProblemValueId(formPbmVal.getId());
                                        formOpptValue.setProblemType(problemType.byteValue());
                                        formOpptValue.setProblemId(problemVo.getProblemID());
                                        formOpptValue.setOpptName(fcv.getChanceName());
                                        formOpptValue.setOpptDesc(opptDescription);
                                        formOpptValue.setOpptId(opportunity.getId());
                                        formOpptValue.setIsPick(fcv.getIsPick());
                                        formOpptValue.setFormResultId(subFormResult.getId());
                                        formOpptValue.setCreateTime(now);
                                        formOpptValue.setModifyTime(now);

                                        formOpptValueMapper.insert(formOpptValue);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        return checkinWithAppCreatedOppts;
    }

    private Long saveAbilityReport(SysUser user, Date now, Long resultId, List<Long> subResultIds, Long shopId, Long formId, Long frontPlanId) {
        // 保存报告
        TReport report = new TReport();

        report.setCheckFormTime(now);
        report.setCreateTime(now);
        report.setCreateUserId(user.getId());
        report.setOrgId(Constant.orgId);
        // 查询店铺名
        TShopInfo shop = shopInfoMapper.selectByPrimaryKey(shopId);
        report.setShopId(shop.getId());
        report.setShopName(shop.getShopName());
        // 查询店长
        SysUser shopOwer = sysUserMapper.selectByPrimaryKey(shop.getShopManagerId());
        report.setShopkeeperId(shopOwer.getId());
        report.setShopkeeperName(shopOwer.getRealName());
        report.setFormType(FormType.ABILITY_TYPE.getCode().byteValue());
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
        report.setFormResultId(resultId);
        reportMapper.saveReport(report);

        // 查询报告抄送部门
        TFormMain form = formMainMapper.selectByPrimaryKey(formId);
        if (StringUtils.isNotBlank(form.getCcDptIds())) {
            String[] ccDptId = form.getCcDptIds().split(",");
            Example dptUserExp = new Example(SysUser.class);
            dptUserExp.createCriteria().andIn("dptId", Arrays.asList(ccDptId)).andEqualTo("status", 0).andEqualTo("isDel", false);
            List<SysUser> ccUsers = sysUserMapper.selectByExample(dptUserExp);
            reportUserMapper.addReportToUser(ccUsers, report.getId());
        }

        // 整理关联的机会点，并存入报告与机会点关联表
        Example opptValueExp = new Example(TFormOpptValue.class);
        opptValueExp.createCriteria().andIn("formResultId", subResultIds).andEqualTo("isPick", true);
        List<TFormOpptValue> tFormOpptValues = formOpptValueMapper.selectByExample(opptValueExp);
        List<TReportOppt> tReportOppts = new ArrayList<>();
        for (TFormOpptValue tFormOpptValue : tFormOpptValues) {
            TReportOppt tReportOppt = new TReportOppt();
            tReportOppt.setOpptId(tFormOpptValue.getOpptId());
            tReportOppt.setOpptName(tFormOpptValue.getOpptName());
            tReportOppt.setOpptDesc(tFormOpptValue.getOpptDesc());
            tReportOppts.add(tReportOppt);
        }
        if (CollectionUtils.isNotEmpty(tReportOppts)) {
            reportOpptMapper.addReportOpptRelation(tReportOppts, report.getId(), new Date());
        }

        // 更新安排与表单完成状态
        frontPlanFormMapper.updateStatusByFormAndArrange(1, frontPlanId, formId, report.getId());

        //更新巡店安排状态
        Example tPlanFormExp = new Example(TFrontPlanForm.class);
        tPlanFormExp.createCriteria().andEqualTo("frontPlanId", frontPlanId).andEqualTo("isDel", false)
                .andEqualTo("reportStatus", 2);
        List<TFrontPlanForm> tFrontPlanForms = frontPlanFormMapper.selectByExample(tPlanFormExp);
        if (CollectionUtils.isEmpty(tFrontPlanForms)) {
            frontPlanMapper.updateFrontPlanStatus(ShopArrangeStatus.FINISH_CHECK.getCode(), frontPlanId);
        }
        return report.getId();
    }


}
