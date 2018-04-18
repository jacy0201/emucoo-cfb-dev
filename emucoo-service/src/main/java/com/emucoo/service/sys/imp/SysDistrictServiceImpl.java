package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysDistrictMapper;
import com.emucoo.model.SysDistrict;
import com.emucoo.service.sys.SysDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysDistrictServiceImpl extends BaseServiceImpl<SysDistrict> implements SysDistrictService {

	@Autowired
	private SysDistrictMapper sysDistrictMapper;

}
