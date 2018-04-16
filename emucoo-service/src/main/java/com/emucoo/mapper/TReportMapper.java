package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.index.ReportItemVo;
import com.emucoo.model.TReport;

import java.util.List;

public interface TReportMapper extends MyMapper<TReport> {
    List<ReportItemVo> fetchUnReadReport(long currUserId);

    Integer countUnReadReport(Long userId);
}