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


@Api(description = "常规任务管理")
@RestController
@RequestMapping("/api/task/repair")
public class TaskRepairController extends AppBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepairService taskRepairService;


    @ApiOperation(value="常规任务列表", httpMethod = "POST")
    @PostMapping("/detail")
    public AppResult<TRepairWork> detail(@RequestBody ParamVo<Long> param) {
        long workId = param.getData();
        TRepairWork repairWork = taskRepairService.detail(workId);

        return success(repairWork);

    }
}
