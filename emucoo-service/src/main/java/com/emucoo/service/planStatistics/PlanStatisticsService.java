package com.emucoo.service.planStatistics;

import com.emucoo.dto.modules.statistics.plan.GeneratePlanReportIn;
import com.emucoo.model.RVRReportStatistics;
import com.emucoo.model.TFormType;
import java.util.List;

/**
 * Created by sj on 2018/5/31.
 */
public interface PlanStatisticsService {
    List<RVRReportStatistics> generatePlanReport(GeneratePlanReportIn param);

    List<TFormType> findFormTypeValues(Long formId);
}
