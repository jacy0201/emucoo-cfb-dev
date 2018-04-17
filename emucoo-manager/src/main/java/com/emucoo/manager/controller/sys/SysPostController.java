package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.SysPost;
import com.emucoo.service.sys.SysPostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class SysPostController extends BaseResource {
	@Autowired
	private SysPostService sysPostService;
	
	/**
	 * 岗位列表查询
	 */
	@PostMapping ("/list")
	@RequiresPermissions("sys:post:list")
	@ResponseBody
	public ApiResult list(@RequestBody ParamVo<SysPost> param){
		SysPost sysPost = param.getData();
		Example example=new Example(SysPost.class);
		if(null!=sysPost.getPostName()) {
			example.createCriteria().andLike("postName", "%"+sysPost.getPostName()+"%");
		}
		PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "create_time desc");
		List<SysPost> postList = sysPostService.selectByExample(example);
		PageInfo<SysPost> pageInfo=new PageInfo(postList);
		return success(pageInfo);

	}


	/**
	 * 保存岗位
	 */
	@PostMapping ("/save")
	@RequiresPermissions("sys:post:save")
	@ResponseBody
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
	@ResponseBody
	public ApiResult update(@RequestBody SysPost post){
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
	@ResponseBody
	public ApiResult delete(Long id){
		sysPostService.deleteById(id);

		return success("success");
	}
}
