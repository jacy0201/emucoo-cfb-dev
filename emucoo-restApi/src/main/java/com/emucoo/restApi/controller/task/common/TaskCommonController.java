package com.emucoo.restApi.controller.task.common;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.sys.UserService;
import com.emucoo.service.task.TaskCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task/common")
public class TaskCommonController extends AppBaseController{
	
    @Autowired
    private TaskCommonService taskCommonService;

    @Autowired
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
//    	long userId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        long userId = 61L;
    	SysUser user = userService.findById(userId);
    	taskCommonService.submitTask(vo, user);
    	return success("");
    }

    @PostMapping("/auditTask")
    public AppResult<String> auditTask(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<TaskCommonAuditIn> base){
    	TaskCommonAuditIn vo = base.getData();
        long userId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        SysUser user = userService.findById(userId);
    	taskCommonService.auditTask(vo, user);
    	return success("");
    }

    @PostMapping("/addExcImgs")
    public AppResult<String> addExcImgs(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<ExecuteImgIn> base){
        SysUser user = UserTokenManager.getInstance().currUser(userToken);
        taskCommonService.editExcImgs(base.getData(), user);
        return success("");
    }
    
    
}
