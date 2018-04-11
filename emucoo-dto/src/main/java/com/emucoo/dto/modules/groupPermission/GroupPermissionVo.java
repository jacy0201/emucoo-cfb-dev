package com.emucoo.dto.modules.groupPermission;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author LXC
 * @date 2017/5/10
 */
@Data
public class GroupPermissionVo {

  private Long id;

  private Long roleId;

  private String name;

  private Boolean isUse;

  private String description;

  private List<GroupOfPermissionVo> list;
}
