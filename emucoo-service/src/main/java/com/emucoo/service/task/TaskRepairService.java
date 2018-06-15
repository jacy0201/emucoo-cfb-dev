package com.emucoo.service.task;

import com.emucoo.model.TDeviceProblem;
import com.emucoo.model.TDeviceType;
import com.emucoo.model.TRepairWork;

import java.util.Date;
import java.util.List;


/**
 * @author Shayne.Wang
 * @date 2018-06-12
 */

public interface TaskRepairService {

    /**
     * 任务详情
     * @param workId
     * @return
     */
    TRepairWork detail(long workId);

    /**
     * 店铺维修任务列表
     *
     * @param shopId
     * @param date
     * @param userId
     * @return
     */
    List<TRepairWork> listRepairWorksByShopId(long shopId, String date, long userId);

    /**
     * 创建维修任务
     * @param work
     */
    void createRepairWork(TRepairWork work);

    /**
     * 修改维修任务
     * @param work
     */
    void modifyRepairWork(TRepairWork work);

    /**
     * 完成维修任务
     * @param work
     */
    void finishRepairWork(TRepairWork work);

    /**
     * 设定维修任务维修时间
     * @param work
     */
    void expectRepairDate(TRepairWork work);

    /**
     * 审核维修任务
     * @param work
     */
    void auditRepairWork(TRepairWork work);

    /**
     * 设备列表
     * @param deviceType
     * @param pageNm
     * @param pageSz
     * @return
     */
    List<TDeviceType> listDeviceTypes(TDeviceType deviceType, int pageNm, int pageSz);

    /**
     * 设备列表个数
     * @param deviceType
     * @return
     */
    int countDeviceTypes(TDeviceType deviceType);

    /**
     * 保存设备任务
     * @param dvc
     */
    void saveDeviceType(TDeviceType dvc);

    /**
     * 配置设备分类
     * @param dvc
     */
    void configDeviceCategory(TDeviceType dvc);

    /**
     * 设置设备问题
     * @param dvc
     */
    void attachDeviceProblem(TDeviceType dvc);

    /**
     * 删除设备
     * @param deviceTypeId
     */
    void removeDeviceType(long deviceTypeId);

    /**
     * 启用/停用
     * @param deviceTypeId
     * @param b
     */
    void switchDeviceUsable(long deviceTypeId, boolean b);

    /**
     * 查找设备详情
     * @param deviceTypeId
     * @return
     */
    TDeviceType fetchDeviceTypeInfo(long deviceTypeId);

    /**
     * 以及分类列表
     * @return
     */
    List<TDeviceType> listTopDeviceTypes();

    /**
     * 子分类列表
     * @param parentId
     * @return
     */
    List<TDeviceType> listChildrenDeviceType(long parentId);

    /**
     * 设备问题列表
     * @param deviceId
     * @return
     */
    List<TDeviceProblem> listDeviceProblems(long deviceId);

    /**
     * 处理超时未执行的维修任务
     * @param dt
     */
    void dealWithExpiredWorks(Date dt);
}
