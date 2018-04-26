package com.emucoo.service.report;

import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.ReportVo;
import com.emucoo.dto.modules.report.SaveReportIn;
import com.emucoo.model.SysUser;

/**
 * Created by sj on 2018/4/24.
 */
public interface ReportService {
    ReportVo getReport(SysUser user, GetReportIn reportIn);

    void saveReport(SysUser user, SaveReportIn reportIn);
}
