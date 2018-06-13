package com.emucoo.dto.modules.RYGReport;

import com.emucoo.dto.modules.form.FormKindVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/6/13.
 */
@Data
@ApiModel
public class RYGReportPreviewIn {
    @ApiModelProperty(value = "巡店id", name = "patrolShopArrangeID", required = true, example = "1")
    private Long patrolShopArrangeID;
    @ApiModelProperty(value = "店铺id", name = "shopID", required = true, example = "1")
    private Long shopID;
    @ApiModelProperty(value = "表单id", name = "checklistID", required = true, example = "1")
    private Long checklistID;
}
