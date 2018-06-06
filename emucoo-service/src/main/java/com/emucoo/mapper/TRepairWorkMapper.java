package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.center.RepairWorkVO;
import com.emucoo.model.TRepairWork;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TRepairWorkMapper extends MyMapper<TRepairWork> {

    List<TRepairWork> fetchWorksByShopId();

    List<RepairWorkVO> getRepairWorkList(@Param("month") String month, @Param("userId") Long userId);

}
