package com.emucoo.restApi.controller.center;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.center.TaskQuery;
import com.emucoo.dto.modules.center.TaskVO;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.center.UserCenterService;
import com.emucoo.service.sys.SysUserService;
import com.emucoo.service.task.LoopWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.threads.TaskQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author Jacy
 * 个人中心
 */
@RestController
@Api(description = "个人中心接口")
@RequestMapping("/api/center/")
public class UserCenterController extends AppBaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserCenterService userCenterService;

    /**
     * 编辑资料
     */
    @ApiOperation(value="编辑资料")
    @PostMapping("/editUser")
    public AppResult editUser(@RequestBody ParamVo<SysUser> params){
        SysUser sysUser=params.getData();
        SysUser currUser = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        if(null==sysUser.getId()){return fail(AppExecStatus.INVALID_PARAM,"id 不能为空!");}
        sysUser.setModifyTime(new Date());
        sysUser.setModifyUserId(currUser.getId());
        sysUserService.editUser(sysUser);
        //删除redis 缓存
    //    redisClient.delete(ISystem.IUSER.USER + sysUser.getId());
        return success("success");
    }


    /**
     * 我执行的任务
     */
    @ApiOperation(value="我执行的任务")
    @PostMapping("/executeTaskList")
    public AppResult executeTaskList(@RequestBody ParamVo<TaskQuery> params){
        TaskQuery taskQuery=params.getData();
        SysUser currUser = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        List<TaskVO> list=userCenterService.executeTaskList(taskQuery.getMonth(),currUser.getId());
        return success(list);
    }

    /**
     * 我审核的任务
     */
    @ApiOperation(value="我审核的任务")
    @PostMapping("/auditTaskList")
    public AppResult auditTaskList(@RequestBody ParamVo<TaskQuery> params){
        TaskQuery taskQuery=params.getData();
        SysUser currUser = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        List<TaskVO> list=userCenterService.auditTaskList(taskQuery.getMonth(),currUser.getId());
        return success(list);
    }

    /**
     * 我创建的任务
     */
    @ApiOperation(value="我创建的任务")
    @PostMapping("/createTaskList")
    public AppResult createTaskList(@RequestBody ParamVo<TaskQuery> params){
        TaskQuery taskQuery=params.getData();
        SysUser currUser = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        List<TaskVO> list=userCenterService.createTaskList(taskQuery.getMonth(),currUser.getId());
        return success(list);
    }

    /**
     * 我接收的报告
     */
    @ApiOperation(value="我接收的报告")
    @PostMapping("/getReportList")
    public AppResult getReportList(@RequestBody ParamVo<TaskQuery> params){
        TaskQuery taskQuery=params.getData();
        SysUser currUser = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        return success("success");
    }



}
