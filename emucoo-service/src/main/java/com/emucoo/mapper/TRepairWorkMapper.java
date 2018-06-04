package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TDeviceType;
import com.emucoo.model.TRepairWork;

import java.util.List;

public interface TRepairWorkMapper extends MyMapper<TRepairWork> {

    List<TRepairWork> fetchWorksByShopId();

    List<TDeviceType> filterDeviceTypesByKeyword(String keyword, long typeId, int i, int pageSz);

    int countDeviceTypesByKeyword(String keyword, long typeId);
}
