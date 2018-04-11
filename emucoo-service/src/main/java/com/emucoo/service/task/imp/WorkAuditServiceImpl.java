package com.emucoo.service.task.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.WorkAuditMapper;
import com.emucoo.model.WorkAudit;
import com.emucoo.service.task.WorkAuditService;

@Transactional
@Service
public class WorkAuditServiceImpl extends BaseServiceImpl<WorkAudit> implements WorkAuditService {

	@Autowired
	private WorkAuditMapper mapper;

	@Override
	public void add(WorkAudit workAudit) {
		mapper.add(workAudit);
	}

	@Override
	public void addList(List<WorkAudit> list) {
		mapper.addList(list);
	}

}
