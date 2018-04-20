package com.emucoo.manager.controller;

import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.FormDetail;
import com.emucoo.model.TFormMain;
import com.emucoo.service.manage.FormManageService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(description = "表单管理")
@RestController
@RequestMapping(value = "form")
public class FormManageController extends BaseResource {

    @Autowired
    private FormManageService formManageService;

    @ApiOperation(value = "表单列表", httpMethod = "POST")
    @PostMapping("/list")
    public ApiResult<PageInfo> list(@RequestBody ParamVo<TFormMain> paramVo) {
        int pageNm = paramVo.getPageNumber();
        int pageSz = paramVo.getPageSize();
        String keyword = paramVo.getData()==null?"":paramVo.getData().getName();
        int total = formManageService.countFormsByNameKeyword(keyword);
        List<TFormMain> forms = formManageService.findFormsByNameKeyword(keyword, pageNm, pageSz);
        PageInfo pageInfo = new PageInfo(forms);
        pageInfo.setPageNum(pageNm);
        pageInfo.setPageSize(pageSz);
        pageInfo.setTotal(total);
        return success(pageInfo);
    }

    @ApiOperation(value = "创建表单", httpMethod = "POST")
    @PostMapping("/create")
    public ApiResult<String> create(@RequestBody ParamVo<TFormMain> paramVo) {
        TFormMain form = paramVo.getData();
        if(form == null)
            return fail("parameter is wrong.");
        formManageService.createForm(form, 0L);
        return success("ok");
    }

    @ApiOperation(value = "编辑表单", httpMethod = "POST")
    @PostMapping("/update")
    public ApiResult<String> update(@RequestBody ParamVo<TFormMain> paramVo) {
        TFormMain form = paramVo.getData();
        if(form == null){
            return fail("parameter is wrong.");
        }
        formManageService.updateForm(form);
        return success("ok");
    }

    @ApiOperation(value = "删除表单", httpMethod = "POST")
    @PostMapping("/delete")
    public ApiResult<String> delete(@RequestBody ParamVo<List<Long>> paramVo) {
        List<Long> ids = paramVo.getData();
        if(ids == null || ids.size() == 0){
            return fail("parameter can not be empty.");
        }
        formManageService.deleteForms(ids);
        return success("ok");
    }

    @ApiOperation(value = "启用表单", httpMethod = "POST")
    @PostMapping("/enable")
    public ApiResult<String> enable(@RequestBody ParamVo<List<Long>> paramVo) {
        List<Long> ids = paramVo.getData();
        if(ids == null || ids.size() == 0){
            return fail("parameter can not be empty.");
        }
        formManageService.enableForms(ids);
        return success("ok");
    }

    @ApiOperation(value = "停用表单", httpMethod = "POST")
    @PostMapping("/disable")
    public ApiResult<String> disable(@RequestBody ParamVo<List<Long>> paramVo) {
        List<Long> ids = paramVo.getData();
        if(ids == null || ids.size() == 0)
            return fail("parameter can not be empty.");
        formManageService.disableForms(ids);
        return success("ok");
    }

    @ApiOperation(value = "配置表单", httpMethod = "POST")
    @PostMapping("/detail")
    public ApiResult<TFormMain> detail(@RequestBody ParamVo<TFormMain> paramVo) {
        TFormMain form = paramVo.getData();
        if(form == null)
            return fail("parameter is wrong.");
        TFormMain formDetail = formManageService.fetchFormDetail(form.getId());
        return success(formDetail);
    }

    @ApiOperation(value = "保存表单配置", httpMethod = "POST")
    @PostMapping("/save")
    public ApiResult<String> save(@RequestBody ParamVo<TFormMain> paramVo) {
        TFormMain formDetail = paramVo.getData();
        if(formDetail == null)
            return fail("parameter is wrong.");
        formManageService.saveFormDetail(formDetail);
        return success("ok");
    }


}
