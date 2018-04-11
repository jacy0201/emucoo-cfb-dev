package com.emucoo.dto.modules.sys;

import lombok.Data;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/30 15:11
 * @modified by:
 */
@Data
public class RoleVo_inDpt extends RoleVo{
   private Long dptId;
   private Long dptLbId  = -1L;
   private Long roleLbId = -1L;
}
