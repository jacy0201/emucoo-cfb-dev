package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.SysAppVersion;
import com.emucoo.service.version.SysAppVersionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sys/version")
public class SysAppVersionController extends BaseResource {

	@Autowired
    private SysAppVersionService sysAppVersionService;

    /**
     * 查询版本列表
     * @param param
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value="查询APP版本列表")
    public ApiResult<PageInfo<SysAppVersion>> list(@RequestBody ParamVo<SysAppVersion> param) {
        SysAppVersion sysAppVersion=param.getData();
        Example example=new Example(SysAppVersion.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDel",false);
        if(null!=sysAppVersion.getAppType()){criteria.andEqualTo("appType",sysAppVersion.getAppType());}
        if(null!=sysAppVersion.getAppVersion()){criteria.andEqualTo("appVersion",sysAppVersion.getAppVersion());}
        PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "update_id desc");
        List<SysAppVersion> list=sysAppVersionService.selectByExample(example);
        PageInfo<SysAppVersion> pageInfo=new PageInfo(list);
        return success(pageInfo);
    }


    /**
     * 创建版本
     */
    @PostMapping ("/save")
    @ApiOperation(value="创建版本")
    public ApiResult save(@RequestBody SysAppVersion sysAppVersion){
        sysAppVersion.setCreateTime(new Date());
        sysAppVersion.setCreateUserId(1L);
        sysAppVersion.setIsDel(false);
        sysAppVersion.setIsUse(false);
        sysAppVersionService.saveSelective(sysAppVersion);
        return success("success");
    }

    /**
     * 编辑版本
     */
    @PostMapping ("/edit")
    @ApiOperation(value="编辑版本")
    public ApiResult edit(@RequestBody SysAppVersion sysAppVersion){
        if(null==sysAppVersion.getId()){return fail(ApiExecStatus.INVALID_PARAM,"id不能为空!");}
        sysAppVersionService.updateSelective(sysAppVersion);
        return success("success");
    }

    /**
     * 启用/停用 版本
     */
    @PostMapping ("/use")
    @ApiOperation(value="启用/停用版本")
    public ApiResult use(@RequestBody SysAppVersion sysAppVersion){
        if(null==sysAppVersion.getId()){return fail(ApiExecStatus.INVALID_PARAM,"id不能为空!");}
        if(null==sysAppVersion.getIsUse()){return fail(ApiExecStatus.INVALID_PARAM,"isUse不能为空!");}
        sysAppVersionService.updateSelective(sysAppVersion);
        return success("success");
    }

    /**
     * 删除版本
     */
    @PostMapping ("/delete")
    @ApiOperation(value="删除版本")
    public ApiResult delete(@RequestBody SysAppVersion sysAppVersion){
        if(null==sysAppVersion.getId()){return fail(ApiExecStatus.INVALID_PARAM,"id不能为空!");}
        if(null==sysAppVersion.getIsDel()){return fail(ApiExecStatus.INVALID_PARAM,"isDel不能为空!");}
        sysAppVersionService.updateSelective(sysAppVersion);
        return success("success");
    }


}
