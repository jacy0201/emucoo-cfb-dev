package com.emucoo.service.shop.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.shop.FormResultVO;
import com.emucoo.dto.modules.shop.ResultQuery;
import com.emucoo.dto.modules.shop.ShopVO;
import com.emucoo.mapper.SysAreaMapper;
import com.emucoo.mapper.SysUserMapper;
import com.emucoo.mapper.TShopInfoMapper;
import com.emucoo.model.SysArea;
import com.emucoo.model.SysUser;
import com.emucoo.model.TShopInfo;
import com.emucoo.service.shop.ShopManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ShopManageServiceImpl extends BaseServiceImpl<TShopInfo> implements ShopManageService {

    @Resource
    private SysAreaMapper sysAreaMapper;

    @Resource
    private TShopInfoMapper shopInfoMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysArea> getAreaList(Long userId){
        return sysAreaMapper.findAreaListByUserId(userId);
    }

    @Override
    public List<ShopVO> getShopList(Long areaId, Long userId){
        List<ShopVO> list=null;
        List<TShopInfo> shopInfoList= shopInfoMapper.getShopListByUserIDAndAreaID(userId,areaId);
        if(null!=shopInfoList && shopInfoList.size()>0) {
            list=new ArrayList<>();
            for(TShopInfo tShopInfo:shopInfoList){
                ShopVO shopVO = new ShopVO();
                shopVO.setShopId(tShopInfo.getId());
                shopVO.setAddress(tShopInfo.getAddress());
                if (null != tShopInfo.getShopManagerId()) {
                    SysUser sysUser = sysUserMapper.selectByPrimaryKey(tShopInfo.getShopManagerId());
                    shopVO.setShopManager(sysUser.getRealName());
                }
                shopVO.setShopManagerId(tShopInfo.getShopManagerId());
                shopVO.setShopName(tShopInfo.getShopName());
                shopVO.setTel(tShopInfo.getTel());
                list.add(shopVO);
            }
        }
        return list;
    }

    @Override
    public List<FormResultVO> getResultList(ResultQuery resultQuery){

        return  null;
    }

}
