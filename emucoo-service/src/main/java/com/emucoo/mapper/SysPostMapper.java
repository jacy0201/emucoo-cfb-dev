package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysPost;

import java.util.List;

public interface SysPostMapper extends MyMapper<SysPost> {

    List<SysPost> findPositionByUserId(Long userId);
}