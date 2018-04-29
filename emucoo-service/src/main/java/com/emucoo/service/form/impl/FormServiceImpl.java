package com.emucoo.service.form.impl;

import com.emucoo.dto.modules.form.*;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.form.FormService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sj on 2018/4/20.
 */
@Service
public class FormServiceImpl implements FormService {

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
    private TFormOpptMapper formOpptMapper;

    public List<TFormMain> listForm() {
        Example example = new Example(TFormMain.class);
        example.createCriteria().andEqualTo("isDel", false).andEqualTo("isUse", true);
        List<TFormMain> tFormMains = formMainMapper.selectByExample(example);
        return tFormMains;
    }

    @Override
    public FormOut checkoutFormInfo(SysUser user, FormIn formIn) {
        Long arrangeId = formIn.getPatrolShopArrangeID();

        FormOut formOut = new FormOut();
        TFormMain formMain = formMainMapper.fetchOneById(formIn.getChecklistID());
        TShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(formIn.getShopID());
        if(formMain == null || shopInfo == null) {
            return null;
        }
        TBrandInfo brandInfo = brandInfoMapper.selectByPrimaryKey(shopInfo.getBrandId());
        formOut.setFormId(formMain.getId());
        formOut.setFormName(formMain.getName());
        formOut.setShopName(shopInfo.getShopName());
        formOut.setBrandName(brandInfo==null?"":brandInfo.getBrandName());
        formOut.setGradeDate(DateUtil.dateToString1(DateUtil.currentDate()));

        List<FormKindVo> formKindVos = new ArrayList<>();
        formOut.setKindArray(formKindVos);

        // 循环里面查数据库是比较弱智的方法，现在先这样吧，有时间再改。
        List<TFormType> modules = formTypeMapper.findFormTypesByFormMainId(formMain.getId());
        for(TFormType module : modules) {
//            TFormValue fv = formValueMapper.fetchOneFormValue(formMain.getId(), module.getId(), arrangeId);
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
                // 要先删除关系，再删除机会点
                formOpptMapper.cleanFormOpptRelationByResultId(result.getId());
                opportunityMapper.cleanOpptsByResultId(result.getId());

                formCheckResultMapper.deleteByPrimaryKey(result.getId());

                formValueMapper.cleanByResultId(result.getId());
                formPbmValMapper.cleanByResultId(result.getId());
                formSubPbmValMapper.cleanByResultId(result.getId());
                formOpptValueMapper.cleanByResultId(result.getId());

            });
        }
    }

    @Override
    public void checkinFormResult(SysUser user, FormIn formIn) {
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
            formValue.setFormMainId(formMainId);
            formValue.setFormMainName(formMain.getName());
            formValue.setFormResultId(formCheckResult.getId());
            formValue.setFormTypeName(module.getKindName());
            formValue.setFromTypeId(module.getKindID());
            formValue.setFrontPlanId(frontPlanId);
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
                formPbmVal.setProblemDescription(problemVo.getProblemDescription());
                formPbmVal.setProblemName(problemVo.getProblemName());
                formPbmVal.setProblemSchemaType(problemVo.getProblemType());
                formPbmVal.setScore(problemVo.getProblemScore());

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
                        formOpptValue.setProblemId(problemVo.getProblemID());
                        formOpptValue.setProblemType(problemVo.getProblemType().byteValue());
                        formOpptValue.setProblemValueId(formPbmVal.getId());

                        opptVals.add(formOpptValue);
                    }
                    formOpptValueMapper.insertList(opptVals);
                }

                List<FormChanceVo> otherChanceVos = problemVo.getOtherChanceArray();
                if(otherChanceVos != null && otherChanceVos.size() > 0) {
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
                        formOpptValue.setProblemType(problemVo.getProblemType().byteValue());
                        formOpptValue.setProblemId(problemVo.getProblemID());
                        formOpptValue.setOpptName(fcv.getChanceName());
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
        formCheckResult.setScoreRate(1.0f * score / total);
        formCheckResultMapper.updateByPrimaryKey(formCheckResult);

    }
}
