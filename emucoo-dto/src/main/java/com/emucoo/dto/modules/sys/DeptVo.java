package com.emucoo.dto.modules.sys;

import lombok.Data;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/30 18:10
 * @modified by:
 */
@Data
public class DeptVo {
    /**
     * 父ID ，层级关系
     */
    private Long parentId;

    /**
     * 0:集团,1：分公司
     */
    private Integer type;

    /**
     * 部门ID
     */
    private String dptname;

    /**
     * 部门编码
     */
    private String dptno;

    /**
     * 备注
     */
    private String remark;

    private Boolean isUse = false;


    /**
     * 地址代码
     */
    private String addressCode;

    /**
     * 详细地址
     */
    private String addressDetail;

    private String tel;
}
