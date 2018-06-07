package com.emucoo.restApi.controller.task.repair;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.RepairWorkParam;
import com.emucoo.model.TDeviceProblem;
import com.emucoo.model.TDeviceType;
import com.emucoo.model.TRepairWork;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.sys.UserService;
import com.emucoo.service.task.TaskRepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(description = "维修任务")
@RestController
@RequestMapping("/api/task/repair")
public class TaskRepairController extends AppBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepairService taskRepairService;

    @ApiOperation(value = "列出当前店铺当前月份的维修单")
    @PostMapping("/list")
    public AppResult<List<TRepairWork>> list(@RequestHeader("userToken") String userToken, @RequestBody ParamVo<RepairWorkParam> param) {
        RepairWorkParam workParam = param.getData();
        long shopId = workParam.getShopId();
        String date = workParam.getDate();
        long userId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        List<TRepairWork> works = taskRepairService.listRepairWorksByShopId(shopId, date, userId);
        return success(works);
    }

    @ApiOperation(value = "维修任务详情", httpMethod = "POST")
    @PostMapping("/detail")
    public AppResult<TRepairWork> detail(@RequestBody ParamVo<Long> param) {
        long workId = param.getData();
        TRepairWork repairWork = taskRepairService.detail(workId);
        return success(repairWork);
    }

    @ApiOperation(value = "创建维修任务", httpMethod = "POST")
    @PostMapping("/create")
    public AppResult<String> create(@RequestBody ParamVo<TRepairWork> param) {
        TRepairWork work = param.getData();
        taskRepairService.createRepairWork(work);
        return AppResult.successRes("创建成功");
    }

    @ApiOperation(value = "修改维修任务", httpMethod = "POST")
    @PostMapping("/modify")
    public AppResult<String> modify(@RequestBody ParamVo<TRepairWork> param) {
        TRepairWork work = param.getData();
        taskRepairService.modifyRepairWork(work);
        return AppResult.successRes("修改成功");
    }

    @ApiOperation(value = "设置预期维修时间", httpMethod = "POST")
    @PostMapping("/expect")
    public AppResult<String> expect(@RequestBody ParamVo<TRepairWork> param) {
        TRepairWork work = param.getData();
        taskRepairService.expectRepairDate(work);
        return AppResult.successRes("设置预期维修时间成功");
    }

    @ApiOperation(value = "完成维修任务", httpMethod = "POST")
    @PostMapping("/finish")
    public AppResult<String> finish(@RequestBody ParamVo<TRepairWork> param) {
        TRepairWork work = param.getData();
        taskRepairService.finishRepairWork(work);
        return AppResult.successRes("完成维修任务");
    }

    @ApiOperation(value = "验收维修任务", httpMethod = "POST")
    @PostMapping("/audit")
    public AppResult<String> audit(@RequestBody ParamVo<TRepairWork> param) {
        TRepairWork work = param.getData();
        taskRepairService.auditRepairWork(work);
        return AppResult.successRes("审核维修任务完成");
    }

    @ApiOperation(value = "获取设备列表", httpMethod = "POST")
    @PostMapping("/device")
    public AppResult<List<TDeviceType>> device(@RequestBody ParamVo<Long> param) {
        long parentId = param.getData() == null ? 0 : param.getData();
        List<TDeviceType> children = taskRepairService.listChildrenDeviceType(parentId);
        return success(children);
    }

    @ApiOperation(value = "获取设备的问题", httpMethod = "POST")
    @PostMapping("/problems")
    public AppResult<List<TDeviceProblem>> problems(@RequestBody ParamVo<Long> param) {
        if (param.getData() == null) {
            return AppResult.paramErrorRes("没有这样的deviceId！");
        }
        long deviceId = param.getData();
        List<TDeviceProblem> problems = taskRepairService.listDeviceProblems(deviceId);
        return success(problems);
    }

}
