package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TBrandInfo;

    
import java.util.HashMap;
import java.util.List;

public interface TBrandInfoMapper extends MyMapper<TBrandInfo> {

    /**
     * 根据机构查询品牌信息
     */
    List<TBrandInfo> listByDpt(HashMap paramMap);

	List<TBrandInfo> findBrandListByUserId(Long id);
}