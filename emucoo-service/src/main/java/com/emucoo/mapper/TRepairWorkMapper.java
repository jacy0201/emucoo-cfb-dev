package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.center.RepairWorkVO;
import com.emucoo.model.TRepairWork;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TRepairWorkMapper extends MyMapper<TRepairWork> {

    List<TRepairWork> fetchWorksListByDate(@Param("shopId") long shopId, @Param("userId") long userId, @Param("beginDt") Date beginDt, @Param("endDt") Date endDt);

    List<RepairWorkVO> getRepairWorkList(@Param("month") String month, @Param("userId") Long userId);

}
