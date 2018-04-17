package com.emucoo.service.shop.imp;

import com.emucoo.dto.modules.shop.PatrolShopInfoIn;
import com.emucoo.dto.modules.shop.PlanArrangeAddIn;
import com.emucoo.dto.modules.shop.PlanArrangeDeleteIn;
import com.emucoo.dto.modules.shop.PlanArrangeEditIn;
import com.emucoo.model.TRemind;
import com.emucoo.service.shop.PlanArrangeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanArrangeServiceImpl implements PlanArrangeService {
    @Override
    public List<TRemind> listRemind() {
        return null;
    }

    @Override
    public void save(PlanArrangeAddIn vo) {

    }

    @Override
    public void edit(PlanArrangeEditIn vo) {

    }

    @Override
    public void delete(PlanArrangeDeleteIn vo) {

    }

    @Override
    public int patrolShop(PatrolShopInfoIn vo) {
        return 0;
    }
}
