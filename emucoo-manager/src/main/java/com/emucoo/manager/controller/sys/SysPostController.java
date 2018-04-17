package com.emucoo.manager.controller.sys;

import com.emucoo.common.util.R;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.manager.controller.AbstractController;
import com.emucoo.model.SysPost;
import com.emucoo.service.sys.SysPostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * 岗位管理
 */
@RestController
@RequestMapping("/sys/post")
public class SysPostController extends AbstractController {
	@Autowired
	private SysPostService sysPostService;
	
	/**
	 * 岗位列表查询
	 */
	@GetMapping  ("/list")
	@RequiresPermissions("sys:post:list")
	public R list(@RequestBody ParamVo<SysPost> param){
		SysPost sysPost = param.getData();
		Example example=new Example(SysPost.class);
		example.createCriteria().andLike("postName",sysPost.getPostName());
		PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "create time desc");
		List<SysPost> postList = sysPostService.selectByExample(example);
		PageInfo<SysPost> pageInfo=new PageInfo(postList);
	    return  R.ok().put("data",pageInfo);
	}


	/**
	 * 保存岗位
	 */
	@PostMapping ("/save")
	@RequiresPermissions("sys:post:save")
	public R save(@RequestBody SysPost post){
		sysPostService.save(post);

		return R.ok();
	}

	/**
	 * 修改岗位
	 */
	@PutMapping ("/update")
	@RequiresPermissions("sys:post:update")
	public R update(@RequestBody SysPost post){
		sysPostService.update(post);
		return R.ok();
	}

	/**
	 * 删除岗位
	 */
	@DeleteMapping ("/delete")
	@RequiresPermissions("sys:post:delete")
	public R delete(Long id){
		sysPostService.deleteById(id);

		return R.ok();
	}
}
