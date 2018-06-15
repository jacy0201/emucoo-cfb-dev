package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TRepairWork;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TRepairWorkMapper extends MyMapper<TRepairWork> {

    List<TRepairWork> fetchWorksListByDate(@Param("shopId") long shopId, @Param("beginDt") Date beginDt, @Param("endDt") Date endDt);

    List<TRepairWork> getRepairWorkList(@Param("month") String month, @Param("userId") Long userId);

    TRepairWork fetchOneById(long workId);

    List<TRepairWork> filterExeExpiredWorks(Date dt);

    void markExeExpiredWorks(Date dt);

    List<TRepairWork> filterAuditExpiredWorks(Date dt);

    void markAuditExpiredWorks(Date dt);
}
