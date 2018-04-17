package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysPostMapper;
import com.emucoo.model.SysPost;
import com.emucoo.service.sys.SysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPost> implements SysPostService {

	@Autowired
	private SysPostMapper sysPostMapper;

}
