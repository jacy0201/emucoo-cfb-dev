package com.emucoo.model;

import lombok.Data;

import java.util.List;

@Data
public class FormItem {
    private TFormPbm formProblem;
    private List<TFormSubPbm> formSubProblems;
    private List<TFormSubPbmHeader> formSubProblemHeads;
}
