package com.emucoo.service.manage;

import com.emucoo.dto.modules.abilityForm.AbilityFormMain;
import com.emucoo.dto.modules.abilityForm.GetFormInfoIn;
import com.emucoo.model.TFormMain;

import java.util.List;

public interface FormManageService {

    int countFormsByNameKeyword(String keyword);

    List<TFormMain> findFormsByNameKeyword(String keyword, int pageNm, int pageSz);

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

    AbilityFormMain getAbilityForm(GetFormInfoIn formIn);
}
