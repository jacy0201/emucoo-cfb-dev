package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TReportOppt;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TReportOpptMapper extends MyMapper<TReportOppt> {
    void addReportOpptRelation(@Param("reportOppts")List<TReportOppt> reportOppts, @Param("reportId")Long reportId,
                               @Param("createTime")Date createTime);
}