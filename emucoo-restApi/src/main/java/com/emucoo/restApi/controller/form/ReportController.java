package com.emucoo.restApi.controller.form;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.form.FormIn;
import com.emucoo.dto.modules.form.FormOut;
import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.ReportVo;
import com.emucoo.dto.modules.report.SaveReportIn;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sj on 2018/4/24.
 */
@RestController
@RequestMapping(value = "/api/form/report")
public class ReportController extends AppBaseController {

    @Autowired
    private ReportService reportService;

    @PostMapping(value = "reportPreview")
    public AppResult<ReportVo> reportPreview(@RequestBody ParamVo<GetReportIn> params, HttpServletRequest request) {
        GetReportIn reportIn = params.getData();
        checkParam(reportIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(reportIn.getChecklistID(), "表单id不能为空！");
        checkParam(reportIn.getShopID(), "店铺id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        ReportVo reportOut = reportService.getReport(user, reportIn);
        return success(reportOut);
    }

    @PostMapping(value = "saveReport")
    public AppResult<String> saveReport(@RequestBody ParamVo<ReportVo> params, HttpServletRequest request) {
        ReportVo reportIn = params.getData();
        checkParam(reportIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(reportIn.getChecklistID(), "表单id不能为空！");
        checkParam(reportIn.getShopID(), "店铺id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        reportService.saveReport(user, reportIn);
        return success("success");
    }
}
