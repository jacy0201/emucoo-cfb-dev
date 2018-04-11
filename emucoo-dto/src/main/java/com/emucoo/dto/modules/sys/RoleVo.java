package com.emucoo.dto.modules.sys;

import lombok.Data;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/30 15:11
 * @modified by:
 */
@Data
public class RoleVo {
  /**
   * 父ID
   */
  private Long parentId = 0L;

  private Long id;

  /**
   * 角色名称
   */
  private String name;

  /**
   * 角色备注
   */
  private String remark;


  private String lbIds;

  private String groupIds;
}
