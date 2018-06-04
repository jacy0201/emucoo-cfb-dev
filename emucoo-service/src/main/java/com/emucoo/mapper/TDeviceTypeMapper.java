package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TDeviceType;

import java.util.List;

public interface TDeviceTypeMapper extends MyMapper<TDeviceType> {

    List<TDeviceType> filterDeviceTypesByKeyword(String keyword, long typeId, int i, int pageSz);

    int countDeviceTypesByKeyword(String keyword, long typeId);
}
