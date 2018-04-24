package com.emucoo.dto.modules.form;

import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class FormOut {
    private String formName;
    private String shopName;
    private String gradeDate;
    private String brandName;
    private List<FormKindVo> kindArray;
}
