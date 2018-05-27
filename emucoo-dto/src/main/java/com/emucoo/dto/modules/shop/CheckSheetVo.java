package com.emucoo.dto.modules.shop;

import lombok.Data;

/**
 * Created by tombaby on 2018/3/25.
 */
@Data
public class CheckSheetVo {
    private Long checklistID;
    private String checklistName;
    private Integer checklistType;
    private Integer sourceType;
    private String sourceName;
}
