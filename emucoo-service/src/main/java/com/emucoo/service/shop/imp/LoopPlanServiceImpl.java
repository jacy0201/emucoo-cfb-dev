package com.emucoo.service.shop.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.TFrontPlanMapper;
import com.emucoo.model.TFrontPlan;
import com.emucoo.model.TLoopPlan;
import com.emucoo.service.shop.LoopPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class LoopPlanServiceImpl extends BaseServiceImpl<TLoopPlan> implements LoopPlanService {


	@Autowired
	private TFrontPlanMapper frontPlanMapper;


	@Override
	public int countFrontPlan(Long userId, Date needDate) {
		return frontPlanMapper.countTodayPlanOfUser(userId, needDate);
	}

	@Override
	public List<TFrontPlan> listFrontPlan(Long submitUserId, Date needDate) {
		 return frontPlanMapper.listTodayPlanOfUser(submitUserId, needDate);
	}
}
