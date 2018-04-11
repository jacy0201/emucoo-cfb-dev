package com.emucoo.dto.modules.dimension;

import lombok.Data;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/31 15:45
 * @modified by:
 */
@Data
public class DimensionVo {
    /**
     * 分类
     */
    private Integer type;

    /**
     * 维度名称
     */
    private String name;

    /**
     * 分类名称
     */
    private String typeName;

    /**
     * 备注
     */
    private String remark;

    public Boolean isUse;
}
