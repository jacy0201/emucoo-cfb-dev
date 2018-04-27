package com.emucoo.service.manage.impl;

import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.manage.FormManageService;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormManageServiceImpl implements FormManageService {

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

    @Override
    public int countFormsByNameKeyword(String keyword) {
        return formMainMapper.countFormsByName(keyword);
    }

    @Override
    public List<TFormMain> findFormsByNameKeyword(String keyword, int pageNm, int pageSz) {
        return formMainMapper.findFormsByName(keyword, (pageNm - 1) * pageSz, pageSz);
    }

    @Override
    public void createForm(TFormMain form, Long userId) {
        form.setCreateTime(DateUtil.currentDate());
        form.setModifyTime(DateUtil.currentDate());
        form.setCreateUserId(userId);
        form.setModifyUserId(userId);
        formMainMapper.insert(form);

    }

    @Override
    public void updateForm(TFormMain form) {
        formMainMapper.updateByPrimaryKey(form);
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
        if(formMain.getCcDptIds() != null) {
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
            for(TFormPbm formPbm : formPbms) {
                if(formPbm.getProblemSchemaType() == 2) {
                    List<TFormSubPbm> subPbms = formSubPbmMapper.findFormSubPbmsByFormPbmId(formPbm.getId());
                    formPbm.setSubProblems(subPbms);
                    List<TFormSubPbmHeader> subPbmHeaders = formSubPbmHeaderMapper.findFormSubPbmHeadersByFormPbmId(formPbm.getId());
                    subPbmHeaders.forEach(header -> header.setSubProblems(subPbms));
                    formPbm.setSubProblemHeads(subPbmHeaders);
                } else {
                    // 有些机会点是app创建的，所以不能在配置表单的时候返回给管理界面
                    if(formPbm.getOppts() != null && formPbm.getOppts().size() > 0) {
                        Iterator<TOpportunity> it = formPbm.getOppts().iterator();
                        while (it.hasNext()) {
                            TOpportunity oppt = it.next();
                            if (oppt.getCreateType() != 1) {
                                it.remove();
                            }
                        }
                    }
                }
            }
            formPbms.stream().filter(pbm -> pbm.getProblemSchemaType() == 2).forEach(formPbm -> {
                List<TFormSubPbm> subPbms = formSubPbmMapper.findFormSubPbmsByFormPbmId(formPbm.getId());
                formPbm.setSubProblems(subPbms);
                List<TFormSubPbmHeader> subPbmHeaders = formSubPbmHeaderMapper.findFormSubPbmHeadersByFormPbmId(formPbm.getId());
                subPbmHeaders.forEach(header -> header.setSubProblems(subPbms));
                formPbm.setSubProblemHeads(subPbmHeaders);
            });
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
        formImptRuless.forEach(it -> {
            it.setFormMainId(formMain.getId());
            it.setCreateTime(DateUtil.currentDate());
            it.setModifyTime(DateUtil.currentDate());
        });
        formImptRulesMapper.insertList(formImptRuless);

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

        List<TFormType> formModules = formMain.getFormModules();
        formModules.forEach(formModule -> {
            formModule.setFormMainId(formMain.getId());
            formModule.setCreateTime(DateUtil.currentDate());
            formModule.setModifyTime(DateUtil.currentDate());
            formTypeMapper.insert(formModule);

            disassembleFormModule(formModule);
        });
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
        List<TFormPbm> problems = formType.getProblems();
        problems.forEach(problem -> {
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
                // 每个子检查项都会自动生成一个机会点
                subProblems.forEach(subProblem -> {
                        subProblem.setFormProblemId(problem.getId());
                        subProblem.setCreateTime(DateUtil.currentDate());
                        subProblem.setCreateTime(DateUtil.currentDate());
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
                });
            } else { // 如果是检查类型的题目，则一道题可能对应与多个机会点，需要检查每次的机会点是否相同，更新关联表
                problem.getOppts().forEach(oppt -> {
                    TFormOppt formOppt = new TFormOppt();
                    formOppt.setProblemId(problem.getId());
                    formOppt.setProblemType(problem.getProblemSchemaType());
                    formOppt.setOpptId(oppt.getId());
                    formOppt.setCreateTime(DateUtil.currentDate());
                    formOppt.setModifyTime(DateUtil.currentDate());
                    formOpptMapper.insert(formOppt);
                });
            }
        });
    }

    public List<TFormMain> findFormList() {
        Example example = new Example(TFormMain.class);
        example.createCriteria().andEqualTo("isDel", false).andEqualTo("isUse", true);
        List<TFormMain> tFormMains = formMainMapper.selectByExample(example);
        return tFormMains;
    }

    @Override
    public void saveFormReportSettings(TFormMain formMain) {
        formMainMapper.updateByPrimaryKey(formMain);
        formAddItemMapper.dropByFormMainId(formMain.getId());
        List<TFormScoreItem> formScoreItems = formMain.getScoreItems();
        formScoreItems.forEach(it -> it.setFormMainId(formMain.getId()));
        formScoreItemMapper.insertList(formScoreItems);
    }
}
