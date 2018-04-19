package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysDept;

import java.util.HashMap;
import java.util.List;

public interface SysDeptMapper extends MyMapper<SysDept> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

    /**
     * 根据品牌id 获取机构信息
     * @param map
     * @return
     */
    List<SysDept> listByBrand(HashMap map);

    /**
     * 根据分区id 获取机构信息
     * @param map
     * @return
     */
    List<SysDept> listByArea(HashMap map);
}