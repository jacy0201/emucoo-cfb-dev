package com.emucoo.manager.controller;

import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.TDeviceType;
import com.emucoo.service.task.TaskRepairService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "维修管理")
@RestController
@RequestMapping("/repair")
public class RepairManageController extends BaseResource {

    @Autowired
    private TaskRepairService taskRepairService;

    @ApiOperation(value = "设备列表", httpMethod = "POST")
    @PostMapping("/list")
    public ApiResult<PageInfo> list(@RequestBody ParamVo<TDeviceType> paramVo) {
        int pageNm = paramVo.getPageNumber();
        int pageSz = paramVo.getPageSize();

        TDeviceType dvc = paramVo.getData();
        String keyword = dvc.getName();
        long typeId = dvc.getParentTypeId();

        int total = taskRepairService.countDeviceTypes(keyword, typeId);
        List<TDeviceType> dts = taskRepairService.listDeviceTypes(keyword, typeId, pageNm, pageSz);

        PageInfo pageInfo = new PageInfo(dts);
        pageInfo.setTotal(total);
        pageInfo.setPageNum(pageNm);
        pageInfo.setPageSize(pageSz);
        pageInfo.setPages(total % pageSz == 0 ? total / pageSz : total / pageSz + 1);
        return success(pageInfo);
    }

    @ApiOperation(value = "设备类别列表", httpMethod = "POST")
    @PostMapping("/types")
    public ApiResult<List<TDeviceType>> types() {
        List<TDeviceType> types = taskRepairService.listTopDeviceTypes();
        return success(types);
    }

    @ApiOperation(value = "设备详情", httpMethod = "POST")
    @PostMapping("/info")
    public ApiResult<TDeviceType> info(@RequestBody ParamVo<Long> paramVo) {
        long deviceTypeId = paramVo.getData();
        TDeviceType deviceType = taskRepairService.fetchDeviceTypeInfo(deviceTypeId);
        return success(deviceType);
    }

    @ApiOperation(value = "启用设备", httpMethod = "POST")
    @PostMapping("/enable")
    public ApiResult<String> enable(@RequestBody ParamVo<Long> paramVo){
        long deviceTypeId = paramVo.getData();
        taskRepairService.switchDeviceUsable(deviceTypeId, true);
        return success("设备成功启用！");
    }

    @ApiOperation(value = "停用设备", httpMethod = "POST")
    @PostMapping("/disable")
    public ApiResult<String> disable(@RequestBody ParamVo<Long> paramVo){
        long deviceTypeId = paramVo.getData();
        taskRepairService.switchDeviceUsable(deviceTypeId, false);
        return success("设备停用完成！");
    }

    @ApiOperation(value = "删除设备", httpMethod = "POST")
    @PostMapping("/remove")
    public ApiResult<String> remove(@RequestBody ParamVo<Long> paramVo) {
        long deviceTypeId = paramVo.getData();
        taskRepairService.removeDeviceType(deviceTypeId);
        return success("删除设备成功！");
    }

    @ApiOperation(value = "创建编辑设备", httpMethod = "POST")
    @PostMapping("/save")
    public ApiResult<String> save(@RequestBody ParamVo<TDeviceType> paramVo) {
        TDeviceType dvc = paramVo.getData();
        taskRepairService.saveDeviceType(dvc);
        return success("成功保存设备修改！");
    }

    @ApiOperation(value = "设备分类设置", httpMethod = "POST")
    @PostMapping("/category")
    public ApiResult<String> category(@RequestBody ParamVo<TDeviceType> paramVo) {
        TDeviceType dvc = paramVo.getData();
        taskRepairService.configDeviceCategory(dvc);
        return success("成功设置设备分类！");
    }

    @ApiOperation(value = "设备问题设置", httpMethod = "POST")
    @PostMapping("/problem")
    public ApiResult<String> attachProblem(@RequestBody ParamVo<TDeviceType> paramVo) {
        TDeviceType dvc = paramVo.getData();
        taskRepairService.attachDeviceProblem(dvc);
        return success("成功设置设备问题！");
    }


}
