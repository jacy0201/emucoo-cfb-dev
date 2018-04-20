package com.emucoo.model;

import lombok.Data;

import java.util.List;

@Data
public class FormDetail {
    private TFormMain formMain;
    private List<TFormImptRules> formImptRules;
    private List<TFormScoreItem> formScoreItems;
    private List<TFormAddItem> formAddItems;
    private List<FormModule> formModules;
}
