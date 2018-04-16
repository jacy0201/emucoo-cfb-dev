package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TShopInfo;

import java.util.List;

public interface TShopInfoMapper extends MyMapper<TShopInfo> {
    List<TShopInfo> fetchShopsOfUser(Long id);
}