package com.emucoo.service.shop;

import com.emucoo.dto.modules.shop.PatrolShopInfoIn;
import com.emucoo.dto.modules.shop.PlanArrangeAddIn;
import com.emucoo.dto.modules.shop.PlanArrangeDeleteIn;
import com.emucoo.dto.modules.shop.PlanArrangeEditIn;
import com.emucoo.model.TRemind;

import java.util.List;

public interface PlanArrangeService {
     List<TRemind> listRemind();
     void save(PlanArrangeAddIn vo);
     void edit(PlanArrangeEditIn vo);
     void delete(PlanArrangeDeleteIn vo);
     int patrolShop(PatrolShopInfoIn vo);
}
