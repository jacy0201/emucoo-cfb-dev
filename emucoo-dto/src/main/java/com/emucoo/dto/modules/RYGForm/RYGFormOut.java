package com.emucoo.dto.modules.RYGForm;

import com.emucoo.dto.modules.form.FormKindVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
public class RYGFormOut {
    @ApiModelProperty(value = "表单id", name = "formId", example = "1")
    private Long formId;
    @ApiModelProperty(value = "表单名称", name = "formName", example = "RYG")
    private String formName;
    @ApiModelProperty(value = "店铺名称", name = "shopName", example = "南京西路店")
    private String shopName;
    private String gradeDate;
    private String brandName;
    private List<RYGFormKindVo> kindArray;
}
