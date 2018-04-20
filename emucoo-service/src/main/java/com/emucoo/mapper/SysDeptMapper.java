package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysDept;

import java.util.HashMap;
import java.util.List;

public interface SysDeptMapper extends MyMapper<SysDept> {

    /**
     * ²éÑ¯×Ó²¿ÃÅIDÁÐ±í
     * @param parentId  ÉÏ¼¶²¿ÃÅID
     */
    List<Long> queryDetpIdList(Long parentId);

    /**
     * 根据品牌id 获取机构信息
     * @param map
     * @return
     */
    List<SysDept> listByBrand(HashMap map);
	
    List<SysDept> findPlanProcessByUserId(Long userId);
    /**
     * 根据分区id 获取机构信息
     * @param map
     * @return
     */
    List<SysDept> listByArea(HashMap map);
}