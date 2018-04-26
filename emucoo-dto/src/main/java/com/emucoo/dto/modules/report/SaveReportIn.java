package com.emucoo.dto.modules.report;

import com.emucoo.common.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/3/26.
 */
@Data
@ApiModel
public class SaveReportIn {
    private Long patrolShopArrangeID;
    private Long shopID;
    private Long checklistID;
    private List<AdditionItemVo> additionArray;
    private String summary;
    private String reportValue;
    private String reporterID;
}
