package com.emucoo.service.sys;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.Label;

import java.util.List;
import java.util.Map;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/30 17:49
 * @modified by:
 */
public interface LabelService extends BaseService<Label> {

  Map<Long, List<Label>> dimensions();

    List<Long> listIdByNames(String names);
}
