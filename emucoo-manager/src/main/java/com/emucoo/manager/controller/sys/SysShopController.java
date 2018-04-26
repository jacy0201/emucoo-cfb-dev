package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.TShopInfo;
import com.emucoo.service.sys.SysShopService;
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
 * 店铺管理
 */
@RestController
@RequestMapping("/sys/shop")
@Api(description="店铺管理" )
public class SysShopController extends BaseResource {
	@Autowired
	private SysShopService sysShopService;
	
	/**
	 * 店铺列表查询
	 */
	@PostMapping ("/list")
	//@RequiresPermissions("sys:shop:list")
	@ApiOperation(value="查询店铺列表")
	@ResponseBody
	public ApiResult<PageInfo<TShopInfo>> list(@RequestBody ParamVo<TShopInfo> param){
		TShopInfo shopInfo = param.getData();
		Example example=new Example(TShopInfo.class);
		if(null!=shopInfo && null!=shopInfo.getShopName()) {
			example.createCriteria().andLike("shopName", "%"+shopInfo.getShopName()+"%");
		}

		if(null!=shopInfo && null!=shopInfo.getAreaId()){
			example.createCriteria().andEqualTo("areaId", shopInfo.getAreaId());
		}
		if(null!=shopInfo && null!=shopInfo.getBrandId()){
			example.createCriteria().andEqualTo("brandId", shopInfo.getBrandId());
		}

		PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "create_time desc");
		List<TShopInfo> shopList = sysShopService.selectByExample(example);
		PageInfo<TShopInfo> pageInfo=new PageInfo(shopList);
		return success(pageInfo);

	}


	/**
	 * 添加店铺
	 */
	@PostMapping ("/save")
	//@RequiresPermissions("sys:shop:save")
	@ApiOperation(value="添加店铺")
	public ApiResult save(@RequestBody TShopInfo shopInfo){
		shopInfo.setCreateTime(new Date());
		shopInfo.setCreateUserId(1L);
		shopInfo.setIsDel(false);
		sysShopService.saveSelective(shopInfo);
		return success("success");
	}

	/**
	 * 更新店铺
	 */
	@PostMapping ("/update")
	//@RequiresPermissions("sys:shop:update")
	@ApiOperation(value="更新店铺")
	public ApiResult update(@RequestBody TShopInfo shopInfo){
		if(shopInfo.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"id 参数不能为空!");}
		shopInfo.setModifyTime(new Date());
		shopInfo.setModifyUserId(1L);
		sysShopService.updateSelective(shopInfo);
		return success("success");
	}

	/**
	 * 删除店铺
	 */
	@PostMapping ("/delete")
	//@RequiresPermissions("sys:shop:delete")
	@ApiOperation(value="删除店铺")
	public ApiResult delete(@RequestBody TShopInfo shopInfo){
		if(shopInfo.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"id 参数不能为空!");}
		sysShopService.deleteById(shopInfo.getId());
		return success("success");
	}

	/**
	 * 启用停用店铺
	 */
	@PostMapping ("/modifyShopUse")
	//@RequiresPermissions("sys:shop:use")
	@ApiOperation(value="店铺启用/停用")
	public ApiResult modifyShopUse(@RequestBody TShopInfo shopInfo){
		if(shopInfo.getId()==null){return fail(ApiExecStatus.INVALID_PARAM,"id 参数不能为空!");}
		if(shopInfo.getIsUse()==null){return fail(ApiExecStatus.INVALID_PARAM,"启用/停用 参数不能为空!");}
		shopInfo.setModifyTime(new Date());
		shopInfo.setModifyUserId(1L);
		sysShopService.updateSelective(shopInfo);
		return success("success");
	}
}
