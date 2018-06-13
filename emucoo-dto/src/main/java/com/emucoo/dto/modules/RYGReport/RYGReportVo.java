package com.emucoo.dto.modules.RYGReport;

import com.emucoo.dto.modules.RYGForm.RYGFormKindVo;
import com.emucoo.dto.modules.report.AdditionItemVo;
import com.emucoo.dto.modules.report.ChancePointVo;
import com.emucoo.dto.modules.report.FormRulesVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/6/11.
 */
@Data
public class RYGReportVo {
    private Long reportID;
    @ApiModelProperty(value = "巡店安排id", name = "patrolShopArrangeID", example = "1")
    private Long patrolShopArrangeID;
    @ApiModelProperty(value = "店铺id", name = "shopID", example = "1")
    private Long shopID;
    @ApiModelProperty(value = "表单id", name = "checklistID", example = "1")
    private Long checklistID;
    @ApiModelProperty(value = "店铺名称", name = "shopName", example = "南京西路店")
    private String shopName;
    @ApiModelProperty(value = "店长名", name = "shopownerName", example = "")
    private String shopownerName;
    @ApiModelProperty(value = "打表日期", name = "checkDate", example = "2018-06-11")
    private String checkDate;
    @ApiModelProperty(value = "督导姓名", name = "inspectorName", example = "")
    private String inspectorName;
    @ApiModelProperty(value = "督导岗位", name = "inspectorPosition", example = "")
    private String inspectorPosition;
    @ApiModelProperty(value = "打表部门", name = "checkDepartmentName", example = "")
    private String checkDepartmentName;
    @ApiModelProperty(value = "总结", name = "summary", example = "合格")
    private String summary;
    @ApiModelProperty(value = "机会点数", name = "chancePointNum", example = "1")
    private String chancePointNum;
    @ApiModelProperty(value = "总红色数量", name = "allRedNum", example = "1")
    private Integer allRedNum;
    @ApiModelProperty(value = "总黄色数量", name = "allYellowNum", example = "1")
    private Integer allYellowNum;
    @ApiModelProperty(value = "总绿色数量", name = "allGreenNum", example = "1")
    private Integer allGreenNum;
    @ApiModelProperty(value = "na数量", name = "allNaNum", example = "1")
    private Integer allNaNum;
    @ApiModelProperty(value = "额外项数组", name = "additionArray")
    private List<AdditionItemVo> additionArray;
    @ApiModelProperty(value = "机会点数组", name = "chancePointArr")
    private List<ChancePointVo> chancePointArr;
    @ApiModelProperty(value = "题项类别数组", name = "kindArray")
    private List<RYGFormKindVo> checklistKindScoreArr;

}
