package com.emucoo.manager.controller;

import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.form.OpptDetailOut;
import com.emucoo.model.TOpportunity;
import com.emucoo.service.manage.ChancePointService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "机会点管理")
@RestController
@RequestMapping(value = "chancePoint")
public class ChancePointController extends BaseResource {

    @Autowired
    private ChancePointService chancePointService;

    @ApiOperation(value="机会点列表", httpMethod = "POST")
    @PostMapping("/list")
    public ApiResult<PageInfo> listChancePoint(@RequestBody ParamVo<TOpportunity> param) {
        String keyword = param.getData()==null?"":param.getData().getName();
        int pageNm = param.getPageNumber();
        int pageSz = param.getPageSize();
        int total = chancePointService.countChancePointsByNameKeyword(keyword);
        List<TOpportunity> opptList = chancePointService.listChancePointsByNameKeyword(keyword, pageNm, pageSz);
        PageInfo pageInfo = new PageInfo<>(opptList);
        pageInfo.setPageSize(pageSz);
        pageInfo.setPageNum(pageNm);
        pageInfo.setTotal(total);
        return success(pageInfo);
    }

    @ApiOperation(value = "编辑机会点", httpMethod = "POST")
    @PostMapping("/edit")
    public ApiResult<TOpportunity> editChancePoint(@RequestBody ParamVo<TOpportunity> param) {
        TOpportunity oppt = param.getData();
        if(oppt == null)
            return fail("参数错误.");
        Long id = oppt.getId();
        TOpportunity opportunity = chancePointService.fetchChancePointById(id);
        return success(opportunity);
    }

    @ApiOperation(value = "创建机会点", httpMethod = "POST")
    @PostMapping("/create")
    public ApiResult<String> createChancePoint(@RequestBody ParamVo<TOpportunity> param) {
        TOpportunity opportunity = param.getData();
        if(opportunity == null)
            return fail("参数错误.");
        if(chancePointService.judgeExisted(opportunity))
            return fail("该机会点已经存在");
        chancePointService.createChancePoint(opportunity, 0L);
        return success("ok");
    }

    @ApiOperation(value = "更新机会点", httpMethod = "POST")
    @PostMapping("/update")
    public ApiResult<String> updateChancePoint(@RequestBody ParamVo<TOpportunity> param) {
        TOpportunity opportunity = param.getData();
        if(opportunity == null)
            return fail("参数错误.");
        chancePointService.updateChancePoint(opportunity, 0L);
        return success("ok");
    }

    @ApiOperation(value = "删除机会点", httpMethod = "POST")
    @PostMapping("/delete")
    public ApiResult<String> deleteChancePoint(@RequestBody ParamVo<List<Long>> param) {
        List<Long> ids = param.getData();
        if(ids == null || ids.size() == 0)
            return fail("参数错误.");
        chancePointService.deleteChancePoints(ids);
        return success("ok");
    }

    @ApiOperation(value = "批量启用", httpMethod = "POST")
    @PostMapping("/enable")
    public ApiResult<String> enableChancePoints(@RequestBody ParamVo<List<Long>> param) {
        List<Long> ids = param.getData();
        if(ids == null || ids.size() == 0)
            return fail("参数错误.");
        chancePointService.enableChancePoints(ids);
        return success("ok");
    }

    @ApiOperation(value = "批量停用", httpMethod = "POST")
    @PostMapping("/disable")
    public ApiResult<String> disableChancePoints(@RequestBody ParamVo<List<Long>> param) {
        List<Long> ids = param.getData();
        if(ids == null || ids.size() == 0)
            return fail("参数错误.");
        chancePointService.disableChancePoints(ids);
        return success("ok");
    }

    @ApiOperation(value = "机会点详情", httpMethod = "POST")
    @PostMapping("/detail")
    public ApiResult<OpptDetailOut> detail(@RequestBody ParamVo<TOpportunity> param) {
        OpptDetailOut out = chancePointService.fetchDetail(param.getData());
        return success(out);
    }

}
