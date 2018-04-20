package com.emucoo.service.manage;

import com.emucoo.model.FormDetail;
import com.emucoo.model.TFormMain;

import java.util.List;

public interface FormManageService {

    List<TFormMain> findFormList();

    int countFormsByNameKeyword(String keyword);

    List<TFormMain> findFormsByNameKeyword(String keyword, int pageNm, int pageSz);

    void createForm(TFormMain form, Long userId);

    void deleteForms(List<Long> ids);

    void updateForm(TFormMain form);

    void enableForms(List<Long> ids);

    void disableForms(List<Long> ids);

    FormDetail fetchFormDetail(Long id);

    void saveFormDetail(FormDetail formDetail);

}
