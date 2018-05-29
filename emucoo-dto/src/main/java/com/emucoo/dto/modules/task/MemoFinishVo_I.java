package com.emucoo.dto.modules.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="修改工作备忘状态")
public class MemoFinishVo_I {
    private String workID;
    private String subWorkID;
    //1-未完成；2-完成
    private Integer memoStatus;
}
