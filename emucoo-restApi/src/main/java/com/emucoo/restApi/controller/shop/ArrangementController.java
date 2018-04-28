package com.emucoo.restApi.controller.shop;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.report.*;
import com.emucoo.dto.modules.shop.*;
import com.emucoo.model.CheckSheet;
import com.emucoo.model.SysUser;
import com.emucoo.model.TFormMain;
import com.emucoo.model.TRemind;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.form.FormService;
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

	@Autowired
	private FormService formService;

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
		List<TFormMain> sheets = formService.listForm();
		CheckSheetVo_O voo = new CheckSheetVo_O();
		List<CheckSheetVo> vos = new ArrayList<CheckSheetVo>();
		for (TFormMain sheet : sheets) {
			CheckSheetVo vo = new CheckSheetVo();
			vo.setChecklistID(sheet.getId());
			vo.setChecklistName(sheet.getName());
			vo.setSourceType(0);
			vo.setSourceName("");
			vos.add(vo);
		}
		voo.setChecklistArr(vos);

		return success(voo);
	}


	@PostMapping("/save")
	public AppResult<String> save(@RequestBody ParamVo<PlanArrangeAddIn> base, HttpServletRequest request) {
		PlanArrangeAddIn vo = base.getData();
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		planArrangeService.save(vo, user);
		return success("success");
	}


	@PostMapping("/edit")
	public AppResult<String> edit(@RequestBody ParamVo<PlanArrangeEditIn> base, HttpServletRequest request) {
		PlanArrangeEditIn vo = base.getData();
		checkParam(vo.getPatrolShopArrangeID(), "巡店安排id不能为空！");
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		planArrangeService.edit(vo, user);
		return success("success");
	}

	@PostMapping("/detail")
	public AppResult<PatrolShopArrangeDetailVO> detail(@RequestBody ParamVo<PatrolShopArrangeDetailIn> base) {
		PatrolShopArrangeDetailIn vo = base.getData();
		checkParam(vo.getPatrolShopArrangeID(), "巡店安排id不能为空！");
		PatrolShopArrangeDetailVO detail = planArrangeService.detail(vo);
		return success(detail);
	}


	@PostMapping("/delete")
	public AppResult<String> delete(@RequestBody ParamVo<PlanArrangeDeleteIn> base, HttpServletRequest request) {
		PlanArrangeDeleteIn vo = base.getData();
		checkParam(vo.getPatrolShopArrangeID(), "巡店安排id不能为空！");
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		planArrangeService.delete(vo, user);
		return success("success");
	}

	@PostMapping("/patrolShop")
	public AppResult<String> patrolShopInfoIn(@RequestBody ParamVo<PatrolShopInfoIn> base, HttpServletRequest request) {
		PatrolShopInfoIn vo = base.getData();
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		planArrangeService.patrolShop(vo, user);
		return success("success");

	}

}
