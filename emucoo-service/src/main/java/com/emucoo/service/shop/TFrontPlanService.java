package com.emucoo.service.shop;

import com.emucoo.dto.modules.report.*;

import java.util.List;

public interface TFrontPlanService {
    SaveReportOut saveReport(SaveReportIn vo);
    ReportBaseInfoOut getReportBaseInfo(ReportBaseInfoIn vo);
    GetReportOut getReportById(GetReportIn vo);
    List<GetReportListOut> getReportList();
}
