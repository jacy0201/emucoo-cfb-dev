package com.emucoo.service.shop;

import com.emucoo.dto.modules.shop.PatrolShopArrangeDetailIn;
import com.emucoo.dto.modules.shop.PatrolShopArrangeDetailVO;
import com.emucoo.dto.modules.shop.PatrolShopInfoIn;
import com.emucoo.dto.modules.shop.PlanArrangeAddIn;
import com.emucoo.dto.modules.shop.PlanArrangeDeleteIn;
import com.emucoo.dto.modules.shop.PlanArrangeEditIn;
import com.emucoo.model.SysUser;
import com.emucoo.model.TRemind;

import java.util.List;

public interface PlanArrangeService {
     List<TRemind> listRemind();

     void save(PlanArrangeAddIn vo, SysUser user);

     void edit(PlanArrangeEditIn vo, SysUser user);

     void delete(PlanArrangeDeleteIn vo, SysUser user);

     void patrolShop(PatrolShopInfoIn vo, SysUser user);

     PatrolShopArrangeDetailVO detail(PatrolShopArrangeDetailIn vo);
}
