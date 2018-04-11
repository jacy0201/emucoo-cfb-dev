package com.emucoo.dto.modules.user;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/8 11:53
 */
@Data
public class UserLogin {
	
	private long id;
    private String headImgUrl;
    private String userName;
    private Integer userType;
    private String departmentType;
    private String departmentID;
    private String departmentName;
}
