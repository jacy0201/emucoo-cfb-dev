package com.emucoo.service.center.impl;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.center.TaskVO;
import com.emucoo.mapper.TLoopWorkMapper;
import com.emucoo.model.TFrontPlan;
import com.emucoo.model.TLoopWork;
import com.emucoo.service.center.UserCenterService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 个人中心
 * @author Jacy
 *
 */
@Service
public class UserCenterServiceImpl extends BaseServiceImpl<TLoopWork> implements UserCenterService {

    @Resource
    private TLoopWorkMapper tLoopWorkMapper;

    //我执行的任务
    @Override
   public  List<TaskVO> executeTaskList(String month,Long userId){
        Example example =new Example(TLoopWork.class);

        return null;
   }

    //我审核的任务
    @Override
    public List<TaskVO> auditTaskList(String month,Long userId){
        return null;

    }

    //我创建的任务
    @Override
    public List<TaskVO> createTaskList(String month,Long userId){
        return null;

    }

    //我接收的报告
    @Override
    public List<TaskVO> getReportList(String month,Long userId){
        return null;

    }

    @Override
    //巡店任务
    public List<TFrontPlan> frontPlanList(String month,Long userId){
        return null;

    }

    //维修任务



}
