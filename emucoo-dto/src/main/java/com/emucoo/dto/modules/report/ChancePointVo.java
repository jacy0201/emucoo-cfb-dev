package com.emucoo.dto.modules.report;

import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class ChancePointVo {
    private Integer chancePointFrequency;
    private Integer chancePointID;
    private String chancePointTitle;
    private String checkItemContent;
    private List<ReportWorkVo> workArr;
}
