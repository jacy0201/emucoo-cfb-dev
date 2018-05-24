package com.emucoo.dto.modules.report;

import com.emucoo.dto.modules.abilityForm.ProblemImg;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class ChancePointVo {
    private Integer chancePointFrequency;
    private Long chancePointID;
    private String chancePointTitle;
    private String chanceContent;
    private List<ReportWorkVo> workArr;
    private List<ProblemImg> descImgArr;
    private String chanceDescription;
}
