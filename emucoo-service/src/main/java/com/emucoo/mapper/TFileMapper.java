package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.TFile;

import java.util.List;

public interface TFileMapper extends MyMapper<TFile> {

    List<TFile> fetchFilesByIds(List<String> idList);

    void dropByIds(List<String> strings);

    void cleanFileById(Long resultId);
}