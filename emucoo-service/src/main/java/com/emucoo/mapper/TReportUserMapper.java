package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysUser;
import com.emucoo.model.TReportUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TReportUserMapper extends MyMapper<TReportUser> {

    void addReportToUser(@Param("users")List<SysUser> users, @Param("reportId")Long reportId);
}