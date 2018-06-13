package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TDeviceType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDeviceTypeMapper extends MyMapper<TDeviceType> {

    List<TDeviceType> filterDeviceTypesByKeyword(@Param("deviceType") TDeviceType deviceType, @Param("pageStart") int pageStart, @Param("pageSz") int pageSz);

    int countDeviceTypesByKeyword(TDeviceType deviceType);

    void switchDeviceTypeUsage(@Param("dvcId") long deviceTypeId, @Param("usage") boolean b);

    void removeDeviceType(long deviceTypeId);

    List<TDeviceType> findChildren(long deviceTypeId);

    List<TDeviceType> listTopDeviceTypes();
}
