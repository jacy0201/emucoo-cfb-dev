package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TDeviceType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDeviceTypeMapper extends MyMapper<TDeviceType> {

    List<TDeviceType> filterDeviceTypesByKeyword(@Param("keyword") String keyword, @Param("typeId") long typeId, @Param("pageStart") int pageStart, @Param("pageSz") int pageSz);

    int countDeviceTypesByKeyword(String keyword, long typeId);
}
