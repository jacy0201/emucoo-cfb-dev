package com.emucoo.service.shop;

import com.emucoo.dto.modules.report.SaveReportIn;
import java.util.List;
import com.emucoo.dto.modules.shop.PatrolShopInfoIn;
import com.emucoo.dto.modules.shop.PlanArrangeAddIn;
import com.emucoo.dto.modules.shop.PlanArrangeDeleteIn;
import com.emucoo.dto.modules.shop.PlanArrangeEditIn;
import com.emucoo.model.shop.TRemind;

/**
 * 巡店安排
 * 
 * @author zhangxq
 * @date 2018-03-26
 */
public interface PlanArrangeService {

	void save(PlanArrangeAddIn planArrangeAddIn);

	void edit(PlanArrangeEditIn planArrangeEditIn);

	void delete(PlanArrangeDeleteIn planArrangeDeleteIn);
	
	int patrolShop(PatrolShopInfoIn patrolShopInfoIn);

	List<TRemind> listRemind(); 
}
