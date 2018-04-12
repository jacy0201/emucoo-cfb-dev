package com.emucoo.restApi.controller.task.common;

import javax.annotation.Resource;

import com.emucoo.dto.modules.task.*;
import com.emucoo.service.task.*;
import org.springframework.web.bind.annotation.*;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.User;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;

@RestController
@RequestMapping("/api/task/common")
public class TaskCommonController extends AppBaseController{
	
    @Resource
    private TaskDesignService taskDesignService;

    @Resource
    private TaskTemplateService taskTemplateService;

    @Resource
    private TaskTypeService taskTypeService;
    
    @Resource
    private TaskCommonService taskCommonService;

    @Resource
    private WorkImgAppendService workImgAppendService;

    @Resource
    private UserService userService;
	 
	@PostMapping("/taskDetail")
	public AppResult<TaskCommonDetailOut> taskDetail(@RequestBody ParamVo<TaskCommonDetailIn> base) {
		TaskCommonDetailIn vo = base.getData();
		TaskCommonDetailOut out = taskCommonService.detail(vo);
		return success(out);
    }

    @PostMapping("/submitTask")
    public AppResult<String> submitTask(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<TaskCommonSubmitIn> base) {
    	TaskCommonSubmitIn vo = base.getData();
    	long userId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
    	User user = userService.findById(userId);
    	taskCommonService.submitTask(vo, user);
    	return success("");
    }

    @PostMapping("/auditTask")
    public AppResult<String> auditTask(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<TaskCommonAuditIn> base){
    	TaskCommonAuditIn vo = base.getData();
        long userId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        User user = userService.findById(userId);
    	taskCommonService.auditTask(vo, user);
    	return success("");
    }

    @PostMapping("/addExcImgs")
    public AppResult<String> addExcImgs(@RequestBody ParamVo<ExecuteImgIn> base){
        workImgAppendService.addExcImgs(base.getData());
        return success("");
    }
    
    
}
