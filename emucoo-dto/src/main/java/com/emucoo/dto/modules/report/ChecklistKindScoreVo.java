package com.emucoo.dto.modules.report;

import lombok.Data;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class ChecklistKindScoreVo {
    private Long kindID;
    private String kindName;
    private Boolean isDone;
    private Float scoreRate;
    private Integer realScore;
    private Integer realTotal;
    private Integer problemNum;
    private Integer wrongNum;
}
