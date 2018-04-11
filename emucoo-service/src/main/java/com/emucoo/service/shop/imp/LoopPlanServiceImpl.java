package com.emucoo.service.shop.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.shop.ShopPlanProgressOut;
import com.emucoo.mapper.CheckSheetMapper;
import com.emucoo.mapper.FrontPlanMapper;
import com.emucoo.mapper.LoopPlanSrcMapper;
import com.emucoo.model.CheckSheet;
import com.emucoo.model.FrontPlan;
import com.emucoo.model.LoopPlanSrc;
import com.emucoo.service.shop.LoopPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class LoopPlanServiceImpl extends BaseServiceImpl<LoopPlanSrc> implements LoopPlanService {

	@Autowired
	private CheckSheetMapper checkSheetMapper;

	@Autowired
	private LoopPlanSrcMapper loopPlanSrcMapper;

	@Autowired
	private FrontPlanMapper frontPlanMapper;

	@Override
	public List<CheckSheet> listCheckSheets() {
		return checkSheetMapper.listCheckSheets();
	}

	@Override
	public List<FrontPlan> listFrontPlan(Long userId, Date needDate) {
		return frontPlanMapper.selectTodayPlanOfUser(userId, needDate);
	}

	@Override
	public int countFrontPlan(Long userId, Date needDate) {
		return frontPlanMapper.countTodayPlanOfUser(userId, needDate);
	}

	@Override
	public List<LoopPlanSrc> getLoopPlanAddList(){

		Example example = new Example(LoopPlanSrc.class);
		//isPlan :1-已安排； 0-未安排
		return loopPlanSrcMapper.selectByExample(example.createCriteria().andEqualTo("isPlan","0"));

	}
}
