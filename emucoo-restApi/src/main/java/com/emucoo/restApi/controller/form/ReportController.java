package com.emucoo.restApi.controller.form;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.abilityReport.AbilityReportVo;
import com.emucoo.dto.modules.report.GetOpptIn;
import com.emucoo.dto.modules.report.GetOpptOut;
import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.ReportVo;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.report.ReportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
        ReportVo reportOut = reportService.getReportPreview(user, reportIn);
        return success(reportOut);
    }

    /**
     *  保存报告
     * @param params
     * @param request
     * @return
     */
    @PostMapping(value = "saveReport")
    public AppResult<Map> saveReport(@RequestBody ParamVo<ReportVo> params, HttpServletRequest request) {
        ReportVo reportIn = params.getData();
        checkParam(reportIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(reportIn.getChecklistID(), "表单id不能为空！");
        checkParam(reportIn.getShopID(), "店铺id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        Long reportId = reportService.saveReport(user, reportIn);
        HashMap<String, Long> map = new HashMap<>();
        map.put("reportID", reportId);
        return success(map);
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
        checkParam(getOpptIn.getChanceID(), "机会点id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        GetOpptOut getOpptOut = reportService.findOpptInfoById(user, getOpptIn);
        return success(getOpptOut);
    }

    @ApiOperation(value = "查询能力模型报告")
    @PostMapping(value = "findAbilityReportInfo")
    public AppResult<AbilityReportVo> findAbilityReportInfo(@RequestBody ParamVo<GetReportIn> params, HttpServletRequest request) {
        GetReportIn reportIn = params.getData();
        checkParam(reportIn.getReportID(), "表单id不能为空！");
        //checkParam(reportIn.getFormType(), "表单类型不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        AbilityReportVo report = reportService.findAbilityReportInfo(user, reportIn);
        return success(report);
    }

}
