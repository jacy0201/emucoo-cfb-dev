package com.emucoo.dto.modules.form;

import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class FormIn {
    private Long patrolShopArrangeID;
    private Long shopID;
    private Long checklistID;
    private List<FormKindVo> kindArray;
}
