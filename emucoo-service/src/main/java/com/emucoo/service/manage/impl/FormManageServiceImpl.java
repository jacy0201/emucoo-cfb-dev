package com.emucoo.service.manage.impl;

import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.manage.FormManageService;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
//        FormDetail detail = new FormDetail();

        TFormMain formMain = formMainMapper.fetchOneById(id);
//        detail.setFormMain(formMain);

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
            formPbms.forEach(formPbm -> {
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
        List<TFormScoreItem> formScoreItems = formMain.getScoreItems();
//        formScoreItemMapper.dropByFormMainId(formMain.getId());
        formScoreItems.forEach(it -> it.setFormMainId(formMain.getId()));
        formScoreItemMapper.insertList(formScoreItems);

        List<TFormImptRules> formImptRuless = formMain.getImptRules();
//        formImptRulesMapper.dropByFormMainId(formMain.getId());
        formImptRuless.forEach(it -> it.setFormMainId(formMain.getId()));
        formImptRulesMapper.insertList(formImptRuless);

        // form add items maybe is null
        List<TFormAddItem> formAddItems = formMain.getAddItems();
//        formAddItemMapper.dropByFormMainId(formMain.getId());
        if(formAddItems != null && formAddItems.size() > 0) {
            formAddItems.forEach(it -> it.setFormMainId(formMain.getId()));
            formAddItemMapper.insertList(formAddItems);
        }

        dropModuleByFormMainId(formMain.getId());
        List<TFormType> formModules = formMain.getFormModules();
        formModules.forEach(formModule -> {
            formModule.setFormMainId(formMain.getId());
            disassembleFormModule(formModule);
        });
    }

    private void dropModuleByFormMainId(Long id) {

    }

    private void disassembleFormModule(TFormType formType) {
        formTypeMapper.insert(formType);

        List<TFormPbm> problems = formType.getProblems();
        problems.forEach(problem -> {

            problem.setFormTypeId(formType.getId());
            formPbmMapper.insert(problem);

            if(problem.getProblemSchemaType() == 2) { // 只有题项是抽查类型时，才会有子题项， 和抽查项。
                List<TFormSubPbmHeader> subProblemHeads = problem.getSubProblemHeads();

                subProblemHeads.forEach(head -> head.setFormProblemId(problem.getId()));
                formSubPbmHeaderMapper.insertList(subProblemHeads);

                List<TFormSubPbm> subProblems = problem.getSubProblems();
                // 每个子检查项都会自动生成一个机会点
                subProblems.forEach(subProblem -> {
                        subProblem.setFormProblemId(problem.getId());
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
                        formOpptMapper.insert(formOppt);
                });
            } else { // 如果是检查类型的题目，则一道题可能对应与多个机会点，需要检查每次的机会点是否相同，更新关联表
                problem.getOppts().forEach(oppt -> {
                    TFormOppt formOppt = new TFormOppt();
                    formOppt.setProblemId(problem.getId());
                    formOppt.setProblemType(problem.getProblemSchemaType());
                    formOppt.setOpptId(oppt.getId());
                    formOpptMapper.insert(formOppt);
                });
            }
        });
    }
}
