package com.emucoo.service.manage;

import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.utils.DateUtil;
import org.apache.poi.ss.formula.FormulaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public void saveFormDetail(FormDetail formDetail) {
        TFormMain formMain = formDetail.getFormMain();
        formMainMapper.upsert(formMain);
        List<TFormScoreItem> formScoreItems = formDetail.getFormScoreItems();
        formScoreItemMapper.upsertMulti(formScoreItems);
        List<TFormImptRules> formImptRuless = formDetail.getFormImptRules();
        formImptRulesMapper.upsertMulti(formImptRuless);
        List<TFormAddItem> formAddItems = formDetail.getFormAddItems();
        formAddItemMapper.upsertMulti(formAddItems);
        List<FormModule> formModules = formDetail.getFormModules();
        formModules.forEach(formModule -> {
            disassembleFormModule(formModule);
        });
    }

    private void disassembleFormModule(FormModule formModule) {
        TFormType formType = formModule.getFormType();
        formTypeMapper.upsert(formType);
        List<FormItem> formItems = formModule.getFormProblems();
        formItems.forEach(formItem -> {
            TFormPbm problem = formItem.getFormProblem();
            formPbmMapper.upsert(problem);
            List<TFormSubPbmHeader> subProblemHeads = formItem.getFormSubProblemHeads();
            formSubPbmHeaderMapper.upsertMulti(subProblemHeads);
            List<TFormSubPbm> subProblems = formItem.getFormSubProblems();
            formSubPbmMapper.upsertMulti(subProblems);
        });
    }
}
