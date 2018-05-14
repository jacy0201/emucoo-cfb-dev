package com.emucoo.dto.modules.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jacy
 */
@Data
@ApiModel(value="检查版本对象参数",description="")
public class VersionQuery {

    //版本号
    @ApiModelProperty(value="版本号",name="appVersion",example="1.0.0",required = true)
    private String appVersion;

    //1-ios;2-android
    @ApiModelProperty(value="app类型:1-ios;2-android",name="appType",example="1",required = true)
    private Integer appType;

}
