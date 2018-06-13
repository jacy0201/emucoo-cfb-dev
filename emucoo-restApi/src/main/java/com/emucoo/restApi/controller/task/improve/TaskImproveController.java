package com.emucoo.restApi.controller.task.improve;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.task.TaskImproveService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Shayne.Wang
 * @date 2018-06-12
 */
@RestController
@RequestMapping("/api/task/improve")
public class TaskImproveController extends AppBaseController {

    @Autowired
    private TaskImproveService taskImproveService;

    @PostMapping("/save")
    public AppResult<String> save(@RequestBody ParamVo<TaskImproveVo> base) {
        TaskImproveVo vo = base.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        taskImproveService.createImproveTask(vo, user);
        return success("");
    }

    @PostMapping("/submit")
    public AppResult<String> submit(@RequestBody ParamVo<TaskImproveSubmitIn> base) {
        TaskImproveSubmitIn vo = base.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        taskImproveService.submitImproveTask(vo, user);
        return success("");
    }

    @PostMapping("/audit")
    public AppResult<String> audit(@RequestBody ParamVo<TaskImproveAuditIn> base) {
        TaskImproveAuditIn vo = base.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        taskImproveService.auditImproveTask(vo, user);
        return success("");
    }

    @PostMapping("/detail")
    public AppResult<TaskImproveDetailOut> detail(@RequestBody ParamVo<TaskImproveDetailIn> base) {
        TaskImproveDetailIn vo = base.getData();
        TaskImproveDetailOut out = taskImproveService.viewImproveTaskDetail(vo);
        return success(out);
    }

    @PostMapping("/getChancePointInfo")
    public TaskChancePointInfo getChancePointInfo(@RequestBody Map map) {
        System.out.println(new Gson().toJson(map));
        return new TaskChancePointInfo();

    }

}
