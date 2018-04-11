package com.emucoo.dto.modules.sys;

import lombok.Data;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/31 13:47
 * @modified by:
 */
@Data
public class LabelVo {
    /**
     * 标签分类
     */
    private Long typeId;

    private Long dimensionId;

    private Long merchId;

    /**
     * 标签备注
     */
    private String remark;

    /**
     * 标签排序
     */
    private Integer sort;
}
