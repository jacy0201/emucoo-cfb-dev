package com.emucoo.service.manage.impl;

import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.manage.FormManageService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private SysUserMapper userMapper;

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
            for(TFormPbm formPbm : formPbms) {
                if(formPbm.getProblemSchemaType() == 2) {
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
        if(users != null){
            users.forEach(u -> keys.add(keyPrefix + Long.toString(u.getId())));
        }
        return keys;
    }
}
