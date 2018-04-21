package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFormSubPbm;
import com.emucoo.model.TFormSubPbmHeader;

import java.util.List;

public interface TFormSubPbmHeaderMapper extends MyMapper<TFormSubPbmHeader> {

    List<TFormSubPbmHeader> findFormSubPbmHeadersByFormPbmId(Long id);

    void upsertMulti(List<TFormSubPbmHeader> subProblemHeads);

    void dropByProblemIds(List<Long> probIds);
}
