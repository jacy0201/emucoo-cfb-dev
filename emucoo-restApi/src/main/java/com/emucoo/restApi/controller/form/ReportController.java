package com.emucoo.restApi.controller.form;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.form.FormIn;
import com.emucoo.dto.modules.form.FormOut;
import com.emucoo.dto.modules.report.GetOpptIn;
import com.emucoo.dto.modules.report.GetOpptOut;
import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.GetReportListOut;
import com.emucoo.dto.modules.report.GetReportOut;
import com.emucoo.dto.modules.report.ReportVo;
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

    /**
     * 报告预览
     * @param params
     * @param request
     * @return
     */
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

    /**
     *  保存报告
     * @param params
     * @param request
     * @return
     */
    @PostMapping(value = "saveReport")
    public AppResult<Long> saveReport(@RequestBody ParamVo<ReportVo> params, HttpServletRequest request) {
        ReportVo reportIn = params.getData();
        checkParam(reportIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(reportIn.getChecklistID(), "表单id不能为空！");
        checkParam(reportIn.getShopID(), "店铺id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        Long reportId = reportService.saveReport(user, reportIn);
        return success(reportId);
    }

    /**
     * 根据报告id查询报告详情
     * @param params
     * @param request
     * @return
     */
    @PostMapping(value = "findReportInfoById")
    public AppResult<ReportVo> findReportInfoById(@RequestBody ParamVo<GetReportIn> params, HttpServletRequest request) {
        GetReportIn reportIn = params.getData();
        checkParam(reportIn.getReportID(), "表单id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        ReportVo reportOut = reportService.findReportInfoById(user, reportIn);
        return success(reportOut);
    }

    @PostMapping(value = "findOpptInfoById")
    public AppResult<GetOpptOut> findOpptInfoById(@RequestBody ParamVo<GetOpptIn> params, HttpServletRequest request) {
        GetOpptIn getOpptIn = params.getData();
        checkParam(getOpptIn.getReportID(), "报告id不能为空！");
        checkParam(getOpptIn.getOpptID(), "机会点id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        GetOpptOut getOpptOut = reportService.findOpptInfoById(user, getOpptIn);
        return success(getOpptOut);
    }

    /*@PostMapping("/getReportList")
    public AppResult<List<GetReportListOut>> getReportList(@RequestBody ParamVo<GetReportIn> base) {
        GetReportIn vo = base.getData();
        List<GetReportListOut> reportList = tFrontPlanService.getReportList();
        return success(reportList);
    }*/

}
