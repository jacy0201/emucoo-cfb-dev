package com.emucoo.restApi.controller.shop;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.report.*;
import com.emucoo.dto.modules.shop.*;
import com.emucoo.model.CheckSheet;
import com.emucoo.model.TRemind;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.shop.LoopPlanService;
import com.emucoo.service.shop.PlanArrangeService;
import com.emucoo.service.shop.TFrontPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tombaby on 2018/3/25.
 */
@RestController
@RequestMapping("/api/shop/arrange")
public class ArrangementController extends AppBaseController {

	@Autowired
	private LoopPlanService loopPlanService;

	@Autowired
	private PlanArrangeService planArrangeService;

	@Autowired
	private TFrontPlanService tFrontPlanService;

	/**
	 * 获取到期提醒时间类型列表
	 *
	 * @return
	 */
	@PostMapping("/reminders")
	public AppResult<ReminderVo_O> reminders() {
		ReminderVo_O voo = new ReminderVo_O();
		List<ReminderVo> vos = new ArrayList<ReminderVo>();
		List<TRemind> listRemind = planArrangeService.listRemind();
		Iterator<TRemind> itRemind = listRemind.iterator();
		while (itRemind.hasNext()) {
			TRemind tRemind = itRemind.next();
			ReminderVo vo = new ReminderVo();
			vo.setRemindType(tRemind.getRemindType());
			vo.setRemindName(tRemind.getRemindName());
			vos.add(vo);
		}
		voo.setRemindArr(vos);
		return success(voo);
	}

	@PostMapping("/sheets")
	public AppResult<CheckSheetVo_O> sheets() {
		List<CheckSheet> sheets = loopPlanService.listCheckSheets();
		CheckSheetVo_O voo = new CheckSheetVo_O();
		List<CheckSheetVo> vos = new ArrayList<CheckSheetVo>();
		for (CheckSheet sheet : sheets) {
			CheckSheetVo vo = new CheckSheetVo();
			vo.setChecklistID(sheet.getId());
			vo.setChecklistName(sheet.getName());
			vo.setSourceType(sheet.getSourceType());
			vo.setSourceName(sheet.getSourceName());
			vos.add(vo);
		}
		voo.setChecklistArr(vos);

		return success(voo);
	}

	@PostMapping("/saveReport")
	public AppResult<SaveReportOut> saveReport(@RequestBody ParamVo<SaveReportIn> base, HttpServletRequest request) {
		SaveReportIn vo = base.getData();
		checkParam(vo.getPatrolShopArrangeID(), "巡店安排id不能为空！");
		checkParam(vo.getReportValue(), "报告内容不能为空！");
		String userToken = request.getHeader("userToken");
		checkParam(userToken, "用户token不能为空！");
		Long auditUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
		vo.setReporterID(auditUserId.toString());
		SaveReportOut saveReportOut = tFrontPlanService.saveReport(vo);
		return success(saveReportOut);
	}

	@PostMapping("/getReportBaseInfo")
	public AppResult<ReportBaseInfoOut> getReportBaseInfo(@RequestBody ParamVo<ReportBaseInfoIn> base, HttpServletRequest request) {
		ReportBaseInfoIn vo = base.getData();
		checkParam(vo.getPatrolShopArrangeID(), "巡店安排id不能为空！");
		checkParam(vo.getShopID(), "店铺id不能为空！");
		String userToken = request.getHeader("userToken");
		checkParam(userToken, "用户token不能为空！");
		Long auditUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
		vo.setReporterId(auditUserId.toString());
		ReportBaseInfoOut reportBaseInfoOut = tFrontPlanService.getReportBaseInfo(vo);

		return success(reportBaseInfoOut);
	}

	@PostMapping("/getReportById")
	public AppResult<GetReportOut> getReportById(@RequestBody ParamVo<GetReportIn> base) {
		GetReportIn vo = base.getData();
		checkParam(vo.getReportID(), "报告id不能为空！");
		GetReportOut report = tFrontPlanService.getReportById(vo);
		return success(report);
	}

	@PostMapping("/getReportList")
	public AppResult<List<GetReportListOut>> getReportList(@RequestBody ParamVo<GetReportIn> base) {
		GetReportIn vo = base.getData();
		List<GetReportListOut> reportList = tFrontPlanService.getReportList();
		return success(reportList);
	}

	@PostMapping("/save")
	public AppResult<String> save(@RequestBody ParamVo<PlanArrangeAddIn> base) {
		PlanArrangeAddIn vo = base.getData();
		planArrangeService.save(vo);
		return success("");
	}



	@PostMapping("/edit")
	public AppResult<String> edit(@RequestBody ParamVo<PlanArrangeEditIn> base) {
		PlanArrangeEditIn vo = base.getData();
		planArrangeService.edit(vo);
		return success("");
	}

	@PostMapping("/delete")
	public AppResult<String> delete(@RequestBody ParamVo<PlanArrangeDeleteIn> base) {
		PlanArrangeDeleteIn vo = base.getData();
		planArrangeService.delete(vo);
		return success("");
	}

	@PostMapping("/patrolShop")
	public AppResult<String> patrolShopInfoIn(@RequestBody ParamVo<PatrolShopInfoIn> base) {
		PatrolShopInfoIn vo = base.getData();
		int i = planArrangeService.patrolShop(vo);
		if (i == 1) {
			return success("");
		} else {
			return fail(AppExecStatus.FAIL, "巡店安排不存在！");
		}
	}

}
