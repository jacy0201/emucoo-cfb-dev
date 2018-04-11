package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.dimension.DimensionVo_O;
import com.emucoo.model.Dimension;

import java.util.List;
import java.util.Map;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/31 15:38
 * @modified by:
 */
public interface DimensionService extends BaseService<Dimension> {
  List<Dimension> queryDimensions();

    List<DimensionVo_O> listDimensions(Long dimensionId, Long typeId, String name);

    List<Dimension> queryTypes(Long dimensionId);

  Map<Long, List<Dimension>> dimensions();
}
