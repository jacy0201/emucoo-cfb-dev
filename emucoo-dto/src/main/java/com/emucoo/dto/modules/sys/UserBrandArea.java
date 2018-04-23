package com.emucoo.dto.modules.sys;

import lombok.Data;

import java.util.List;

/**
 * 设置用户品牌分区
 */

@Data
public class UserBrandArea {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 品牌id集合
     */
    private List<Long> listBrandId;

    /**
     * 分区id集合
     */
    private List<Long>  listAreaId;

}
