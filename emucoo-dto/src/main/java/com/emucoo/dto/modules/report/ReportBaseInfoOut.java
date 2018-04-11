package com.emucoo.dto.modules.report;

import lombok.Data;

/**
 * Created by sj on 2018/3/30.
 */
@Data
public class ReportBaseInfoOut {
    //打表人名称
    private String inspectorName;
    //打表人职位
    private String inspectorPosition;
    //店长
    private String shopownerName;
    //店铺名
    private String shopName;
    //打表日期
    private String checkDate;
    //打表人部门名称
    private String checkDepartmentName;



}
