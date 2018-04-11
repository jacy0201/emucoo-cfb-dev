package com.emucoo.dto.modules.user;

import lombok.Data;

@Data
public class User_query {
    private String username;
    private String realName;
    private String mobile;
    private String email;
    private String labelName;
    private Integer status;
}
