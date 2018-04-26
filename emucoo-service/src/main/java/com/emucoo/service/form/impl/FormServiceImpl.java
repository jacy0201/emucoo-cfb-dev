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
        if(shopInfo != null) {
            return null;
        }
        formOut.setFormId(formMain.getId());
        formOut.setFormName(formMain.getName());
        formOut.setShopName(shopInfo.getShopName());
        formOut.setBrandName(shopInfo.getBrandName());
        formOut.setGradeDate(DateUtil.dateToString1(DateUtil.currentDate()));

        List<FormKindVo> formKindVos = new ArrayList<>();
        formOut.setKindArray(formKindVos);

        // 循环里面查数据库是比较弱智的方法，现在先这样吧，有时间再改。
        List<TFormType> modules = formTypeMapper.findFormTypesByFormMainId(shopInfo.getId());
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
                pbmVo.setIsNA(true);
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
                        if(subPbm.getOpportunity() != null){
                            List<FormChanceVo> subProblemChanceVos = new ArrayList<>();
                            for(TFormSubPbmHeader subPbmHeader : subPbmHeaders){
                                FormChanceVo subProblemChanceVo = new FormChanceVo();
                                subProblemChanceVo.setChanceID(subPbm.getOpportunity().getId());
                                subProblemChanceVo.setChanceName(subPbm.getOpportunity().getName());
//                                TFormOpptValue opptVal = formOpptValueMapper.fetchOneSubPbmOpptValue(subPbmVal.getId(), subPbm.getId(), subPbmHeader.getId(), 2);
                                subProblemChanceVo.setPick(false);
                            }
                        }
                        subProblemVos.add(subProblemVo);
                    }
                    pbmVo.setSubProblemArray(subProblemVos);
                    pbmVo.setSubProblemUnitArray(subProblemUnitVos);
                } else {
                    List<TOpportunity> oppts = pbm.getOppts();
                    List<FormChanceVo> formChanceVos = new ArrayList<>();
                    if(oppts != null && oppts.size() > 0) {
                        for(TOpportunity oppt : oppts) {
//                            TFormOpptValue opptValue = formOpptValueMapper.fetchOnePbmOpptValue(pbmVal.getId(), oppt.getId(), 1);
                            FormChanceVo formChanceVo = new FormChanceVo();
                            formChanceVo.setChanceID(oppt.getId());
                            formChanceVo.setChanceName(oppt.getName());
                            formChanceVo.setPick(false);
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

    @Override
    public void checkinFormResult(SysUser user, FormIn formIn) {
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
//            formValue.setScore(module.);
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
                score += problemVo.getProblemScore();
                total += problemVo.getProblemTotal();

                List<FormChanceVo> chanceVos = problemVo.getChanceArray();
                List<TFormOpptValue> opptVals = new ArrayList<>();
                for(FormChanceVo formChanceVo : chanceVos) {
                    TFormOpptValue formOpptValue = new TFormOpptValue();
                    formOpptValue.setCreateTime(DateUtil.currentDate());
                    formOpptValue.setModifyTime(DateUtil.currentDate());
                    formOpptValue.setFormResultId(formCheckResult.getId());
                    formOpptValue.setIsPick(formChanceVo.isPick());
                    formOpptValue.setOpptId(formChanceVo.getChanceID());
                    formOpptValue.setOpptName(formChanceVo.getChanceName());
                    formOpptValue.setProblemId(problemVo.getProblemID());
                    formOpptValue.setProblemType(problemVo.getProblemType().byteValue());
                    formOpptValue.setProblemValueId(formPbmVal.getId());

                    opptVals.add(formOpptValue);
                }
                formOpptValueMapper.insertList(opptVals);

                List<FormChanceVo> otherChanceVos = problemVo.getOtherChanceArray();
                List<TFormOpptValue> otherOpptVals = new ArrayList<>();
                for(FormChanceVo fcv : otherChanceVos) {
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

                    TFormOpptValue formOpptValue =  new TFormOpptValue();
//                    formOpptValue.setSubProblemValueId();
//                    formOpptValue.setSubProblemUnitScore();
//                    formOpptValue.setSubProblemId();
//                    formOpptValue.setSubHeaderId();
                    formOpptValue.setProblemType(problemVo.getProblemType().byteValue());
                    formOpptValue.setProblemId(problemVo.getProblemID());
                    formOpptValue.setOpptName(fcv.getChanceName());
                    formOpptValue.setOpptId(opportunity.getId());
                    formOpptValue.setIsPick(fcv.isPick());
                    formOpptValue.setFormResultId(formCheckResult.getId());
                    formOpptValue.setCreateTime(DateUtil.currentDate());
                    formOpptValue.setModifyTime(DateUtil.currentDate());

                    otherOpptVals.add(formOpptValue);
                }
                formOpptValueMapper.insertList(otherOpptVals);

                List<FormSubProblemVo> subProblemVos = problemVo.getSubProblemArray();
                for(FormSubProblemVo subProblemVo : subProblemVos) {
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
                    for(FormChanceVo subChanceVo : subChanceVos) {
                        for(FormSubProblemUnitVo subProblemUnitVo : subProblemUnitVos) {
                            TFormOpptValue formOpptValue = new TFormOpptValue();
                            formOpptValue.setCreateTime(DateUtil.currentDate());
                            formOpptValue.setModifyTime(DateUtil.currentDate());
                            formOpptValue.setFormResultId(formCheckResult.getId());
                            formOpptValue.setIsPick(subChanceVo.isPick());
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

        formCheckResult.setScore(score);
        formCheckResult.setScoreRate(1.0f * score / total);
        formCheckResultMapper.updateByPrimaryKey(formCheckResult);

    }
}
