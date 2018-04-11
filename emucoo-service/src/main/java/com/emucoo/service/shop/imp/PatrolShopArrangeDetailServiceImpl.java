/**
 * 
 */
package com.emucoo.service.shop.imp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.shop.ChecklistArrVO;
import com.emucoo.dto.modules.shop.PatrolShopArrangeDetailVO;
import com.emucoo.dto.modules.shop.ShopArrVO;
import com.emucoo.mapper.CheckSheetMapper;
import com.emucoo.mapper.DepartmentMapper;
import com.emucoo.mapper.shop.TFrontPlanMapper;
import com.emucoo.mapper.shop.TRemindMapper;
import com.emucoo.model.CheckSheet;
import com.emucoo.model.Department;
import com.emucoo.model.shop.TFrontPlan;
import com.emucoo.model.shop.TRemind;
import com.emucoo.service.shop.PatrolShopArrangeDetailService;
import com.emucoo.utils.DateUtil;

/**
 * @author Simon
 *
 */
@Service
public class PatrolShopArrangeDetailServiceImpl extends BaseServiceImpl<TFrontPlan>
		implements PatrolShopArrangeDetailService {

	@Resource
	private TFrontPlanMapper tFrontPlanMapper;

	@Resource
	private DepartmentMapper departmentMapper;

	@Resource
	private CheckSheetMapper checkSheetMapper;

	@Autowired
	private TRemindMapper tRemindMapper;

	@Override
	public PatrolShopArrangeDetailVO findPatrolShopArrangeDetail(int patrolShopArrangeID) {
		PatrolShopArrangeDetailVO patrolShopArrangeDetailVO = new PatrolShopArrangeDetailVO();
		TFrontPlan frontPlan = tFrontPlanMapper.selectByPrimaryKey(Long.valueOf(patrolShopArrangeID));
		List<ShopArrVO> shopArrList = new ArrayList<ShopArrVO>();
		// 如果在巡店安排表里没找到，返回空的VO
		if (frontPlan == null) {
			patrolShopArrangeDetailVO.setShopArr(shopArrList);
			return patrolShopArrangeDetailVO;
		}
		if (frontPlan.getPlanTime() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(frontPlan.getPlanTime());
			StringBuffer sb = new StringBuffer();
			sb.append(cal.get(cal.YEAR));
			String month = cal.get(cal.MONTH) < 9 ?"0" + (cal.get(cal.MONTH) + 1): "" + (cal.get(cal.MONTH) + 1);
			sb.append(month);
			String day = cal.get(cal.DAY_OF_MONTH) < 10 ?"0" + cal.get(cal.DAY_OF_MONTH) : "" + cal.get(cal.DAY_OF_MONTH);
			sb.append(day);	
			patrolShopArrangeDetailVO.setExPatrloShopArrangeDate(sb.toString());
		}
		Department departmentVO = departmentMapper.selectByPrimaryKey(frontPlan.getDptId());
		// 如果没找到相应店，也返回空的VO
		if (departmentVO == null) {
			patrolShopArrangeDetailVO.setShopArr(shopArrList);
			return patrolShopArrangeDetailVO;
		}
		ShopArrVO shopArr = new ShopArrVO();
		shopArr.setShopID(departmentVO.getId());
		shopArr.setShopName(departmentVO.getDptname());
		shopArr.setPostscript(departmentVO.getDescription());
		shopArr.setSubId(frontPlan.getLoopPlanSrcId());
		if (frontPlan.getStartTime() != null) {
			shopArr.setExPatrloShopArrangeTime(frontPlan.getStartTime().getTime());
		}
		// 2-已安排 3-已巡店
		if (frontPlan.getStatus()) {
			shopArr.setShopStatus("3");
		} else {
			shopArr.setShopStatus("2");
		}

		// 检查点
		// 如果检查点不为空
		if (frontPlan.getCheckFormIds() != null) {
			String[] checkFormIds = frontPlan.getCheckFormIds().split(",");
			List<ChecklistArrVO> checklistArr = new ArrayList<ChecklistArrVO>();
			for (int i = 0; i < checkFormIds.length; i++) {
				Long checkFormId = Long.parseLong(checkFormIds[i].trim());
				CheckSheet checkSheet = checkSheetMapper.selectByPrimaryKey(checkFormId);
				// 如果检查点合法，能查到
				if (checkSheet != null) {
					ChecklistArrVO checklistArrVO = new ChecklistArrVO();
					checklistArrVO.setChecklistID(checkFormId);
					checklistArrVO.setChecklistName(checkSheet.getName());
					long status = frontPlan.getReportId() == null ? 2 : 1;
					checklistArrVO.setChecklistStatus(status);
					if(frontPlan.getReportId() != null){
						checklistArrVO.setReportID(frontPlan.getReportId());
					}
					checklistArr.add(checklistArrVO);
				}
			}
			shopArr.setChecklistArr(checklistArr);
		}

		if (frontPlan.getStartTime() != null) {
			shopArr.setPatrolShopStartTime(frontPlan.getStartTime().getTime());
		}
		shopArr.setPatrolShopLocation(frontPlan.getLocation());
		shopArr.setLatitude(frontPlan.getLat());
		shopArr.setLongitude(frontPlan.getLon());
		shopArr.setReportId(frontPlan.getReportId() == null ? -1 : frontPlan.getReportId());

		// add 提醒 Remind_type and Remind_name
		TRemind tRemind = tRemindMapper.selectByType(frontPlan.getRemindType());
		if (tRemind != null) {
			shopArr.setRemindType(tRemind.getRemindType());
			shopArr.setRemindName(tRemind.getRemindName());
		}

		shopArrList.add(shopArr);
		patrolShopArrangeDetailVO.setShopArr(shopArrList);

		return patrolShopArrangeDetailVO;
	}
}
