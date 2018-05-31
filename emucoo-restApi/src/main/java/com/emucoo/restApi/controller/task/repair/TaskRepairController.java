package com.emucoo.restApi.controller.task.repair;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.TRepairWork;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.service.sys.UserService;
import com.emucoo.service.task.TaskRepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(description = "常规任务管理")
@RestController
@RequestMapping("/api/task/repair")
public class TaskRepairController extends AppBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepairService taskRepairService;

    @ApiOperation(value = "列出当前店铺当前月份的维修单")
    @PostMapping("/list")
    public AppResult<List<TRepairWork>> list(@RequestBody ParamVo<Long> param) {
        Long shopId = param.getData();
        List<TRepairWork> works = taskRepairService.listRepairWorksByShopId(shopId);
        return success(works);
    }

    @ApiOperation(value="维修任务详情", httpMethod = "POST")
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
    @PostMapping
    public AppResult<String> finish(@RequestBody ParamVo<TRepairWork> param) {
        TRepairWork work = param.getData();
        taskRepairService.finishRepairWork(work);
        return AppResult.successRes("完成维修任务");
    }

    @ApiOperation(value = "验收维修任务", httpMethod = "POST")
    @PostMapping
    public AppResult<String> audit(@RequestBody ParamVo<TRepairWork> param) {
        TRepairWork work = param.getData();
        taskRepairService.auditRepairWork(work);
        return AppResult.successRes("审核维修任务完成");
    }

}
