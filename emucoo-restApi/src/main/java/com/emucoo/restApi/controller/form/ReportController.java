package com.emucoo.restApi.controller.form;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.RYGForm.RYGForm;
import com.emucoo.dto.modules.RYGReport.RYGReportVo;
import com.emucoo.dto.modules.abilityReport.AbilityReportVo;
import com.emucoo.dto.modules.report.GetOpptIn;
import com.emucoo.dto.modules.report.GetOpptOut;
import com.emucoo.dto.modules.report.GetReportIn;
import com.emucoo.dto.modules.report.ReportVo;
import com.emucoo.dto.modules.report.SaveReportOut;
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
    @ApiOperation(value = "RVR类报告预览")
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
    @ApiOperation(value = "保存RVR类报告")
    @PostMapping(value = "saveReport")
    public AppResult<SaveReportOut> saveReport(@RequestBody ParamVo<ReportVo> params, HttpServletRequest request) {
        ReportVo reportIn = params.getData();
        checkParam(reportIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(reportIn.getChecklistID(), "表单id不能为空！");
        checkParam(reportIn.getShopID(), "店铺id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        Long reportId = reportService.saveReport(user, reportIn);
        SaveReportOut reportOut = new SaveReportOut();
        reportOut.setReportID(reportId);
        return success(reportOut);
    }

    /**
     * 根据报告id查询报告详情
     * @param params
     * @param request
     * @return
     */
    @ApiOperation(value = "读取RVR类报告详情")
    @PostMapping(value = "findReportInfoById")
    public AppResult<ReportVo> findReportInfoById(@RequestBody ParamVo<GetReportIn> params, HttpServletRequest request) {
        GetReportIn reportIn = params.getData();
        checkParam(reportIn.getReportID(), "表单id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        ReportVo reportOut = reportService.findReportInfoById(user, reportIn);
        return success(reportOut);
    }

    @ApiOperation(value = "读取RVR类报告机会点详情")
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

    @ApiOperation(value = "红黄绿表单预览")
    @PostMapping(value = "RYGReportPreview")
    public AppResult<RYGReportVo> getRYGReportPreview(@RequestBody ParamVo<RYGForm> params, HttpServletRequest request) {
        RYGForm reportIn = params.getData();
        checkParam(reportIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(reportIn.getChecklistID(), "表单id不能为空！");
        checkParam(reportIn.getShopID(), "店铺id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        RYGReportVo reportOut = reportService.getRYGReportPreview(user, reportIn);
        return success(reportOut);
    }

    @ApiOperation(value = "保存红黄绿报告")
    @PostMapping(value = "saveRYGReport")
    public AppResult<SaveReportOut> saveRYGReport(@RequestBody ParamVo<RYGReportVo> params, HttpServletRequest request) {
        RYGReportVo reportIn = params.getData();
        checkParam(reportIn.getPatrolShopArrangeID(), "巡店安排id不能为空！");
        checkParam(reportIn.getChecklistID(), "表单id不能为空！");
        checkParam(reportIn.getShopID(), "店铺id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        Long reportId = reportService.saveRYGReport(user, reportIn);
        SaveReportOut reportOut = new SaveReportOut();
        reportOut.setReportID(reportId);
        return success(reportOut);
    }

    @ApiOperation(value = "读取红黄绿报告")
    @PostMapping(value = "findRYGReportInfoById")
    public AppResult<RYGReportVo> findRYGReportInfoById(@RequestBody ParamVo<GetReportIn> params, HttpServletRequest request) {
        GetReportIn reportIn = params.getData();
        checkParam(reportIn.getReportID(), "表单id不能为空！");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        RYGReportVo reportOut = reportService.findRYGReportInfoById(user, reportIn);
        return success(reportOut);
    }

}
