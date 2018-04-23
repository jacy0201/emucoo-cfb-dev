package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.SysPost;
import com.emucoo.service.sys.SysPostService;
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
 * 岗位管理
 */
@RestController
@RequestMapping("/sys/post")
@Api(description="岗位管理" )
public class SysPostController extends BaseResource {
	@Autowired
	private SysPostService sysPostService;
	
	/**
	 * 岗位列表查询
	 */
	@PostMapping ("/list")
	@RequiresPermissions("sys:post:list")
	@ApiOperation(value="查询岗位列表")
	@ResponseBody
	public ApiResult<PageInfo<SysPost>> list(@RequestBody ParamVo<SysPost> param){
		SysPost sysPost = param.getData();
		Example example=new Example(SysPost.class);
		if(null!=sysPost && null!=sysPost.getPostName()) {
			example.createCriteria().andLike("postName", "%"+sysPost.getPostName()+"%");
		}
		PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "create_time desc");
		List<SysPost> postList = sysPostService.selectByExample(example);
		PageInfo<SysPost> pageInfo=new PageInfo(postList);
		return success(pageInfo);

	}


	/**
	 * 查询岗位列表(不分页)
	 */
	@PostMapping ("/listAll")
	@RequiresPermissions("sys:post:listAll")
	@ResponseBody
	@ApiOperation(value="查询全部岗位")
	public ApiResult<List<SysPost>> listAll(@RequestBody SysPost sysPost){
		Example example=new Example(SysPost.class);
		if(null!=sysPost.getPostName()) {
			example.createCriteria().andLike("postName", "%"+sysPost.getPostName()+"%");
		}
		List<SysPost> postList = sysPostService.selectByExample(example);
		return success(postList);

	}



	/**
	 * 保存岗位
	 */
	@PostMapping ("/save")
	@RequiresPermissions("sys:post:save")
	@ApiOperation(value="添加岗位")
	public ApiResult save(@RequestBody SysPost post){
		post.setCreateTime(new Date());
		post.setCreateUserId(1L);
		post.setIsDel(false);
		post.setStatus(0);
		sysPostService.saveSelective(post);
		return success("success");
	}

	/**
	 * 修改岗位
	 */
	@PostMapping ("/update")
	@RequiresPermissions("sys:post:update")
	@ApiOperation(value="修改岗位")
	public ApiResult update(@RequestBody SysPost post){
		if(post.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"id 参数不能为空!");}
		post.setModifyTime(new Date());
		post.setModifyUserId(1L);
		sysPostService.updateSelective(post);
		return success("success");
	}

	/**
	 * 删除岗位
	 */
	@PostMapping ("/delete")
	@RequiresPermissions("sys:post:delete")
	@ApiOperation(value="删除岗位")
	public ApiResult delete(@RequestBody SysPost post){
		if(post.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"id 参数不能为空!");}
		sysPostService.deleteById(post.getId());
		return success("success");
	}
}
