package com.emucoo.mapper;

import com.emucoo.common.base.mapper.MyMapper;
import com.emucoo.model.SysPost;
import com.emucoo.model.SysUser;
import com.emucoo.model.TTaskPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TTaskPersonMapper extends MyMapper<TTaskPerson> {

    List<TTaskPerson> fetchByTaskId(Long id);

    void dropByTaskId(Long taskId);

    List<SysUser> filterTaskExecutorsByDpt(@Param("taskId") Long taskId);

    List<SysUser> filterTaskExecutorsByShop(@Param("taskId") Long taskId);

    List<SysUser> filterSendersByExecutorShop(@Param("userId") Long userId, @Param("taskId") Long taskId);

    List<SysUser> filterSendersByExecutorDpt(Long id);

    List<SysUser> fetchSupervisorsOfShop(@Param("deptId")Long deptId, @Param("shopId") Long shopId);

    SysUser fetchImmediateSuperiorOfUser(@Param("userId") Long userId, @Param("deptId") Long deptId, @Param("positionId") Long currentPosId);

    List<SysPost> listPositionsOfDept(Long deptId);
}