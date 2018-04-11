package com.emucoo.restApi.controller.task.improve;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.TaskChancePointInfo;
import com.emucoo.dto.modules.task.TaskImproveAuditIn;
import com.emucoo.dto.modules.task.TaskImproveDetailIn;
import com.emucoo.dto.modules.task.TaskImproveDetailOut;
import com.emucoo.dto.modules.task.TaskImproveSubmitIn;
import com.emucoo.dto.modules.task.TaskImproveVo;
import com.emucoo.model.User;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.task.TaskDesignService;
import com.emucoo.service.task.TaskImproveService;
import com.emucoo.service.task.TaskTemplateService;
import com.emucoo.service.task.TaskTypeService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/task/improve")
public class TaskImproveController extends AppBaseController {

	@Resource
	private TaskDesignService taskDesignService;

	@Resource
	private TaskTemplateService taskTemplateService;

	@Resource
	private TaskTypeService taskTypeService;

	@Resource
	private TaskImproveService taskImproveService;

	@PostMapping("/save")
	public AppResult<String> save(@RequestBody ParamVo<TaskImproveVo> base) {
		TaskImproveVo vo = base.getData();
		User user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		taskImproveService.save(vo, user);
		return success("");
	}

	@PostMapping("/submit")
	public AppResult<String> submit(@RequestBody ParamVo<TaskImproveSubmitIn> base) {
		TaskImproveSubmitIn vo = base.getData();
		User user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		taskImproveService.submit(vo, user);
		return success("");
	}

	@PostMapping("/audit")
	public AppResult<String> audit(@RequestBody ParamVo<TaskImproveAuditIn> base) {
		TaskImproveAuditIn vo = base.getData();
		User user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		taskImproveService.audit(vo, user);
		return success("");
	}

	@PostMapping("/detail")
	public AppResult<TaskImproveDetailOut> detail(@RequestBody ParamVo<TaskImproveDetailIn> base) {
		TaskImproveDetailIn vo = base.getData();
		TaskImproveDetailOut out = taskImproveService.detail(vo);
		return success(out);
	}

	@PostMapping("/getChancePointInfo")
	public TaskChancePointInfo getChancePointInfo(@RequestBody Map map) {
		System.out.println(new Gson().toJson(map));
		return new TaskChancePointInfo();

	}

}
