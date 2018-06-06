package com.emucoo.service.shop.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.SysAreaMapper;
import com.emucoo.mapper.TShopInfoMapper;
import com.emucoo.model.SysArea;
import com.emucoo.model.TShopInfo;
import com.emucoo.service.shop.ShopManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class ShopManageServiceImpl extends BaseServiceImpl<TShopInfo> implements ShopManageService {

    @Resource
    private SysAreaMapper sysAreaMapper;

    @Resource
    private TShopInfoMapper shopInfoMapper;

    @Override
    public List<SysArea> getAreaList(Long userId){
        return sysAreaMapper.findAreaListByUserId(userId);
    }

    @Override
    public List<TShopInfo> getShopList(Long areaId, Long userId){
        return shopInfoMapper.getShopListByUserIDAndAreaID(userId,areaId);
    }

}
