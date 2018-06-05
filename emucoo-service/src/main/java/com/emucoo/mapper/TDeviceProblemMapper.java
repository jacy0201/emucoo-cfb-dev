package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TDeviceProblem;

import java.util.List;

public interface TDeviceProblemMapper extends MyMapper<TDeviceProblem> {

    List<TDeviceProblem> findDeviceTypeProblems(long deviceTypeId);
}
