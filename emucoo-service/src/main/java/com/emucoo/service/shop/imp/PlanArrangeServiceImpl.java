package com.emucoo.service.shop.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.dto.modules.shop.CheckList;
import com.emucoo.dto.modules.shop.PatrolShopInfoIn;
import com.emucoo.dto.modules.shop.PlanArrangeAddIn;
import com.emucoo.dto.modules.shop.PlanArrangeDeleteIn;
import com.emucoo.dto.modules.shop.PlanArrangeEditIn;
import com.emucoo.dto.modules.shop.Shop;
import com.emucoo.mapper.shop.TFrontPlanMapper;
import com.emucoo.mapper.shop.TLoopPlanSrcMapper;
import com.emucoo.mapper.shop.TRemindMapper;
import com.emucoo.model.shop.TFrontPlan;
import com.emucoo.model.shop.TRemind;
import com.emucoo.service.shop.PlanArrangeService;
import com.emucoo.utils.ConstantsUtil;

@Transactional
@Service
public class PlanArrangeServiceImpl implements PlanArrangeService {

	@Autowired
	private TFrontPlanMapper mapper;
	
	@Autowired
	private TLoopPlanSrcMapper tLoopPlanSrcMapper;
	
	@Autowired
	private TRemindMapper tRemindMapper;

	@Override
	public void save(PlanArrangeAddIn planArrangeAddIn) {
		Integer year = Integer.parseInt(planArrangeAddIn.getExPatrloShopArrangeDate().substring(0,4));
		Integer month = Integer.parseInt(planArrangeAddIn.getExPatrloShopArrangeDate().substring(4,6));
		Long shopId = null;
		List<Shop> shopList = planArrangeAddIn.getShopArr();
		List<TFrontPlan> list = new ArrayList<TFrontPlan>();
		for (Shop shop : shopList) {
			TFrontPlan tFrontPlan = new TFrontPlan();
			shopId = shop.getShopID();
			tFrontPlan.setDptId(shop.getShopID());
			Date date = new Date(shop.getExPatrloShopArrangeTime());
			tFrontPlan.setPlanTime(date);
			tFrontPlan.setRemindType(shop.getRemindType());
			tFrontPlan.setStatus(ConstantsUtil.FrontPlan.STATUS_ONE);
			tFrontPlan.setType(ConstantsUtil.FrontPlan.TYPE_ZERO);
			List<CheckList> checkList = shop.getChecklistArr();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < checkList.size(); i++) {
				if (i == (checkList.size() - 1)) {
					sb.append(checkList.get(i).getChecklistID());
				} else {
					sb.append(checkList.get(i).getChecklistID()).append(ConstantsUtil.Separator.SEPARATOR_IMGURL);
				}
			}
			tFrontPlan.setCheckFormIds(sb.toString());
			tFrontPlan.setSubID(shop.getSubID());
			tFrontPlan.setYear(year);
			tFrontPlan.setMonth(month);
			list.add(tFrontPlan);
		}
		mapper.addList(list);
		// 更新计划安排状态
		tLoopPlanSrcMapper.updateStatus(year, month, shopId, ConstantsUtil.FrontPlan.ISPLAN_TWO);
	}

	@Override
	public void edit(PlanArrangeEditIn planArrangeEditIn) {
		List<Shop> shopList = planArrangeEditIn.getShopArr();
		List<TFrontPlan> list = new ArrayList<TFrontPlan>();
		for (Shop shop : shopList) {
			TFrontPlan tFrontPlan = new TFrontPlan();
			tFrontPlan.setId(planArrangeEditIn.getPatrolShopArrangeID());
			tFrontPlan.setDptId(shop.getShopID());
			Date date = new Date(shop.getExPatrloShopArrangeTime());
			tFrontPlan.setPlanTime(date);
			tFrontPlan.setRemindType(shop.getRemindType());
			List<CheckList> checkList = shop.getChecklistArr();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < checkList.size(); i++) {
				if (i == (checkList.size() - 1)) {
					sb.append(checkList.get(i).getChecklistID());
				} else {
					sb.append(checkList.get(i).getChecklistID()).append(ConstantsUtil.Separator.SEPARATOR_IMGURL);
				}
			}
			tFrontPlan.setCheckFormIds(sb.toString());
			list.add(tFrontPlan);
		}
		mapper.editList(list);
	}

	@Override
	public void delete(PlanArrangeDeleteIn planArrangeDeleteIn) {
		mapper.deleteById(planArrangeDeleteIn.getPatrolShopArrangeID());
	}

	@Override
	public int patrolShop(PatrolShopInfoIn patrolShopInfoIn) {
		TFrontPlan tFrontPlan = new TFrontPlan();
		tFrontPlan.setId(patrolShopInfoIn.getPatrolShopArrangeID());
		tFrontPlan.setLocation(patrolShopInfoIn.getPatrolShopLocation());
		tFrontPlan.setLon(Double.valueOf(patrolShopInfoIn.getLongitude()));
		tFrontPlan.setLat(Double.valueOf(patrolShopInfoIn.getLatitude()));
		tFrontPlan.setStartTime(new Date(patrolShopInfoIn.getPatrolShopStartTime()));
		return mapper.patrolShop(tFrontPlan);
	}

	@Override
	public List<TRemind> listRemind() {
		return tRemindMapper.list();
	}

}
