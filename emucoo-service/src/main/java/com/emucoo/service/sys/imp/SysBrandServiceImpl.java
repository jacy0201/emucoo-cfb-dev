package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.TBrandInfoMapper;
import com.emucoo.model.TBrandInfo;
import com.emucoo.service.sys.SysBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysBrandServiceImpl extends BaseServiceImpl<TBrandInfo> implements SysBrandService {

	@Autowired
	private TBrandInfoMapper brandInfoMapper;

}
