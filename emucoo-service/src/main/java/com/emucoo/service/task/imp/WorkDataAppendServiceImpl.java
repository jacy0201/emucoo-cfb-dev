package com.emucoo.service.task.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.WorkDataAppendMapper;
import com.emucoo.model.WorkDataAppend;
import com.emucoo.service.task.WorkDataAppendService;

@Transactional
@Service
public class WorkDataAppendServiceImpl extends BaseServiceImpl<WorkDataAppend> implements WorkDataAppendService {

	@Autowired
	private WorkDataAppendMapper mapper;

	@Override
	public void add(WorkDataAppend workDataAppend) {
		mapper.add(workDataAppend);
	}

	@Override
	public void addList(List<WorkDataAppend> list) {
		mapper.addList(list);
	}

	@Override
	public WorkDataAppend fetchWorkDataAppend(String workid, String subWorkid) {
		return mapper.fetchWorkDataAppend(workid, subWorkid);
	}

}
