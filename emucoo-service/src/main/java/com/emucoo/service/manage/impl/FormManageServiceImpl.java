package com.emucoo.service.manage.impl;

import com.emucoo.common.exception.ApiException;
import com.emucoo.common.exception.BaseException;
import com.emucoo.dto.modules.abilityForm.AbilityFormMain;
import com.emucoo.dto.modules.abilityForm.AbilitySubForm;
import com.emucoo.dto.modules.abilityForm.AbilitySubFormKind;
import com.emucoo.dto.modules.abilityForm.GetFormInfoIn;
import com.emucoo.dto.modules.abilityForm.ProblemChanceVo;
import com.emucoo.dto.modules.abilityForm.ProblemVo;
import com.emucoo.dto.modules.abilityForm.SubProblemVo;
import com.emucoo.enums.Constant;
import com.emucoo.enums.DeleteStatus;
import com.emucoo.enums.FormType;
import com.emucoo.enums.WorkStatus;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.manage.FormManageService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormManageServiceImpl implements FormManageService {
    private Logger logger = LoggerFactory.getLogger(FormManageServiceImpl.class);

    @Autowired
    private TFormMainMapper formMainMapper;

    @Autowired
    private TFormAddItemMapper formAddItemMapper;

    @Autowired
    private TFormImptRulesMapper formImptRulesMapper;

    @Autowired
    private TFormScoreItemMapper formScoreItemMapper;

    @Autowired
    private TFormTypeMapper formTypeMapper;

    @Autowired
    private TFormPbmMapper formPbmMapper;

    @Autowired
    private TFormSubPbmMapper formSubPbmMapper;

    @Autowired
    private TFormSubPbmHeaderMapper formSubPbmHeaderMapper;

    @Autowired
    private TOpportunityMapper opportunityMapper;

    @Autowired
    private  TFormOpptMapper formOpptMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private TShopInfoMapper shopInfoMapper;

    @Autowired
    private TBrandInfoMapper brandInfoMapper;

    @Override
    public int countFormsByNameKeyword(String keyword, Integer formType) {
        return formMainMapper.countFormsByName(keyword, formType);
    }

    @Override
    public List<TFormMain> findFormsByNameKeyword(String keyword, int pageNm, int pageSz, Integer formType) {
        return formMainMapper.findFormsByName(keyword, (pageNm - 1) * pageSz, pageSz, formType);
    }

    @Override
    public void createForm(TFormMain form, Long userId) {
        form.setCreateTime(DateUtil.currentDate());
        form.setModifyTime(DateUtil.currentDate());
        form.setCreateUserId(userId);
        form.setModifyUserId(userId);
        form.setIsDel(false);
        form.setIsUse(false);
        formMainMapper.insert(form);

    }

    @Override
    public void updateForm(TFormMain form) {
        formMainMapper.updateByPrimaryKeySelective(form);
    }

    @Override
    public void deleteForms(List<Long> ids) {
        formMainMapper.removeByIds(ids);
    }

    @Override
    public void enableForms(List<Long> ids) {
        formMainMapper.changeIsUse(ids, 1);

    }

    @Override
    public void disableForms(List<Long> ids) {
        formMainMapper.changeIsUse(ids, 0);
    }

    /**
     * using the form main id to fetch the check form form database. because of the form is complexed tructure,
     * so according to the db table's association to assemble the form construction.
     *
     * @param id
     * @return
     */
    @Override
    public TFormMain fetchFormDetail(Long id) {

        TFormMain formMain = formMainMapper.fetchOneById(id);
        if(StringUtils.isNotBlank(formMain.getCcDptIds())) {
            List<Long> ids = Arrays.stream(formMain.getCcDptIds().split(",")).map(str -> Long.parseLong(str)).collect(Collectors.toList());
            List<SysDept> ccDepts = sysDeptMapper.fetchByIds(ids);
            formMain.setCcDepts(ccDepts);
        }

        List<TFormImptRules> formImptRuless = formImptRulesMapper.findFormImptRulesByFormMainId(formMain.getId());
        formMain.setImptRules(formImptRuless);

        List<TFormScoreItem> formScoreItems = formScoreItemMapper.findFormScoreItemsByFormMainId(formMain.getId());
        formMain.setScoreItems(formScoreItems);

        List<TFormAddItem> formAddItems = formAddItemMapper.findFormAddItemsByFormMainId(formMain.getId());
        formMain.setAddItems(formAddItems);

        List<TFormType> formModules = assembleFormModule(formMain.getId());
        formMain.setFormModules(formModules);

        return  formMain;
    }

    private List<TFormType> assembleFormModule(Long id) {
        List<TFormType> formTypes = formTypeMapper.findFormTypesByFormMainId(id);
        formTypes.forEach(formType -> {
            List<TFormPbm> formPbms = formPbmMapper.findFormPbmsByFormTypeId(formType.getId());
            for (TFormPbm formPbm : formPbms) {
                if (formPbm.getProblemSchemaType() == 2) {
                    List<TFormSubPbm> subPbms = formSubPbmMapper.findSubPbmsByPbmId(formPbm.getId());
                    formPbm.setSubProblems(subPbms);
                    List<TFormSubPbmHeader> subPbmHeaders = formSubPbmHeaderMapper.findFormSubPbmHeadersByFormPbmId(formPbm.getId());
                    subPbmHeaders.forEach(header -> header.setSubProblems(subPbms));
                    formPbm.setSubProblemHeads(subPbmHeaders);
                } else {
                    // 有些机会点是app创建的，所以不能在配置表单的时候返回给管理界面
                    List<TOpportunity> opptsOfPbm = opportunityMapper.findOpptsByPbmId(formPbm.getId());
                    formPbm.setOppts(opptsOfPbm);
                }
            }
            formType.setProblems(formPbms);
        });
        return formTypes;
    }

    /**
     * cause of the input parameter is complexed contructions, so disassemble the structure into different data object
     * and store them into db tables.
     * @param formMain
     */
    @Override
    @Transactional
    public void saveFormDetail(TFormMain formMain) {
        // 创建之前先把原来的数据清除
        cleanOldFormInfo(formMain);

        List<TFormImptRules> formImptRuless = formMain.getImptRules();
        if(formImptRuless != null && formImptRuless.size() > 0) {
            formImptRuless.forEach(it -> {
                it.setFormMainId(formMain.getId());
                it.setCreateTime(DateUtil.currentDate());
                it.setModifyTime(DateUtil.currentDate());
            });
            formImptRulesMapper.insertList(formImptRuless);
        }

        List<TFormScoreItem> formScoreItems = formMain.getScoreItems();
        if(formScoreItems != null && formScoreItems.size() > 0) {
            formScoreItems.forEach(it -> {
                it.setFormMainId(formMain.getId());
                it.setCreateTime(DateUtil.currentDate());
                it.setModifyTime(DateUtil.currentDate());
            });
            formScoreItemMapper.insertList(formScoreItems);
        }

        List<TFormType> formModules = formMain.getFormModules();
        formMain.setTotalScore(0);
        formModules.forEach(formModule -> {
            formModule.setFormMainId(formMain.getId());
            formModule.setCreateTime(DateUtil.currentDate());
            formModule.setModifyTime(DateUtil.currentDate());
            formTypeMapper.insert(formModule);

            disassembleFormModule(formModule);
            formMain.setTotalScore(formMain.getTotalScore().intValue() + formModule.getScore().intValue());
        });

        formMainMapper.updateByPrimaryKeySelective(formMain);
    }

    private void cleanOldFormInfo(TFormMain formMain) {
        formScoreItemMapper.dropByFormMainId(formMain.getId());
        formImptRulesMapper.dropByFormMainId(formMain.getId());
        List<TFormType> formModules = formMain.getFormModules();
        List<Long> mdlIds = new ArrayList<>();
        List<Long> probIds = new ArrayList<>();
        formModules.forEach(module -> {
            mdlIds.add(module.getId());
            module.getProblems().forEach(problem -> probIds.add(problem.getId()));
        });
        // 每次都要把抽查类问题自动创建的机会点和关系删除掉。
        List<Long> opptIds = formOpptMapper.fetchOpptIdsByProblemIds(probIds, 2);
        formTypeMapper.dropByFormMainId(formMain.getId());
        if(mdlIds.size() > 0)
            formPbmMapper.dropByFormTypeIds(mdlIds);
        if(probIds.size() > 0) {
            formSubPbmMapper.dropByProblemIds(probIds);
            formSubPbmHeaderMapper.dropByProblemIds(probIds);
            formOpptMapper.dropByProblemIds(probIds);
        }
        if(opptIds.size() > 0)
            opportunityMapper.dropByIds(opptIds);
    }


    private void disassembleFormModule(TFormType formType) {
        formType.setScore(0);
        List<TFormPbm> problems = formType.getProblems();
        int moduleScore = 0;
        for(TFormPbm problem : problems) {
            problem.setFormTypeId(formType.getId());
            problem.setCreateTime(DateUtil.currentDate());
            problem.setModifyTime(DateUtil.currentDate());
            formPbmMapper.insert(problem);

            if(problem.getProblemSchemaType() == 2) { // 只有题项是抽查类型时，才会有子题项， 和抽查项。
                List<TFormSubPbmHeader> subProblemHeads = problem.getSubProblemHeads();

                subProblemHeads.forEach(head -> {
                    head.setFormProblemId(problem.getId());
                    head.setCreateTime(DateUtil.currentDate());
                    head.setModifyTime(DateUtil.currentDate());
                });
                formSubPbmHeaderMapper.insertList(subProblemHeads);

                List<TFormSubPbm> subProblems = problem.getSubProblems();
                int subscore = 0;
                // 每个子检查项都会自动生成一个机会点
                for(TFormSubPbm subProblem : subProblems) {
                        subProblem.setFormProblemId(problem.getId());
                        subProblem.setCreateTime(DateUtil.currentDate());
                        subProblem.setCreateTime(DateUtil.currentDate());
                        subscore += subProblem.getSubProblemScore()==null?0:subProblem.getSubProblemScore();
                        formSubPbmMapper.insert(subProblem);

                        TOpportunity opportunity = new TOpportunity();
                        opportunity.setName(subProblem.getSubProblemName() + "不合格");
                        opportunity.setDescription(opportunity.getName());
                        opportunity.setCreateType(3);
                        opportunity.setType(0);
                        opportunity.setFrontCanCreate(false);
                        opportunity.setIsDel(false);
                        opportunity.setIsUse(true);
                        opportunity.setCreateTime(DateUtil.currentDate());
                        opportunity.setModifyTime(DateUtil.currentDate());
                        opportunityMapper.insert(opportunity);

                        TFormOppt formOppt = new TFormOppt();
                        formOppt.setProblemId(problem.getId());
                        formOppt.setProblemType(problem.getProblemSchemaType());
                        formOppt.setSubProblemId(subProblem.getId());
                        formOppt.setOpptId(opportunity.getId());
                        formOppt.setCreateTime(DateUtil.currentDate());
                        formOppt.setModifyTime(DateUtil.currentDate());
                        formOpptMapper.insert(formOppt);
                }
                int cols = subProblemHeads==null?0:subProblemHeads.size();
                problem.setScore(cols * subscore);
                formPbmMapper.updateByPrimaryKey(problem);
            } else { // 如果是检查类型的题目，则一道题可能对应与多个机会点，需要检查每次的机会点是否相同，更新关联表
                if(problem.getOppts() != null && problem.getOppts().size() > 0) {
                    problem.getOppts().forEach(oppt -> {
                        if(oppt.getId() == null)
                            return;
                        TFormOppt formOppt = new TFormOppt();
                        formOppt.setProblemId(problem.getId());
                        formOppt.setProblemType(problem.getProblemSchemaType());
                        formOppt.setOpptId(oppt.getId());
                        formOppt.setCreateTime(DateUtil.currentDate());
                        formOppt.setModifyTime(DateUtil.currentDate());
                        formOpptMapper.insert(formOppt);
                    });
                }
            }
            moduleScore += problem.getScore();
        };
        formType.setScore(moduleScore);
    }

    public List<TFormMain> findFormList() {
        Example example = new Example(TFormMain.class);
        example.createCriteria().andEqualTo("isDel", false).andEqualTo("isUse", true);
        List<TFormMain> tFormMains = formMainMapper.selectByExample(example);
        return tFormMains;
    }

    @Override
    public void saveFormReportSettings(TFormMain formMain) {
        formMainMapper.updateByPrimaryKeySelective(formMain);
        formAddItemMapper.dropByFormMainId(formMain.getId());
        // form add items maybe is null
        List<TFormAddItem> formAddItems = formMain.getAddItems();
        if(formAddItems != null && formAddItems.size() > 0) {
            formAddItems.forEach(it -> {
                it.setFormMainId(formMain.getId());
                it.setCreateTime(DateUtil.currentDate());
                it.setModifyTime(DateUtil.currentDate());
            });
            formAddItemMapper.insertList(formAddItems);
        }
    }

    @Override
    public TFormMain fetchFormReportSettings(Long id) {
        TFormMain formMain = formMainMapper.fetchOneById(id);
        List<TFormAddItem> formAddItems = formAddItemMapper.findFormAddItemsByFormMainId(formMain.getId());
        formMain.setAddItems(formAddItems);
        return formMain;
    }

    @Override
    public List<String> fetchAllBufferedFormTemplate(String keyPrefix) {
        List<SysUser> users = userMapper.fetchAllUsers();
        List<String> keys = new ArrayList<>();
        if (users != null) {
            users.forEach(u -> keys.add(keyPrefix + Long.toString(u.getId())));
        }
        return keys;
    }

    @Transactional
    public void saveAbilityForm(AbilityFormMain formMain) {
        try {
            Date now = new Date();
            TFormMain newFormMain = new TFormMain();
            newFormMain.setName(formMain.getFormName());
            newFormMain.setIsDel(DeleteStatus.COMMON.getCode());
            newFormMain.setFormType(FormType.ABILITY_TYPE.getCode());
            newFormMain.setCreateTime(now);
            newFormMain.setModifyTime(now);
            newFormMain.setUse(WorkStatus.STOP_USE.getCode());
            newFormMain.setOrgId(Constant.orgId);
            // 保存表单主体数据
            formMainMapper.insert(newFormMain);
            for (AbilitySubForm subForm : formMain.getSubFormArray()) {
                TFormMain subFormMain = new TFormMain();
                subFormMain.setName(subForm.getSubFormName());
                subFormMain.setIsDel(DeleteStatus.COMMON.getCode());
                subFormMain.setCreateTime(now);
                subFormMain.setModifyTime(now);
                subFormMain.setOrgId(Constant.orgId);
                subFormMain.setParentFormId(newFormMain.getId());
                // 保存子表
                formMainMapper.insert(subFormMain);
                if (CollectionUtils.isNotEmpty(subForm.getSubFormKindArray())) {
                    for (AbilitySubFormKind subFormKind : subForm.getSubFormKindArray()) {
                        TFormType formType = new TFormType();
                        formType.setFormMainId(subFormMain.getId());
                        formType.setCreateTime(now);
                        formType.setModifyTime(now);
                        formType.setTypeName(subFormKind.getKindName());
                        // 保存表单类型
                        formTypeMapper.insert(formType);
                        if (CollectionUtils.isNotEmpty(subFormKind.getProblemArray())) {
                            for (ProblemVo problem : subFormKind.getProblemArray()) {
                                TFormPbm formPbm = new TFormPbm();
                                formPbm.setName(problem.getProblemName());
                                formPbm.setCheckMethod(problem.getCheckMode());
                                formPbm.setFormTypeId(formType.getId());
                                formPbm.setCreateTime(now);
                                formPbm.setModifyTime(now);
                                if (problem.getIsSubList()) {
                                    TFormMain savedformMain = saveSubForm(problem.getSubListObject(), subFormMain.getId(), 1);
                                    if (savedformMain != null) {
                                        formPbm.setSubFormId(savedformMain.getId());
                                    }
                                }
                                // 保存题项数据
                                formPbmMapper.insert(formPbm);
                                if (CollectionUtils.isNotEmpty(problem.getSubProblemArray())) {
                                    for (SubProblemVo subProblemVo : problem.getSubProblemArray()) {
                                        TFormSubPbm formSubPbm = new TFormSubPbm();
                                        formSubPbm.setSubProblemName(subProblemVo.getSubProblemName());
                                        formSubPbm.setCreateTime(now);
                                        formSubPbm.setModifyTime(now);
                                        formSubPbm.setCheckMethod(subProblemVo.getCheckMode());
                                        formSubPbm.setFormProblemId(formPbm.getId());
                                        formSubPbm.setOrgId(Constant.orgId);
                                        if (subProblemVo.getIsSubList()) {
                                            TFormMain savedformMain = saveSubForm(subProblemVo.getSubListObject(), subFormMain.getId(), 2);
                                            if (savedformMain != null) {
                                                formSubPbm.setSubFormId(savedformMain.getId());
                                            }
                                        }
                                        // 保存子题数据
                                        formSubPbmMapper.insert(formSubPbm);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("创建能力模型表单失败！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("创建能力模型表单失败！");
        }
    }

    /**
     * 保存子表数据
     * @param formMain
     * @param parentFormId
     * @param
     * @return
     */
    private TFormMain saveSubForm(AbilitySubForm formMain, Long parentFormId, int subjectType) {
        Date now = new Date();
        TFormMain newFormMain = new TFormMain();
        newFormMain.setName(formMain.getSubFormName());
        newFormMain.setIsDel(DeleteStatus.COMMON.getCode());
        newFormMain.setCreateTime(now);
        newFormMain.setSubjectType(subjectType);
        newFormMain.setModifyTime(now);
        newFormMain.setParentFormId(parentFormId);
        newFormMain.setOrgId(Constant.orgId);
        // 保存表单主体数据
        formMainMapper.insert(newFormMain);
        if (CollectionUtils.isNotEmpty(formMain.getSubFormKindArray())) {
            for (AbilitySubFormKind subFormKind : formMain.getSubFormKindArray()) {
                TFormType formType = new TFormType();
                formType.setFormMainId(newFormMain.getId());
                formType.setCreateTime(now);
                formType.setModifyTime(now);
                formType.setTypeName(subFormKind.getKindName());
                // 保存表单类型
                formTypeMapper.insert(formType);
                if (CollectionUtils.isNotEmpty(subFormKind.getProblemArray())) {
                    for (ProblemVo problem : subFormKind.getProblemArray()) {
                        TFormPbm formPbm = new TFormPbm();
                        formPbm.setName(problem.getProblemName());
                        formPbm.setFormTypeId(formType.getId());
                        formPbm.setCheckMethod(problem.getCheckMode());
                        formPbm.setCreateTime(now);
                        formPbm.setModifyTime(now);
                        // 保存题项数据
                        formPbmMapper.insert(formPbm);
                        if (CollectionUtils.isNotEmpty(problem.getSubProblemArray())) {
                            for (SubProblemVo subProblemVo : problem.getSubProblemArray()) {
                                TFormSubPbm formSubPbm = new TFormSubPbm();
                                formSubPbm.setSubProblemName(subProblemVo.getSubProblemName());
                                formSubPbm.setCreateTime(now);
                                formSubPbm.setModifyTime(now);
                                formSubPbm.setCheckMethod(subProblemVo.getCheckMode());
                                formSubPbm.setFormProblemId(formPbm.getId());
                                formSubPbm.setOrgId(Constant.orgId);
                                if (subProblemVo.getIsSubList()) {
                                    TFormMain savedformMain = saveSubForm(subProblemVo.getSubListObject(), formMain.getSubFormID(), 2);
                                    if (savedformMain != null) {
                                        formSubPbm.setSubFormId(savedformMain.getId());
                                    }
                                }
                                // 保存子题数据
                                formSubPbmMapper.insert(formSubPbm);
                            }
                        }
                    }
                }
            }
        }


        return newFormMain;
    }

    public AbilityFormMain getAbilityForm(GetFormInfoIn formIn, SysUser user) {
        try {
            AbilityFormMain formVo = new AbilityFormMain();
            TFormMain main = new TFormMain();
            main.setId(formIn.getFormID());
            if(formIn.getFormType() != null) {
                main.setFormType(formIn.getFormType());
            } else {
                main.setFormType(FormType.ABILITY_TYPE.getCode());
            }

            TFormMain form = formMainMapper.selectOne(main);
            Long userId = null;
            if(user != null) {
                userId = user.getId();
            }
            if(form != null) {
                if(formIn.getShopID() != null) {
                    // 查询店铺
                    TShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(formIn.getShopID());

                    TBrandInfo brandInfo = brandInfoMapper.selectByPrimaryKey(shopInfo.getBrandId());
                    formVo.setShopName(shopInfo.getShopName());
                    formVo.setBrandName(brandInfo.getBrandName());
                    Date now = new Date();
                    formVo.setGradeDate(DateUtil.dateToString1(now));
                }

                formVo.setFormID(form.getId());
                formVo.setFormName(form.getName());
                Example formMainExp = new Example(TFormMain.class);
                formMainExp.createCriteria().andEqualTo("parentFormId", form.getId());
                List<TFormMain> formMains = formMainMapper.selectByExample(formMainExp);
                // 组装顶级子表的模板
                if(CollectionUtils.isNotEmpty(formMains)) {
                    List<AbilitySubForm> subForms = new ArrayList<>();
                    int cnt = 0;
                    for (TFormMain formMain : formMains) {
                        cnt++;
                        AbilitySubForm topSubForm = new AbilitySubForm();
                        topSubForm.setSubFormID(formMain.getId());
                        topSubForm.setSubFormName(formMain.getName());
                        if(cnt == 1) {
                            topSubForm.setIsUsable(true);
                        } else {
                            topSubForm.setIsUsable(false);
                        }
                        topSubForm.setIsDone(false);
                        topSubForm.setIsPass(false);
                        // 查询表单类型及下属题项与子题项
                        List<TFormType> formTypes = formTypeMapper.findFormTypeTreeUntilSubPbmByFormId(formMain.getId());
                        // 组装表单题目与子题
                        if(CollectionUtils.isNotEmpty(formTypes)) {
                            List<AbilitySubFormKind> subFormKindArray = new ArrayList<>();
                            for(TFormType saveFormType : formTypes) {
                                AbilitySubFormKind formKind = new AbilitySubFormKind();
                                formKind.setKindID(saveFormType.getId());
                                formKind.setKindName(saveFormType.getTypeName());
                                formKind.setIsDone(false);
                                formKind.setIsPass(false);
                                // 组装题项
                                if(CollectionUtils.isNotEmpty(saveFormType.getProblems())) {
                                    List<Long> pbmIds = new ArrayList<>();
                                    List<ProblemVo> problemArray = new ArrayList<>();
                                    for(TFormPbm formPbm : saveFormType.getProblems()) {
                                        ProblemVo problemVo = new ProblemVo();
                                        problemVo.setProblemID(formPbm.getId());
                                        problemVo.setCheckMode(formPbm.getCheckMethod());
                                        problemVo.setProblemName(formPbm.getName());
                                        problemVo.setIsDone(false);
                                        problemVo.setIsNA(false);
                                        problemVo.setIsPass(false);
                                        pbmIds.add(formPbm.getId());
                                        // 组装子题
                                        if(CollectionUtils.isNotEmpty(formPbm.getSubProblems())) {
                                            problemVo.setIsSubProblem(true);
                                            List<SubProblemVo> subProblemArray = new ArrayList<>();
                                            List<Long> subPbmIds = new ArrayList<>();
                                            for(TFormSubPbm formSubPbm : formPbm.getSubProblems()) {
                                                SubProblemVo subProblemVo = new SubProblemVo();
                                                subProblemVo.setSubProblemID(formSubPbm.getId());
                                                subProblemVo.setSubProblemName(formSubPbm.getSubProblemName());
                                                subProblemVo.setIsDone(false);
                                                subProblemVo.setIsNA(false);
                                                subProblemVo.setIsPass(false);
                                                subProblemVo.setCheckMode(formSubPbm.getCheckMethod());
                                                if (formSubPbm.getSubFormId() != null) {
                                                    subProblemVo.setIsSubList(true);
                                                    // 查询子表信息
                                                    AbilitySubForm subForm = findSubForm(formSubPbm.getSubFormId(), userId);
                                                    subProblemVo.setSubListObject(subForm);
                                                } else {
                                                    subProblemVo.setIsSubList(false);
                                                }
                                                subPbmIds.add(formSubPbm.getId());
                                                subProblemArray.add(subProblemVo);
                                            }

                                            // 查询机会点
                                            List<TFormOppt> formOppts = formOpptMapper.findFormOpptListByPbmId(subPbmIds, null, 2, userId);
                                            if (CollectionUtils.isNotEmpty(formOppts)) {
                                                for(SubProblemVo subProblemVo : subProblemArray) {
                                                    for (TFormOppt formOppt : formOppts) {
                                                        if(formOppt.getSubProblemId().equals(subProblemVo.getSubProblemID())) {
                                                            ProblemChanceVo subProblemChanceVo = new ProblemChanceVo();
                                                            subProblemChanceVo.setChanceID(formOppt.getOpptId());
                                                            subProblemChanceVo.setChanceName(formOppt.getOpptName());
                                                            subProblemChanceVo.setIsPick(false);
                                                            List<ProblemChanceVo> subProblemChanceVos = subProblemVo.getSubProblemChanceArray();
                                                            if(subProblemChanceVos != null) {
                                                                subProblemChanceVos.add(subProblemChanceVo);
                                                            } else {
                                                                subProblemChanceVos = new ArrayList<>();
                                                                subProblemChanceVos.add(subProblemChanceVo);
                                                            }
                                                            subProblemVo.setSubProblemChanceArray(subProblemChanceVos);
                                                        }
                                                    }
                                                }
                                            }

                                            problemVo.setSubProblemArray(subProblemArray);
                                        } else {
                                            problemVo.setIsSubProblem(false);
                                        }
                                        if(formPbm.getSubFormId() != null) {
                                            problemVo.setIsSubList(true);
                                            // 查询子表信息
                                            AbilitySubForm subForm = findSubForm(formPbm.getSubFormId(), userId);
                                            problemVo.setSubListObject(subForm);
                                        } else {
                                            problemVo.setIsSubList(false);
                                        }

                                        problemArray.add(problemVo);
                                    }
                                    // 查询机会点
                                    List<TFormOppt> formOppts = formOpptMapper.findFormOpptListByPbmId(null, pbmIds, 1, userId);
                                    if (CollectionUtils.isNotEmpty(formOppts)) {
                                        for (ProblemVo problemVo : problemArray) {
                                            for (TFormOppt formOppt : formOppts) {
                                                if (formOppt.getProblemId().equals(problemVo.getProblemID())) {
                                                    ProblemChanceVo problemChanceVo = new ProblemChanceVo();
                                                    problemChanceVo.setChanceID(formOppt.getOpptId());
                                                    problemChanceVo.setChanceName(formOppt.getOpptName());
                                                    problemChanceVo.setIsPick(false);
                                                    List<ProblemChanceVo> problemChanceVos = problemVo.getChanceArray();
                                                    if (problemChanceVos != null) {
                                                        problemChanceVos.add(problemChanceVo);
                                                    } else {
                                                        problemChanceVos = new ArrayList<>();
                                                        problemChanceVos.add(problemChanceVo);
                                                    }
                                                    problemVo.setChanceArray(problemChanceVos);
                                                }
                                            }
                                        }
                                    }

                                    formKind.setProblemArray(problemArray);
                                }
                                subFormKindArray.add(formKind);
                            }
                            topSubForm.setSubFormKindArray(subFormKindArray);
                        }
                        subForms.add(topSubForm);
                    }
                    formVo.setSubFormArray(subForms);
                }
            }
            return formVo;
        } catch (Exception e) {
            logger.error("查询能力模型表单失败！", e);
            if (e instanceof BaseException) {
                throw new ApiException(((BaseException) e).getMsg());
            }
            throw new ApiException("查询能力模型表单失败！");
        }
    }

    /**
     * 根据id查询子表
     * @param subFormId
     * @param userId
     * @return
     */
    private AbilitySubForm findSubForm(Long subFormId, Long userId) {
        AbilitySubForm abilitySubForm = null;
        TFormMain formMain = formMainMapper.selectByPrimaryKey(subFormId);
        if(formMain != null) {
            abilitySubForm = new AbilitySubForm();
            abilitySubForm.setSubFormID(formMain.getId());
            abilitySubForm.setSubFormName(formMain.getName());
            abilitySubForm.setIsPass(false);
            abilitySubForm.setIsDone(false);
            abilitySubForm.setIsUsable(false);
            // 查询表单类型及下属题项与子题项
            List<TFormType> formTypes = formTypeMapper.findFormTypeTreeUntilSubPbmByFormId(formMain.getId());
            // 组装表单题目与子题
            if (CollectionUtils.isNotEmpty(formTypes)) {
                List<AbilitySubFormKind> subFormKindArray = new ArrayList<>();
                for (TFormType saveFormType : formTypes) {
                    AbilitySubFormKind formKind = new AbilitySubFormKind();
                    formKind.setKindID(saveFormType.getId());
                    formKind.setKindName(saveFormType.getTypeName());
                    formKind.setIsDone(false);
                    formKind.setIsPass(false);
                    // 组装题项
                    if (CollectionUtils.isNotEmpty(saveFormType.getProblems())) {
                        List<Long> pbmIds = new ArrayList<>();
                        List<ProblemVo> problemArray = new ArrayList<>();
                        for (TFormPbm formPbm : saveFormType.getProblems()) {
                            ProblemVo problemVo = new ProblemVo();
                            problemVo.setProblemID(formPbm.getId());
                            problemVo.setCheckMode(formPbm.getCheckMethod());
                            problemVo.setProblemName(formPbm.getName());
                            problemVo.setIsDone(false);
                            problemVo.setIsNA(false);
                            problemVo.setIsPass(false);
                            pbmIds.add(formPbm.getId());
                            // 组装子题
                            if (CollectionUtils.isNotEmpty(formPbm.getSubProblems())) {
                                problemVo.setIsSubProblem(true);
                                List<Long> subPbmIds = new ArrayList<>();
                                List<SubProblemVo> subProblemArray = new ArrayList<>();
                                for (TFormSubPbm formSubPbm : formPbm.getSubProblems()) {
                                    SubProblemVo subProblemVo = new SubProblemVo();
                                    subProblemVo.setSubProblemID(formSubPbm.getId());
                                    subProblemVo.setSubProblemName(formSubPbm.getSubProblemName());
                                    subProblemVo.setCheckMode(formSubPbm.getCheckMethod());
                                    subProblemVo.setIsDone(false);
                                    subProblemVo.setIsNA(false);
                                    subProblemVo.setIsPass(false);
                                    if (formSubPbm.getSubFormId() != null) {
                                        subProblemVo.setIsSubList(true);
                                    } else {
                                        subProblemVo.setIsSubList(false);
                                    }
                                    subPbmIds.add(formSubPbm.getId());
                                    subProblemArray.add(subProblemVo);
                                }
                                // 查询机会点
                                List<TFormOppt> formOppts = formOpptMapper.findFormOpptListByPbmId(subPbmIds, null, 2, userId);
                                if (CollectionUtils.isNotEmpty(formOppts)) {
                                    for (SubProblemVo subProblemVo : subProblemArray) {
                                        for (TFormOppt formOppt : formOppts) {
                                            if (formOppt.getSubProblemId().equals(subProblemVo.getSubProblemID())) {
                                                ProblemChanceVo subProblemChanceVo = new ProblemChanceVo();
                                                subProblemChanceVo.setChanceID(formOppt.getOpptId());
                                                subProblemChanceVo.setChanceName(formOppt.getOpptName());
                                                subProblemChanceVo.setIsPick(false);
                                                List<ProblemChanceVo> subProblemChanceVos = subProblemVo.getSubProblemChanceArray();
                                                if (subProblemChanceVos != null) {
                                                    subProblemChanceVos.add(subProblemChanceVo);
                                                } else {
                                                    subProblemChanceVos = new ArrayList<>();
                                                    subProblemChanceVos.add(subProblemChanceVo);
                                                }
                                                subProblemVo.setSubProblemChanceArray(subProblemChanceVos);
                                            }
                                        }
                                    }
                                }

                                problemVo.setSubProblemArray(subProblemArray);
                            } else {
                                problemVo.setIsSubProblem(false);
                            }
                            if (formPbm.getSubFormId() != null) {
                                problemVo.setIsSubList(true);
                            } else {
                                problemVo.setIsSubList(false);
                            }
                            problemArray.add(problemVo);
                        }
                        // 查询机会点
                        List<TFormOppt> formOppts = formOpptMapper.findFormOpptListByPbmId(null, pbmIds, 1, userId);
                        if (CollectionUtils.isNotEmpty(formOppts)) {
                            for (ProblemVo problemVo : problemArray) {
                                for (TFormOppt formOppt : formOppts) {
                                    if (formOppt.getProblemId().equals(problemVo.getProblemID())) {
                                        ProblemChanceVo problemChanceVo = new ProblemChanceVo();
                                        problemChanceVo.setChanceID(formOppt.getOpptId());
                                        problemChanceVo.setChanceName(formOppt.getOpptName());
                                        problemChanceVo.setIsPick(false);
                                        List<ProblemChanceVo> problemChanceVos = problemVo.getChanceArray();
                                        if (problemChanceVos != null) {
                                            problemChanceVos.add(problemChanceVo);
                                        } else {
                                            problemChanceVos = new ArrayList<>();
                                            problemChanceVos.add(problemChanceVo);
                                        }
                                        problemVo.setChanceArray(problemChanceVos);
                                    }
                                }
                            }

                        }
                        formKind.setProblemArray(problemArray);
                    }
                    subFormKindArray.add(formKind);
                }
                abilitySubForm.setSubFormKindArray(subFormKindArray);
            }
        }
        return abilitySubForm;
    }


    /**
     * 关联机会点
     * @param tFormOpptList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFormOppt(List<TFormOppt> tFormOpptList){
        for (TFormOppt tFormOppt : tFormOpptList){
            if(null!=tFormOppt.getSubProblemId())
                tFormOppt.setProblemType(2);
            else
                tFormOppt.setProblemType(1);
            tFormOppt.setCreateTime(new Date());

            insertListOpt(tFormOppt.getOpptIdStr(),tFormOppt.getProblemType(),tFormOppt.getProblemId(),tFormOppt.getSubProblemId());
        }
    }

    /**
     * 编辑机会点
     * @param tFormOpptList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editFormOppt(List<TFormOppt> tFormOpptList){
        //先删除之前关联的机会点

        for (TFormOppt tFormOppt : tFormOpptList){
            Example example =new Example(TFormOppt.class);
            Example.Criteria criteria=example.createCriteria();
            if(null!=tFormOppt.getProblemId()){
                criteria.andEqualTo("problemId",tFormOppt.getProblemId());

            }
            if(null!=tFormOppt.getSubProblemId()){
                criteria.andEqualTo("subProblemId",tFormOppt.getSubProblemId());
            }
            formOpptMapper.deleteByExample(example);
        }
        //关联机会点
        for (TFormOppt tFormOppt : tFormOpptList){
            if(null!=tFormOppt.getSubProblemId())
                tFormOppt.setProblemType(2);
            else
                tFormOppt.setProblemType(1);

            insertListOpt(tFormOppt.getOpptIdStr(),tFormOppt.getProblemType(),tFormOppt.getProblemId(),tFormOppt.getSubProblemId());
        }

    }

    @Override
    public  List<TFormOppt> getTFormOpptList(TFormOppt tFormOppt){
        Example example=new Example(TFormOppt.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("problemId",tFormOppt.getProblemId());
        if(null!=tFormOppt.getSubProblemId()){
            criteria.andEqualTo("subProblemId",tFormOppt.getSubProblemId());
        }
        return formOpptMapper.selectByExample(example);
    }

    private void insertListOpt(String  opptIdStr,Integer problemType,Long problemId,Long subProblemId) {
        if(StringUtils.isNotEmpty(opptIdStr)) {
            String[] optArr = opptIdStr.split(",");
            List<TFormOppt> list = null;
            if (null != optArr && optArr.length > 0) {
                list = new ArrayList<>();
                TFormOppt tFormOppt=null;
                for (int i=0;i<optArr.length;i++) {
                    tFormOppt=new TFormOppt();
                    tFormOppt.setProblemId(problemId);
                    tFormOppt.setSubProblemId(subProblemId);
                    tFormOppt.setProblemType(problemType);
                    tFormOppt.setOpptId(Long.parseLong(optArr[i]));
                    tFormOppt.setCreateTime(new Date());
                    list.add(tFormOppt);
                }
                formOpptMapper.insertList(list);
            }
        }
    }
}
