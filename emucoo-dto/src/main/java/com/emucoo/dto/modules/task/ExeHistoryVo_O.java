package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/25.
 */
@Data
public class ExeHistoryVo_O {
    private int totalImplementNum;
    private List<ExeHistoryItemVo> historyTaskArr;
}
