package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.SysArea;
import com.emucoo.service.sys.SysAreaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


/**
 * 分区管理
 */
@RestController
@RequestMapping("/sys/area")
@Api(description="分区管理" )
public class SysAreaController extends BaseResource {
	@Autowired
	private SysAreaService sysAreaService;
	
	/**
	 * 岗位列表查询
	 */
	@PostMapping ("/list")
	@RequiresPermissions("sys:area:list")
	@ResponseBody
	@ApiOperation(value="查询分区列表")
	public ApiResult list(@RequestBody ParamVo<SysArea> param){
		SysArea sysArea = param.getData();
		Example example=new Example(SysArea.class);
		if(null!=sysArea.getAreaName()) {
			example.createCriteria().andLike("areaName", "%"+sysArea.getAreaName()+"%");
		}
		PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "create_time desc");
		List<SysArea> areaList = sysAreaService.selectByExample(example);
		PageInfo<SysArea> pageInfo=new PageInfo(areaList);
		return success(pageInfo);

	}


	/**
	 * 添加分区
	 */
	@PostMapping ("/save")
	@RequiresPermissions("sys:area:save")
	@ApiOperation(value="添加分区")
	public ApiResult save(@RequestBody SysArea area){
		area.setCreateTime(new Date());
		area.setCreateUserId(1L);
		area.setIsDel(false);
		sysAreaService.saveSelective(area);
		return success("success");
	}

	/**
	 * 修改岗位
	 */
	@PostMapping ("/update")
	@RequiresPermissions("sys:area:update")
	@ApiOperation(value="修改分区")
	public ApiResult update(@RequestBody SysArea area){
		area.setModifyTime(new Date());
		area.setModifyUserId(1L);
		sysAreaService.updateSelective(area);
		return success("success");
	}

	/**
	 * 删除岗位
	 */
	@PostMapping ("/delete")
	@RequiresPermissions("sys:area:delete")
	@ApiOperation(value="删除分区")
	public ApiResult delete(Long id){
		sysAreaService.deleteById(id);

		return success("success");
	}
}
