package com.emucoo.service.manage.impl;

import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.manage.FormManageService;
import com.emucoo.utils.DateUtil;
import org.apache.poi.ss.formula.FormulaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public FormDetail fetchFormDetail(Long id) {
        FormDetail detail = new FormDetail();

        TFormMain formMain = formMainMapper.fetchOneById(id);
        detail.setFormMain(formMain);

        List<TFormImptRules> formImptRuless = formImptRulesMapper.findFormImptRulesByFormMainId(formMain.getId());
        detail.setFormImptRules(formImptRuless);

        List<TFormScoreItem> formScoreItems = formScoreItemMapper.findFormScoreItemsByFormMainId(formMain.getId());
        detail.setFormScoreItems(formScoreItems);

        List<TFormAddItem> formAddItems = formAddItemMapper.findFormAddItemsByFormMainId(formMain.getId());
        detail.setFormAddItems(formAddItems);

        List<FormModule> formModules = assembleFormModule(formMain.getId());
        detail.setFormModules(formModules);

        return  detail;
    }

    private List<FormModule> assembleFormModule(Long id) {
        List<FormModule> formModules = new ArrayList<>();
        List<TFormType> formTypes = formTypeMapper.findFormTypesByFormMainId(id);
        formTypes.forEach(formType -> {
            FormModule formModule = new FormModule();
            formModule.setFormType(formType);
            List<FormItem> formItems = new ArrayList<>();
            List<TFormPbm> formPbms = formPbmMapper.findFormPbmsByFormTypeId(formType.getId());
            formPbms.forEach(formPbm -> {
                FormItem formItem = new FormItem();
                formItem.setFormProblem(formPbm);
                formItem.setFormSubProblems(formSubPbmMapper.findFormSubPbmsByFormPbmId(formPbm.getId()));
                formItem.setFormSubProblemHeads(formSubPbmHeaderMapper.findFormSubPbmHeadersByFormPbmId(formPbm.getId()));
                formItems.add(formItem);
            });
            formModule.setFormProblems(formItems);
            formModules.add(formModule);
        });
        return formModules;
    }

    /**
     * cause of the input parameter is complexed contructions, so disassemble the structure into different data object
     * and store them into db tables.
     * @param formDetail
     */
    @Override
    @Transactional
    public void saveFormDetail(FormDetail formDetail) {
        TFormMain formMain = formDetail.getFormMain();
        formMainMapper.upsert(formMain);

        List<TFormScoreItem> formScoreItems = formDetail.getFormScoreItems();
        formScoreItemMapper.dropByFormMainId(formMain.getId());
        formScoreItems.forEach(it -> it.setFormMainId(formMain.getId()));
        formScoreItemMapper.insertList(formScoreItems);

        List<TFormImptRules> formImptRuless = formDetail.getFormImptRules();
        formImptRulesMapper.dropByFormMainId(formMain.getId());
        formImptRuless.forEach(it -> it.setFormMainId(formMain.getId()));
        formImptRulesMapper.insertList(formImptRuless);

        // form add items maybe is null
        List<TFormAddItem> formAddItems = formDetail.getFormAddItems();
        formAddItemMapper.dropByFormMainId(formMain.getId());
        if(formAddItems != null && formAddItems.size() > 0) {
            formAddItems.forEach(it -> it.setFormMainId(formMain.getId()));
            formAddItemMapper.insertList(formAddItems);
        }

        dropModuleByFormMainId(formMain.getId());
        List<FormModule> formModules = formDetail.getFormModules();
        formModules.forEach(formModule -> {
            Optional.of(formModule.getFormType()).ifPresent(it -> it.setFormMainId(formMain.getId()));
            disassembleFormModule(formModule);
        });
    }

    private void dropModuleByFormMainId(Long id) {

    }

    private void disassembleFormModule(FormModule formModule) {
        TFormType formType = formModule.getFormType();
        formTypeMapper.upsert(formType);
        List<FormItem> formItems = formModule.getFormProblems();
        formItems.forEach(formItem -> {
            TFormPbm problem = formItem.getFormProblem();
            formPbmMapper.upsert(problem);
            if(problem.getProblemSchemaType() == 2) { // 只有题项是抽查类型时，才会有子题项， 和抽查项。
                List<TFormSubPbmHeader> subProblemHeads = formItem.getFormSubProblemHeads();
                formSubPbmHeaderMapper.upsertMulti(subProblemHeads);
                List<TFormSubPbm> subProblems = formItem.getFormSubProblems();
                // 每个子检查项都会自动生成一个机会点
                subProblems.forEach(subProblem -> {
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

                });

//                formSubPbmMapper.upsertMulti(subProblems);

            } else { // 如果是检查类型的题目，则一道题可能对应与多个机会点，需要检查每次的机会点是否相同，更新关联表

            }
        });
    }
}
