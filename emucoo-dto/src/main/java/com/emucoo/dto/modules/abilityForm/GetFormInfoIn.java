package com.emucoo.dto.modules.abilityForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by sj on 2018/5/17.
 */
@Data
@ApiModel
public class GetFormInfoIn {
    @ApiModelProperty(value = "表单id", name = "formID", required = true, example = "1")
    private Long formID;
    @ApiModelProperty(value = "表单类型 1-类RVR表，2-能力模型", name = "formType", required = true, example = "2")
    private Integer formType;
}
