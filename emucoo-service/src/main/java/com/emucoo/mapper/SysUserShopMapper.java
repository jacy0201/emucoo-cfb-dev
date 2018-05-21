package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysUserShop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserShopMapper extends MyMapper<SysUserShop> {

    List<SysUserShop> findResponsiblePersonOfShop(@Param("shopId")Long shopID, @Param("dptId")Long dptId);
}