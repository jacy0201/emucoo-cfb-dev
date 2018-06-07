package com.emucoo.service.shop;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.shop.FormResultVO;
import com.emucoo.dto.modules.shop.ResultQuery;
import com.emucoo.dto.modules.shop.ShopVO;
import com.emucoo.model.SysArea;
import com.emucoo.model.TShopInfo;

import java.util.List;

public interface ShopManageService extends BaseService<TShopInfo> {

    /**
     * 获取分区列表
     * @param userId
     * @return
     */
    List<SysArea> getAreaList(Long userId);

    /**
     * 根据分区获取店面资源
     * @param areaId
     * @param userId
     * @return
     */
    List<ShopVO> getShopList(Long areaId, Long userId);


    /**
     * 获取稽核结果
     * @param resultQuery
     * @return
     */
    List<FormResultVO> getResultList(ResultQuery resultQuery);
}
