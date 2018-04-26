package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.dto.modules.report.AdditionItemVo;
import com.emucoo.model.TFormAddItemValue;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TFormAddItemValueMapper extends MyMapper<TFormAddItemValue> {

    void addAdditionItemList(@Param("addList") List<TFormAddItemValue> additionArray, @Param("createTime") Date date,
                             @Param("formResultId")Long formResultId);
}