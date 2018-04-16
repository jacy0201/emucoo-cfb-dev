package com.emucoo.dto.modules.user;

import java.util.Date;

import com.emucoo.dto.modules.demo.demoVo;

import lombok.Data;

@Data
public class UserVo_I extends UserVo {
    private String pushToken;
    private String mobile;
    private String password;
}
