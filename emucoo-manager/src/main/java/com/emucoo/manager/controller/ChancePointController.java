package com.emucoo.manager.controller;

import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.TOpportunity;
import com.emucoo.service.manage.ChancePointService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "chancePoint")
public class ChancePointController extends BaseResource {

    @Autowired
    private ChancePointService chancePointService;

    @ApiOperation(value="机会点列表", httpMethod = "POST")
    @PostMapping("/list")
    public ApiResult<List<TOpportunity>> listChancePoint(@RequestBody ParamVo<String> param) {
        String keyword = param.getData();
        int pageNm = param.getPageNumber();
        int pageSz = param.getPageSize();
        List<TOpportunity> opptList = chancePointService.listChancePointsByNameKeyword(keyword, pageNm, pageSz);
        return success(opptList);
    }

    @ApiOperation(value = "编辑机会点", httpMethod = "POST")
    @PostMapping("/edit")
    public ApiResult<TOpportunity> editChancePoint(@RequestBody ParamVo<Long> param) {
        Long id = param.getData();
        TOpportunity opportunity = chancePointService.fetchChancePointById(id);
        return success(opportunity);
    }

    @ApiOperation(value = "创建机会点", httpMethod = "POST")
    @PostMapping("/create")
    public ApiResult<String> createChancePoint(@RequestBody ParamVo<TOpportunity> param) {
        TOpportunity opportunity = param.getData();
        chancePointService.createChancePoint(opportunity);
        return success("ok");
    }

    @ApiOperation(value = "更新机会点", httpMethod = "POST")
    @PostMapping("/update")
    public ApiResult<String> updateChancePoint(@RequestBody ParamVo<TOpportunity> param) {
        TOpportunity opportunity = param.getData();
        chancePointService.updateChancePoint(opportunity);
        return success("ok");
    }

    @ApiOperation(value = "删除机会点", httpMethod = "POST")
    @PostMapping("/delete")
    public ApiResult<String> deleteChancePoint(@RequestBody ParamVo<List<Long>> param) {
        List<Long> ids = param.getData();
        chancePointService.deleteChancePoints(ids);
        return success("ok");
    }

    @ApiOperation(value = "批量启用", httpMethod = "POST")
    @PostMapping("/enable")
    public ApiResult<String> enableChancePoints(@RequestBody ParamVo<List<Long>> param) {
        List<Long> ids = param.getData();
        chancePointService.enableChancePoints(ids);
        return success("ok");
    }

    @ApiOperation(value = "批量停用", httpMethod = "POST")
    @PostMapping("/disable")
    public ApiResult<String> disableChancePoints(@RequestBody ParamVo<List<Long>> param) {
        List<Long> ids = param.getData();
        chancePointService.disableChancePoints(ids);
        return success("ok");
    }

}
