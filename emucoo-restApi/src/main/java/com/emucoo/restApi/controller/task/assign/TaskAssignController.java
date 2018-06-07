package com.emucoo.restApi.controller.task.assign;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.sys.UserService;
import com.emucoo.service.task.LoopWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task/assign")
public class TaskAssignController extends AppBaseController {

	@Autowired
    private LoopWorkService loopWorkService;

	@Autowired
    private UserService userService;

	/**
     *  取得防止重复提交的token
	 */
    @PostMapping("/fetchSubmitToken")
	public AppResult<SubmitTokenVo_O> fetchSubmitToken() {
		SubmitTokenVo_O submitToken = new SubmitTokenVo_O();
		submitToken.setSubmitToken(UserTokenManager.getInstance().produceReSubmitToken());
	    return success(submitToken);
    }

    /**
     * 取联系人列表
     * @param params
     * @return
     */
    @PostMapping("/fetchContacts")
    public AppResult<ContactsVo_O> fetchContacts(@RequestBody ParamVo<ContactsVo_I> params) {
	    ContactsVo_I paramIn = params.getData();
        ContactsVo_O result = userService.fetchContacts(paramIn);
        return success(result);

    }

    /**
     * 创建指派任务
     * @param params
     * @return
     */
    @PostMapping("/createTask")
	public AppResult<String> createTask(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<AssignTaskCreationVo_I> params) {
	    AssignTaskCreationVo_I inParam = params.getData();
	    String submitToken = inParam.getSubmitToken();

	    long loginUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);

	    if(UserTokenManager.getInstance().validateToken(submitToken)){
	    	loopWorkService.createAssignTask(inParam, loginUserId);
	    	UserTokenManager.getInstance().clearReSubmitToken(submitToken);
	    	return success("创建成功");
		} else {
	        return fail(AppExecStatus.SERVICE_ERR, "创建任务失败！");
		}
	}

    /**
     * 提交指派任务
     * @param params
     * @return
     */
    @PostMapping("/submitTask")
    public AppResult<String> submitTask(@RequestBody ParamVo<AssignTaskSubmitVo_I> params) {
        AssignTaskSubmitVo_I atsi = params.getData();
        loopWorkService.submitAssignTask(atsi);
        return success("提交成功。");
    }

    /**
     * 审核指派任务
     * @param params
     * @return
     */
    @PostMapping("/auditTask")
    public AppResult<String> auditTask(@RequestBody ParamVo<AssignTaskAuditVo_I> params) {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        checkParam(user, "没有当前用户！");
        AssignTaskAuditVo_I atai = params.getData();
        loopWorkService.auditAssignTask(user, atai);

        return success("审核成功。");
    }

    /**
     * 查看指派任务的详细信息
     * @param userToken
     * @param params
     * @return
     */
    @PostMapping("/taskDetail")
	public AppResult<AssignTaskDetailVo_O> taskDetail(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<AssignTaskDetailVo_I> params) {
        SysUser loginUser = UserTokenManager.getInstance().currUser(userToken);
        AssignTaskDetailVo_I voi = params.getData();
        int workType = voi.getWorkType();
        String workId = voi.getWorkID();
        String subWorkId = voi.getSubID();

        AssignTaskDetailVo_O result = loopWorkService.viewAssignTaskDetail(workId, subWorkId, workType, loginUser);
        if(result == null){
            return fail(AppExecStatus.SERVICE_ERR, "找不到对应的任务信息！");
        }

        return success(result);
	}



    /**
     * 查看指派任务的历史数据
     * @param param
     * @param taskAssignController
     * @return
     */
    @PostMapping("/viewHistoryData")
    public AppResult<AssignTaskHistoryVo_O> viewHistoryData(@RequestBody ParamVo<AssignTaskHistoryVo_I> param, TaskAssignController taskAssignController) {
        AssignTaskHistoryVo_I ahi = param.getData();
        int workType = ahi.getWorkType();
        String workId = ahi.getWorkID();
        String subWorkId = ahi.getSubID();

        AssignTaskHistoryVo_O result = loopWorkService.viewAssignTaskHistory(workType, workId, subWorkId);

        return taskAssignController.success(result);
    }

    /**
     * 根据类型查看任何一种任务的执行的历史列表
     * @param params
     * @return
     */
    @PostMapping("/viewExeHistory")
    public AppResult<ExeHistoryVo_O> viewExeHistory(@RequestBody ParamVo<ExeHistoryVo_I> params) {
        ExeHistoryVo_I voi = params.getData();
        String workType = voi.getWorkId();
        String workId = voi.getWorkId();

        ExeHistoryVo_O voo = loopWorkService.viewTaskExeHistory(workType, workId);
        return success(voo);
    }


}
