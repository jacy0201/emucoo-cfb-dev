package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.model.SysDistrict;
import com.emucoo.service.sys.SysDistrictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 行政区域管理
 */
@RestController
@RequestMapping("/sys/district")
@Api(description="行政区域管理" )
public class SysDistrictController extends BaseResource {
	@Autowired
	private SysDistrictService sysDistrictService;

	/**
	 * 获取区域信息
	 */
	@ResponseBody
	@PostMapping (value = "get")
	@ApiOperation(value="获取单个地区详情")
	public SysDistrict get(@RequestBody SysDistrict sysDistrict) {
		return sysDistrictService.findOne(sysDistrict);
	}

	/**
	 * 查询省列表
	 */
	@PostMapping (value = "listProvice")
	@ApiOperation(value="查询省份集合")
	public ApiResult listProvice() {
		SysDistrict sysDistrict=new SysDistrict();
		sysDistrict.setAreaType("1");
		return success(sysDistrictService.findListByWhere(sysDistrict));
	}

	/**
	 * 根据省编码查市集合
	 */
	@PostMapping (value = "listCityByPrvCode")
	@ApiOperation(value="根据省份编码查询市", notes ="areaCode 参数不能为空，需要传递所要查询的省的编码！！")
	public ApiResult listCityByPrvCode(@RequestBody SysDistrict sysDistrict) {
		if(null==sysDistrict.getAreaCode()){return  fail(ApiExecStatus.INVALID_PARAM,"areaCode 不能为空！");}
		sysDistrict.setAreaType("2");
		sysDistrict.setParentCode(sysDistrict.getAreaCode());
		sysDistrict.setAreaCode(null);
		return success(sysDistrictService.findListByWhere(sysDistrict));
	}

	/**
	 * 根据省编码查 市集合
	 */
	@PostMapping (value = "listDisByCityCode")
	@ApiOperation(value="根据市编码查询区",notes ="areaCode 参数不能为空，需要传递所要查询的市的编码！！")
	public ApiResult listDisByCityCode(@RequestBody SysDistrict sysDistrict) {
		if(null==sysDistrict.getAreaCode()){return  fail(ApiExecStatus.INVALID_PARAM,"areaCode 不能为空！");}
		sysDistrict.setAreaType("3");
		sysDistrict.setParentCode(sysDistrict.getAreaCode());
		sysDistrict.setAreaCode(null);
		return success(sysDistrictService.findListByWhere(sysDistrict));
	}
}
