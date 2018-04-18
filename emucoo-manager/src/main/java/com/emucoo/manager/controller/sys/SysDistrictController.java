package com.emucoo.manager.controller.sys;

import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.model.SysDistrict;
import com.emucoo.service.sys.SysDistrictService;
import io.swagger.annotations.Api;
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
	public SysDistrict get(@RequestBody SysDistrict sysDistrict) {
		return sysDistrictService.findOne(sysDistrict);
	}

	/**
	 * 区域列表
	 * @param sysDistrict
	 */
	@PostMapping (value = "list")
	public List<SysDistrict> list(@RequestBody SysDistrict sysDistrict) {
		return sysDistrictService.findListByWhere(sysDistrict);
	}
}
