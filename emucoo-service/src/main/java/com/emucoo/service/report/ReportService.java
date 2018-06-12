package com.emucoo.service.report;

import com.emucoo.dto.modules.RYGForm.RYGForm;
import com.emucoo.dto.modules.RYGReport.RYGReportVo;
import com.emucoo.dto.modules.abilityReport.AbilityReportVo;
import com.emucoo.dto.modules.report.GetOpptIn;
import com.emucoo.dto.modules.report.GetOpptOut;
import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.ReportVo;
import com.emucoo.dto.modules.report.SaveReportIn;
import com.emucoo.model.SysUser;

/**
 * Created by sj on 2018/4/24.
 */
public interface ReportService {
    ReportVo getReportPreview(SysUser user, GetReportIn reportIn);

    Long saveReport(SysUser user, ReportVo reportIn);

    ReportVo findReportInfoById(SysUser user, GetReportIn reportIn);

    GetOpptOut findOpptInfoById(SysUser user, GetOpptIn opptId);

    AbilityReportVo findAbilityReportInfo(SysUser user, GetReportIn reportIn);

    RYGReportVo getRYGReportPreview(SysUser user, RYGForm reportIn);

    Long saveRYGReport(SysUser user, RYGReportVo reportIn);

    RYGReportVo findRYGReportInfoById(SysUser user, GetReportIn reportIn);
}
