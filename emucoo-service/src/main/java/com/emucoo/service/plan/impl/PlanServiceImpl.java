package com.emucoo.service.plan.impl;

import com.emucoo.dto.modules.plan.FindShopListIn;
import com.emucoo.dto.modules.plan.FindShopListOut;
import com.emucoo.dto.modules.plan.ShopToPlanIn;
import com.emucoo.dto.modules.plan.ShopVo;
import com.emucoo.enums.Constant;
import com.emucoo.enums.DeleteStatus;
import com.emucoo.enums.ShopArrangeStatus;
import com.emucoo.mapper.TFrontPlanMapper;
import com.emucoo.mapper.TShopInfoMapper;
import com.emucoo.model.SysUser;
import com.emucoo.model.TFrontPlan;
import com.emucoo.model.TPlanFormRelation;
import com.emucoo.model.TShopInfo;
import com.emucoo.service.plan.PlanService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sj on 2018/4/17.
 */
@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private TShopInfoMapper tShopInfoMapper;

    @Autowired
    private TFrontPlanMapper tFrontPlanMapper;

    public FindShopListOut findShopList(SysUser user, FindShopListIn findShopListIn) {
        List<TShopInfo> shopList = tShopInfoMapper.selectShopListByUserAndArea(user.getId(), findShopListIn.getPrecinctID());
        FindShopListOut findShopListOut = new FindShopListOut();
        List<ShopVo> shopVos = new ArrayList<ShopVo>();
        if(CollectionUtils.isNotEmpty(shopList)) {
            for(TShopInfo tShopInfo : shopList) {
                ShopVo shopVo = new ShopVo();
                shopVo.setShopID(tShopInfo.getId());
                shopVo.setShopName(tShopInfo.getShopName());
                shopVos.add(shopVo);
            }
            findShopListOut.setShopArr(shopVos);
        }

        return findShopListOut;
    }

    @Transactional
    public void addShopToPlan(SysUser user, ShopToPlanIn shopToPlanIn) {
        Long planId = shopToPlanIn.getPlanID();
        Date now = new Date();
        for(ShopVo shopVo : shopToPlanIn.getShopArr()) {
            TFrontPlan tFrontPlan = new TFrontPlan();
            TShopInfo tShopInfo = new TShopInfo();
            tShopInfo.setId(shopVo.getShopID());
            tFrontPlan.setShop(tShopInfo);
            tFrontPlan.setLoopPlanId(planId);
            tFrontPlan.setStatus(ShopArrangeStatus.NOT_ARRANGE.getCode().byteValue());
            tFrontPlan.setCreateTime(now);
            tFrontPlan.setModifyTime(now);
            tFrontPlan.setCreateUserId(user.getId());
            tFrontPlan.setModifyUserId(user.getId());
            tFrontPlan.setOrgId(Constant.orgId);
            tFrontPlan.setIsDel(DeleteStatus.COMMON.getCode());
            tFrontPlanMapper.addUnArrangeToPlan(tFrontPlan);
        }
    }

    public void deleteShopInPlan(ShopToPlanIn shopToPlanIn) {
        Example example = new Example(TFrontPlan.class);
        List<Long> shopIds = new ArrayList<>();
        for(ShopVo shopVo : shopToPlanIn.getShopArr()) {
            shopIds.add(shopVo.getShopID());
        }
        example.createCriteria().andIn("shopId", shopIds);
        tFrontPlanMapper.deleteByExample(example);
    }
}
