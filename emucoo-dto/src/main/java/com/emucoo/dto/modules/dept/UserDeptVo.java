package com.emucoo.dto.modules.dept;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/16 12:26
 */
@Data
public class UserDeptVo {
    private Long userId;
    private Long deptId;
    private Integer deptType;
    private String userType;
    private String deptName;
}
