package com.emucoo.service.manage;

import com.emucoo.dto.modules.RYGForm.RYGForm;
import com.emucoo.dto.modules.RYGForm.RYGFormOut;
import com.emucoo.dto.modules.abilityForm.AbilityFormMain;
import com.emucoo.dto.modules.abilityForm.GetFormInfoIn;
import com.emucoo.model.SysUser;
import com.emucoo.model.TFormMain;
import com.emucoo.model.TFormOppt;

import java.util.List;

public interface FormManageService {

    int countFormsByNameKeyword(String keyword, Integer formType);

    List<TFormMain> findFormsByNameKeyword(String keyword, int pageNm, int pageSz, Integer formType);

    void createForm(TFormMain form, Long userId);

    void deleteForms(List<Long> ids);

    void updateForm(TFormMain form);

    void enableForms(List<Long> ids);

    void disableForms(List<Long> ids);

    TFormMain fetchFormDetail(Long id);

    void saveFormDetail(TFormMain formMain);

    public List<TFormMain> findFormList();

    void saveFormReportSettings(TFormMain formMain);

    TFormMain fetchFormReportSettings(Long id);

    List<String> fetchAllBufferedFormTemplate(String keyPrefix);

    void saveAbilityForm(AbilityFormMain formMain);

    AbilityFormMain getAbilityForm(GetFormInfoIn formIn, SysUser user);

    /**
     * 关联机会点
     * @param tFormOpptList
     */
    void addFormOppt(List<TFormOppt> tFormOpptList);

    /**
     * 编辑机会点
     * @param tFormOpptList
     */
    void editFormOppt(List<TFormOppt> tFormOpptList);

    /**
     * 获取机会点列表
     * @param tFormOppt
     * @return
     */
    List<TFormOppt> getTFormOpptList(TFormOppt tFormOppt);
}
