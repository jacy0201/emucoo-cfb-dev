package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.index.ReportItemVo;
import com.emucoo.model.TReport;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TReportMapper extends MyMapper<TReport> {
    List<ReportItemVo> fetchUnReadReport(long currUserId);

    Integer countUnReadReport(Long userId);

    void saveReport(TReport report);

    List<TReport> findReportByUser(Long userId);

    List<TReport> findReportByFormIdAndTime(@Param("formId")Long formId, @Param("beginDate")Date beginReportDate, @Param("endDate")Date endReportDate);
}