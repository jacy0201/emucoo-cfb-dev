package com.emucoo.service.msg;

import com.emucoo.dto.modules.msg.MsgSummaryOut;
import com.emucoo.model.SysUser;

/**
 * Created by sj on 2018/5/30.
 */
public interface MsgService {

    MsgSummaryOut msgSummary(SysUser user);
}