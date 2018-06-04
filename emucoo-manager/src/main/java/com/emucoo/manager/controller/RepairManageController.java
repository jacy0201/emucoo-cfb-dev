package com.emucoo.manager.controller;

import com.emucoo.common.PageInfo;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.TDeviceType;
import com.emucoo.service.task.TaskRepairService;
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
        long typeId = dvc.getTypeId();

        int total = taskRepairService.countDeviceTypes(keyword, typeId);
        List<TDeviceType> dts = taskRepairService.listDeviceTypes(keyword, typeId, pageNm, pageSz);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalCount(total);
        pageInfo.setPageNumber(pageNm);
        pageInfo.setPageSize(pageSz);
        pageInfo.setTotalPage(total % pageSz == 0 ? total / pageSz : total / pageSz + 1);
        pageInfo.setData(dts);
        return success(pageInfo);
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
