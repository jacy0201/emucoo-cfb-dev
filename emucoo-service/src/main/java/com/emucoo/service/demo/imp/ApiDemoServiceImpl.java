package com.emucoo.service.demo.imp;


import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.ContentMapper;
import com.emucoo.model.Content;
import com.emucoo.service.demo.ApiDemoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Service
public class ApiDemoServiceImpl extends BaseServiceImpl<Content> implements ApiDemoService {

	@Resource
    private ContentMapper contentMapper;
	
	@Override
	public Content getById(long id) {
		// TODO Auto-generated method stub
		return contentMapper.selectByPrimaryKey(id);
	}

}
