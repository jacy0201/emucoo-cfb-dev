package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/18.
 */
@Data
public class AssignTaskHistoryVo_O {
    private String digitalItemName;
    private List<AssignTaskHistoryItemVo> historyDataArr;
}
