package com.emucoo.dto.modules.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="用户列表查询",description="用户列表查询参数")
public class UserQuery{
    @ApiModelProperty(value="用户名",name="username",example="xingguo")
    private String username;
    @ApiModelProperty(value="姓名",name="realName",example="张三")
    private String realName;
    private String mobile;
    private String email;
    @ApiModelProperty(value="状态:0-启用；1-停用；2-锁定",name="status")
    private Integer status;
    @ApiModelProperty(value="机构Id",name="dptId",required=true)
    private Long dptId;
    @ApiModelProperty(value="店铺id",name="shopId")
    private Long shopId;
    @ApiModelProperty(value="岗位id",name="postId")
    private Long postId;
    @ApiModelProperty(value="是否是店长:true/false",name="isShopManager",example="true" )
    private Boolean isShopManager;
}
