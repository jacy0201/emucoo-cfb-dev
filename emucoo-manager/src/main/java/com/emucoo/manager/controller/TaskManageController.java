package com.emucoo.manager.controller;

import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.TaskParameterVo;
import com.emucoo.model.SysPost;
import com.emucoo.service.task.TaskCommonService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/task")
public class TaskManageController extends BaseResource {

    @Autowired
    private TaskCommonService taskCommonService;

    @ApiOperation(value="常规任务列表", httpMethod = "POST")
    @PostMapping("/list")
    public ApiResult<PageInfo> listTasks(@RequestBody ParamVo<TaskParameterVo> param) {

        int pageNm = param.getPageNumber();
        int pageSz = param.getPageSize();
        TaskParameterVo dataIn = param.getData();
        String keyword =  dataIn.getName();
        Boolean usage = dataIn.getIsUse();

        int total = taskCommonService.countCommonTaskByName(keyword, usage);
        List<TaskParameterVo> taskList = taskCommonService.listCommonTaskByName(keyword, usage, pageNm, pageSz);

        PageInfo pageInfo = new PageInfo(taskList);
        pageInfo.setPageSize(pageSz);
        pageInfo.setPageNum(pageNm);
        pageInfo.setTotal(total);
        return success(pageInfo);
    }

    @ApiOperation(value = "创建常规任务", httpMethod = "POST")
    @PostMapping("/create")
    public ApiResult<String> create(@RequestBody ParamVo<TaskParameterVo> param) {
        TaskParameterVo data = param.getData();
        if(data == null)
            return fail("参数错误!");
        if(taskCommonService.judgeExistCommonTask(data))
            return fail("同名任务已经存在!");
        taskCommonService.createCommonTask(data);
        return success("Ok");
    }

    @ApiOperation(value = "编辑任务", httpMethod = "POST")
    @PostMapping("/edit")
    public ApiResult<String> edit(@RequestBody ParamVo<TaskParameterVo> param) {
        TaskParameterVo data = param.getData();
        if(data == null)
            return fail("参数错误!");
        taskCommonService.saveCommonTask(data);
        return success("ok");
    }

    @ApiOperation(value = "删除任务", httpMethod = "POST")
    @PostMapping("/remove")
    public ApiResult<String> remove(@RequestBody ParamVo<List<Long>> param) {
        List<Long> data = param.getData();
        if(data == null || data.size() < 1)
            return fail("参数错误!");
        taskCommonService.removeCommonTask(data);
        return success("Ok");
    }

    @ApiOperation(value = "批量启用", httpMethod = "POST")
    @PostMapping("/enable")
    public ApiResult<String> enable(@RequestBody ParamVo<List<Long>> param) {
        List<Long> data = param.getData();
        if(data == null || data.size() < 1)
            return fail("参数错误!");
        taskCommonService.switchCommonTask(data, true);
        return success("Ok");
    }

    @ApiOperation(value = "批量禁用", httpMethod = "POST")
    @PostMapping("/disable")
    public ApiResult<String> disable(@RequestBody ParamVo<List<Long>> param) {
        List<Long> data = param.getData();
        if(data == null || data.size() < 1)
            return fail("参数错误!");
        taskCommonService.switchCommonTask(data, false);
        return success("Ok");
    }

    @ApiOperation(value = "设置任务详情", httpMethod = "POST")
    @PostMapping("/detail")
    public ApiResult<TaskParameterVo> detail(@RequestBody ParamVo<Long> param) {
        Long taskId = param.getData();
        if(taskId == null)
            return fail("参数错误!");
        TaskParameterVo vo = taskCommonService.detailCommonTask(taskId);
        return success(vo);
    }

    @ApiOperation(value = "保存任务详细设置", httpMethod = "POST")
    @PostMapping("/config")
    public ApiResult<String> config(@RequestBody ParamVo<TaskParameterVo> param) {
        TaskParameterVo data = param.getData();
        if(data == null)
            return fail("参数错误!");
        taskCommonService.configCommonTask(data);
        return success("Ok");
    }

    @ApiOperation(value = "根据部门id查出岗位", httpMethod = "POST")
    @PostMapping("/listDeptPos")
    public ApiResult<List<SysPost>> listDeptPos(@RequestBody ParamVo<Long> param) {
        Long deptId = param.getData();
        if(deptId == null)
            return fail("参数错误!");
        List<SysPost> positions = taskCommonService.listPositionsOfDept(deptId);
        return success(positions);
    }



}
