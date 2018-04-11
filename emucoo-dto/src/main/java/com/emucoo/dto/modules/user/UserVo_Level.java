package com.emucoo.dto.modules.user;

import com.google.common.collect.Lists;

import java.util.List;

import lombok.Data;

@Data
public class UserVo_Level {

    public static final int UNIQUE = 1;
    public static final int NOT_UNIQUE = 0;

    private String levelName;
    private Long userPid;
    private Long supRoleId;
    private Long subRoleId;
    //是否对应  0 不对应，1 对应
    private Integer type = UNIQUE;
    List<UserVo_Simple> userList = Lists.newArrayList();
}
