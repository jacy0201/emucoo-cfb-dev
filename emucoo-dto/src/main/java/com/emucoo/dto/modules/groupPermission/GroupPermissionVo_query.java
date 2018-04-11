package com.emucoo.dto.modules.groupPermission;

import lombok.Data;

/**
 * @author LXC
 * @date 2017/5/10
 */
@Data
public class GroupPermissionVo_query {

  private String name;

  private String sortField;

  private String sort;

  private Integer pageNum = 1;

  private Integer pageSize = 10;
}
