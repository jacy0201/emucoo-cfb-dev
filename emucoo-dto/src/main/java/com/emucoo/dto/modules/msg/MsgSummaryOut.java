package com.emucoo.dto.modules.msg;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/5/30.
 */
@Data
@ApiModel(value = "返回数据集")
public class MsgSummaryOut {
    List<MsgSummaryModuleVo> msgModuleList;
}
