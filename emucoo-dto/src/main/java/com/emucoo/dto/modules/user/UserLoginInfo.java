package com.emucoo.dto.modules.user;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author river
 * @date 2018/3/8 11:53
 */
@Data
public class UserLoginInfo {
	
    private String userToken;
    private String headImgUrl;
    private String userName;
    private Long userId;
    private String userType;
    private Integer departmentType;
    private Long departmentID;
    private String departmentName;
    private Boolean usePlan;
    private List<shop> shopNameArr = Lists.newArrayList();
    
    @Data
    public static class shop {
        private String shopName;
        private Long shopID;
    }
}
