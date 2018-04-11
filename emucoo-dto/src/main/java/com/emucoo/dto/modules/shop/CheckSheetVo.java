package com.emucoo.dto.modules.shop;

import lombok.Data;

/**
 * Created by tombaby on 2018/3/25.
 */
@Data
public class CheckSheetVo {
    private long checklistID;
    private String checklistName;
    private int sourceType;
    private String sourceName;
}
