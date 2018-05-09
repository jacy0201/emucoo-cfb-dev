package com.emucoo.manager.controller;

import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.manager.config.QiNiuConfig;
import com.emucoo.model.TFormMain;
import com.emucoo.service.manage.FormManageService;
import com.github.pagehelper.PageInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(description = "表单管理")
@RestController
@RequestMapping(value = "form")
public class FormManageController extends BaseResource {

    @Resource(name = "qiniuAuth")
    private Auth qiniuAuth;

    @Autowired
    private QiNiuConfig qiNiuConfig;

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
    @PostMapping("/saveDetail")
    public ApiResult<String> saveDetail(@RequestBody ParamVo<TFormMain> paramVo) {
        TFormMain formDetail = paramVo.getData();
        if(formDetail == null)
            return fail("parameter is wrong.");
        formManageService.saveFormDetail(formDetail);
        return success("ok");
    }

    @ApiOperation(value = "列出可用表单")
    @PostMapping(value = "/findFormList")
    public ApiResult<List<TFormMain>> findFormList() {
        List<TFormMain> list = formManageService.findFormList();
        return success(list);
    }

    @ApiOperation(value = "设置报告")
    @PostMapping(value = "/setReport")
    public ApiResult<String> setReport(@RequestBody ParamVo<TFormMain> paramVo) {
        TFormMain formMain = paramVo.getData();
        if (formMain == null) {
            return fail("parameter is wrong.");
        }
        formManageService.saveFormReportSettings(formMain);
        return success("ok");
    }

    @ApiOperation(value = "获取报告配置信息")
    @PostMapping(value = "/getReport")
    public ApiResult<TFormMain> getReport(@RequestBody ParamVo<TFormMain> paramVo) {
        TFormMain formMain = formManageService.fetchFormReportSettings(paramVo.getData().getId());
        return success(formMain);
    }

    @ApiOperation(value = "获得图片上传token")
    @PostMapping(value = "/uploadToken")
    public ApiResult<String> uploadToken() {
        StringMap policy = new StringMap();
        String returnBody = "{\"url\":\"" + qiNiuConfig.getBaseUrl() + "/$(key)\",\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}";
        policy.put("returnBody", returnBody);
        String token = qiniuAuth.uploadToken(qiNiuConfig.getBucket(), null, qiNiuConfig.getExpires(), policy) ;
        return success(token);
    }

}
