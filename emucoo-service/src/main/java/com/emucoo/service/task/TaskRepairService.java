package com.emucoo.service.task;

import com.emucoo.model.TRepairWork;

import java.util.List;

public interface TaskRepairService {

    TRepairWork detail(long workId);

    List<TRepairWork> listRepairWorksByShopId(Long shopId);

    void createRepairWork(TRepairWork work);

    void modifyRepairWork(TRepairWork work);

    void finishRepairWork(TRepairWork work);

    void expectRepairDate(TRepairWork work);

    void auditRepairWork(TRepairWork work);
}
