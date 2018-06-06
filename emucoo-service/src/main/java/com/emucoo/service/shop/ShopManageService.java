package com.emucoo.service.shop;

import com.emucoo.common.base.service.BaseService;
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
    List<TShopInfo> getShopList(Long areaId, Long userId);
}
