package com.emucoo.dto.modules.dimension;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/17 12:30
 */
@Data
public class DimensionVo_O {
    private Long id;
    private String name;
    private Integer typeNum;
    private Integer labelNum;
    private Boolean isUse;
}
