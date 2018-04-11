package com.emucoo.dto.modules.report;

import com.emucoo.common.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by sj on 2018/3/26.
 */
@Data
@ApiModel
public class SaveReportIn extends BaseEntity {
    private String patrolShopArrangeID;
    private String reportValue;
    private String reporterID;
}
