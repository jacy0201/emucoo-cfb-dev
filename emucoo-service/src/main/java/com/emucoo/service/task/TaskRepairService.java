package com.emucoo.service.task;

import com.emucoo.model.TDeviceProblem;
import com.emucoo.model.TDeviceType;
import com.emucoo.model.TRepairWork;

import java.util.List;



public interface TaskRepairService {

    TRepairWork detail(long workId);

    List<TRepairWork> listRepairWorksByShopId(long shopId, String date, long userId);

    void createRepairWork(TRepairWork work);

    void modifyRepairWork(TRepairWork work);

    void finishRepairWork(TRepairWork work);

    void expectRepairDate(TRepairWork work);

    void auditRepairWork(TRepairWork work);

    List<TDeviceType> listDeviceTypes(String keyword, long typeId, int pageNm, int pageSz);

    int countDeviceTypes(String keyword, long typeId);

    void saveDeviceType(TDeviceType dvc);

    void configDeviceCategory(TDeviceType dvc);

    void attachDeviceProblem(TDeviceType dvc);

    void removeDeviceType(long deviceTypeId);

    void switchDeviceUsable(long deviceTypeId, boolean b);

    TDeviceType fetchDeviceTypeInfo(long deviceTypeId);

    List<TDeviceType> listTopDeviceTypes();

    List<TDeviceType> listChildrenDeviceType(long parentId);

    List<TDeviceProblem> listDeviceProblems(long deviceId);
}
