package com.emucoo.dto.modules.RYGForm;

import com.emucoo.dto.modules.form.FormKindVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
@ApiModel
public class RYGForm extends RYGFormOut {
    @ApiModelProperty(value = "巡店id", name = "patrolShopArrangeID", required = true, example = "1")
    private Long patrolShopArrangeID;
    @ApiModelProperty(value = "店铺id", name = "shopID", required = true, example = "1")
    private Long shopID;
    @ApiModelProperty(value = "表单id", name = "checklistID", required = true, example = "1")
    private Long checklistID;
    @ApiModelProperty(value = "表单类型 1-类RVR表，2-能力模型", name = "formType", example = "2")
    private Integer formType;
    @ApiModelProperty(value = "表单名称", name = "formName", example = "RYG")
    private String formName;
    @ApiModelProperty(value = "题项类别", name = "kindArray")
    private List<RYGFormKindVo> kindArray;
}
