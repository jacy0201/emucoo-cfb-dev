package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.TBrandInfo;
import com.emucoo.service.sys.SysBrandService;
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
 * 品牌管理
 */
@RestController
@RequestMapping("/sys/brand")
@Api(description="品牌管理" )
public class SysBrandController extends BaseResource {
	@Autowired
	private SysBrandService sysBrandService;
	
	/**
	 * 分页查询品牌列表
	 */
	@PostMapping ("/list")
	//@RequiresPermissions("sys:brand:list")
	@ResponseBody
	@ApiOperation(value="分页查询品牌")
	public ApiResult<PageInfo<TBrandInfo>> list(@RequestBody ParamVo<TBrandInfo> param){
		TBrandInfo brandInfo = param.getData();
		Example example=new Example(TBrandInfo.class);
		Example.Criteria criteria=example.createCriteria();
		if(null!=brandInfo && null!=brandInfo.getBrandName()) {
			criteria.andLike("brandName", "%"+brandInfo.getBrandName()+"%");
		}
		criteria.andEqualTo("isDel",0);
		PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "create_time desc");
		List<TBrandInfo> brandList = sysBrandService.selectByExample(example);
		PageInfo<TBrandInfo> pageInfo=new PageInfo(brandList);
		return success(pageInfo);

	}

	/**
	 * 查询品牌列表(不分页)
	 */
	@PostMapping ("/listAll")
	//@RequiresPermissions("sys:brand:listAll")
	@ResponseBody
	@ApiOperation(value="查询全部品牌")
	public ApiResult<List<TBrandInfo>> listAll(@RequestBody TBrandInfo param){
		Example example=new Example(TBrandInfo.class);
		Example.Criteria criteria=example.createCriteria();
		if(null!=param.getBrandName()) {
			criteria.andLike("brandName", "%"+param.getBrandName()+"%");
		}
		criteria.andEqualTo("isDel",0);
		List<TBrandInfo> brandList = sysBrandService.selectByExample(example);
		return success(brandList);

	}


	/**
	 * 保存品牌
	 */
	@PostMapping ("/save")
	//@RequiresPermissions("sys:brand:save")
	@ApiOperation(value="添加品牌")
	public ApiResult save(@RequestBody TBrandInfo brand){
		brand.setCreateTime(new Date());
		brand.setCreateUserId(1L);
		brand.setIsDel(false);
		sysBrandService.saveSelective(brand);
		return success("success");
	}

	/**
	 * 修改品牌
	 */
	@PostMapping ("/update")
	//@RequiresPermissions("sys:brand:update")
	@ApiOperation(value="修改品牌")
	public ApiResult update(@RequestBody TBrandInfo brand){
		brand.setModifyTime(new Date());
		brand.setModifyUserId(1L);
		sysBrandService.updateSelective(brand);
		return success("success");
	}

	/**
	 * 删除品牌
	 */
	@PostMapping ("/delete")
	//@RequiresPermissions("sys:brand:delete")
	@ApiOperation(value="删除品牌")
	public ApiResult delete(@RequestBody TBrandInfo brand){
		if(brand.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"id 参数不能为空!");}
		sysBrandService.deleteById(brand.getId());
		return success("success");
	}
}
