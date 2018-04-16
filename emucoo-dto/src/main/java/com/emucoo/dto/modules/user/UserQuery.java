package com.emucoo.dto.modules.user;

import lombok.Data;

@Data
public class UserQuery {
    private String username;
    private String realName;
    private String mobile;
    private String email;
    private Integer status;
    private String dptId;
    private String shopId;
    private String postId;
    private Integer pageNum;
    private Integer pageSize;

}
