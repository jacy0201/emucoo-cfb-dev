package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TShopInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TShopInfoMapper extends MyMapper<TShopInfo> {
    List<TShopInfo> fetchShopsOfUser(Long id);

    List<TShopInfo> selectShopListByUserAndArea(@Param("userId") Long id, @Param("areaId")Long precinctID);
}