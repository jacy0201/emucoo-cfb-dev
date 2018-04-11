package com.emucoo.service.shop;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.GetReportListOut;
import com.emucoo.dto.modules.report.GetReportOut;
import com.emucoo.dto.modules.report.ReportBaseInfoIn;
import com.emucoo.dto.modules.report.ReportBaseInfoOut;
import com.emucoo.dto.modules.report.SaveReportIn;
import com.emucoo.dto.modules.report.SaveReportOut;
import com.emucoo.model.LoopPlanSrc;
import com.emucoo.model.shop.TFrontPlan;
import com.emucoo.model.shop.TFrontReport;

import java.util.List;

/**
 * Created by sj on 2018/3/28.
 */
public interface TFrontPlanService {

    SaveReportOut saveReport(SaveReportIn vo);

    GetReportOut getReportById(GetReportIn vo);

    List<GetReportListOut> getReportList();

    ReportBaseInfoOut getReportBaseInfo(ReportBaseInfoIn vo);
}
