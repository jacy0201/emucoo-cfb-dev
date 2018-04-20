package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TBrandInfo;

import java.util.HashMap;
import java.util.List;

public interface TBrandInfoMapper extends MyMapper<TBrandInfo> {

    /**
     * ���ݻ�����ѯƷ����Ϣ
     */
    List<TBrandInfo> listByDpt(HashMap paramMap);

}