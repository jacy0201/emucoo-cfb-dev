package com.emucoo.dto.base;

import lombok.Data;

/**
 * @author river
 * @date 2018/2/28 16:20
 */
@Data
public class Page {

    private String sortField = "id";

    private String sort = "desc";

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
