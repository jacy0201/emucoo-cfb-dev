package com.emucoo.dto.modules.sys;

import lombok.Data;

/**
 * 机构查询
 */

@Data
public class DeptQuery {

    /**
     * 机构名称
     */
    private String dptName;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 分区id
     */
    private Long areaId;

    /**
     * 启用/停用
     */
    private Boolean isUse;

}
