package com.emucoo.dto.modules.report;


import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/8 18:45
 */

@Data
public class ReportVo {
    private Integer reportID;
    private Integer patrolShopArrangeID;
    private String shopName;
    private String shopownerName;
    private Date checkDate;
    private String inspectorName;
    private String inspectorPosition;
    private String checkDepartmentName;
    private String summary;
    private Integer realScore;
    private Integer realTotal;
    private List<FormRulesVo> rulesArray;
    private String scoreSummaryImg;
    private String chancePointNum;
    private List<AdditionItemVo> additionArray;
    private List<ChancePointVo> chancePointArr;
    private List<ChecklistKindScoreVo> checklistKindScoreArr;
}
