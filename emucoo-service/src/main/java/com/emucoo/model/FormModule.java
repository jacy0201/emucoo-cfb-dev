package com.emucoo.model;

import lombok.Data;

import java.util.List;

@Data
public class FormModule {
    private TFormType formType;
    private List<FormItem> formProblems;

}
