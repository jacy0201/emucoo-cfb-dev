package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysAreaMapper;
import com.emucoo.model.SysArea;
import com.emucoo.service.sys.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysAreaServiceImpl extends BaseServiceImpl<SysArea> implements SysAreaService {

	@Autowired
	private SysAreaMapper sysAreaMapper;

}
