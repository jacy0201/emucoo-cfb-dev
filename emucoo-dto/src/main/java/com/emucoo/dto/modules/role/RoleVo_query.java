package com.emucoo.dto.modules.role;

import lombok.Data;

/**
 * @author LXC
 * @date 2017/5/10
 */
@Data
public class RoleVo_query {

  private String name = "";

  /**
   * 如果pid = 0 则为系统角色
   */
  private Long pid;

  private String sortField = "id";

  private String sort = "desc";

  private Integer pageNum = 1;

  private Integer pageSize = 10;
}
