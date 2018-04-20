package com.emucoo.service.manage;

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

}
