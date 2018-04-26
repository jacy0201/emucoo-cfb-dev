package com.emucoo.service.form.impl;

import com.emucoo.dto.modules.form.*;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.form.FormService;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

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
            TFormValue fv = formValueMapper.fetchOneFormValue(formMain.getId(), module.getId(), arrangeId);
            FormKindVo formKindVo = new FormKindVo();
            formKindVo.setIsDone(fv.getIsDone());
            formKindVo.setKindID(module.getId());
            formKindVo.setKindName(module.getTypeName());

            List<FormProblemVo> problemVos = new ArrayList<>();

            List<TFormPbm> pbms = formPbmMapper.findFormPbmsByFormTypeId(module.getId());
            for(TFormPbm pbm : pbms) {
                TFormPbmVal pbmVal = formPbmValMapper.fetchOnePbmValue(fv.getId(), pbm.getId());

                FormProblemVo pbmVo = new FormProblemVo();
                pbmVo.setProblemID(pbm.getId());
                pbmVo.setProblemName(pbm.getName());
                pbmVo.setProblemDescription(pbm.getDescriptionHit());
                pbmVo.setIsImportant(pbm.getIsImportant());
                pbmVo.setIsNA(pbmVal.getIsNa());
                pbmVo.setIsScore(pbmVal.getIsScore());
                pbmVo.setProblemScore(Integer.parseInt(pbmVal.getScore()));
                pbmVo.setProblemTotal(pbm.getScore());
                pbmVo.setProblemType(pbm.getProblemSchemaType());

                if(pbm.getProblemSchemaType() == 2) {
                    List<TFormSubPbmHeader> subPbmHeaders = formSubPbmHeaderMapper.findFormSubPbmHeadersByFormPbmId(pbm.getId());
                    List<FormSubProblemUnitVo> subProblemUnitVos = new ArrayList<>();
                    for(TFormSubPbmHeader subPbmHeader : subPbmHeaders){
                        FormSubProblemUnitVo formSubProblemUnitVo = new FormSubProblemUnitVo();
                        formSubProblemUnitVo.setSubProblemUnitName(subPbmHeader.getName());
                        subProblemUnitVos.add(formSubProblemUnitVo);
                    }

                    List<TFormSubPbm> subPbms = formSubPbmMapper.findFormSubPbmsByFormPbmId(pbm.getId());
                    List<FormSubProblemVo> subProblemVos = new ArrayList<>();
                    for(TFormSubPbm subPbm : subPbms) {
                        TFormSubPbmVal subPbmVal = formSubPbmValMapper.fetchOneSubPbmValue(pbmVal.getId(), subPbm.getId());
                        FormSubProblemVo subProblemVo = new FormSubProblemVo();
                        subProblemVo.setSubProblemID(subPbm.getId());
                        subProblemVo.setSubProblemName(subPbm.getSubProblemName());
                        subProblemVo.setSubProblemScore(subPbmVal.getSubProblemScore());
                        subProblemVo.setSubProblemTotal(subPbm.getSubProblemScore() * subProblemUnitVos.size());
                        if(subPbm.getOpportunity() != null){
                            List<FormChanceVo> subProblemChanceVos = new ArrayList<>();
                            for(TFormSubPbmHeader subPbmHeader : subPbmHeaders){
                                FormChanceVo subProblemChanceVo = new FormChanceVo();
                                subProblemChanceVo.setChanceID(subPbm.getOpportunity().getId());
                                subProblemChanceVo.setChanceName(subPbm.getOpportunity().getName());
                                TFormOpptValue opptVal = formOpptValueMapper.fetchOneSubPbmOpptValue(subPbmVal.getId(), subPbm.getId(), subPbmHeader.getId(), 2);
                                subProblemChanceVo.setPick(opptVal.getIsPick());
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
                            TFormOpptValue opptValue = formOpptValueMapper.fetchOnePbmOpptValue(pbmVal.getId(), oppt.getId(), 1);
                            FormChanceVo formChanceVo = new FormChanceVo();
                            formChanceVo.setChanceID(oppt.getId());
                            formChanceVo.setChanceName(oppt.getName());
                            formChanceVo.setPick(opptValue.getIsPick());
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
    public void checkinFormResult(FormIn formIn) {
        Long frontPlanId = formIn.getPatrolShopArrangeID();
        Long formMainId = formIn.getChecklistID();
        Long shopId = formIn.getShopID();

        List<FormKindVo> modules = formIn.getKindArray();
        for(FormKindVo module : modules) {
            List<FormProblemVo> problemVos = module.getProblemArray();
            for(FormProblemVo problemVo : problemVos) {
                List<FormChanceVo> chanceVos = problemVo.getChanceArray();
                List<FormChanceVo> otherChanceVos = problemVo.getOtherChanceArray();
                List<FormSubProblemVo> subProblemVos = problemVo.getSubProblemArray();
                List<FormSubProblemUnitVo> subProblemUnitVos = problemVo.getSubProblemUnitArray();

            }
        }
    }
}
