package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TBrandInfo;
import com.emucoo.model.TShopInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TShopInfoMapper extends MyMapper<TShopInfo> {
    List<TShopInfo> fetchShopsOfUser(Long id);

    List<TShopInfo> selectShopListByUserAndAreaBrand(@Param("userId") Long id, @Param("areaId") Long precinctID,
                                                @Param("brandList")List<TBrandInfo> brandInfos);

    List<TShopInfo> selectShopListByUserId(@Param("userId")Long userId, @Param("brandList")List<TBrandInfo> brandInfos);

   void updateByManagerId(@Param("userId")Long userId);
}