package com.emucoo.service.calendar.impl;

import com.emucoo.dto.base.ISystem;
import com.emucoo.dto.modules.calendar.*;
import com.emucoo.dto.modules.task.WorkVo_O;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.calendar.CalendarService;
import com.emucoo.utils.ConstantsUtil;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.Collator;
import java.util.*;

/**
 * 行事历
 *
 * @author Jacy
 */
@Transactional
@Service
public class CalendarServiceImpl implements CalendarService {

    @Resource
    private TFrontPlanMapper tFrontPlanMapper;
    @Resource
    private TFrontPlanFormMapper tFrontPlanFormMapper;
    @Resource
    private TFormMainMapper tFormMainMapper;
    @Resource
    private TShopInfoMapper tShopInfoMapper;
    @Resource
    private TLoopWorkMapper tLoopWorkMapper;
    @Resource
    private TTaskMapper taskMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private JedisCluster jedisCluster;

    public CalendarListMonthOut listCalendarMonth(CalendarListMonthIn calendarListIn, Long currentUserId) {
        CalendarListMonthOut calendarListMonthOut = new CalendarListMonthOut();
        WorkVo_O.Work work = null;
        List<WorkVo_O.Work> workArr = new ArrayList<>();
        calendarListMonthOut.setMonth(calendarListIn.getMonth());
        calendarListMonthOut.setUserId(calendarListIn.getUserId());
        String yearStr = calendarListIn.getMonth().substring(0, 4);
        String monthStr = calendarListIn.getMonth().substring(4, 6);
        Example example = new Example(TFrontPlan.class);
        example.createCriteria().andEqualTo("arrangeeId", calendarListIn.getUserId())
                .andEqualTo("arrangeYear", yearStr).andEqualTo("arrangeMonth", monthStr)
                .andEqualTo("isDel", false);
        example.setOrderByClause("plan_precise_time desc");
        List<TFrontPlan> list = tFrontPlanMapper.selectByExample(example);
        //设置巡店安排
        if (null != list && list.size() > 0) {
            for (TFrontPlan frontPlan : list) {
                work = getFrontPlanWork(frontPlan);
                workArr.add(work);
            }
            //设置 常规任务,指派任务，改善任务
            List<TLoopWork> loopWorkList = tLoopWorkMapper.calendarMonthList(calendarListIn.getUserId(), yearStr, monthStr);
            if (null != loopWorkList && loopWorkList.size() > 0) {
                for (TLoopWork tLoopWork : loopWorkList) {
                    work = getLoopWork(tLoopWork);
                    workArr.add(work);
                }
            }
        }
        calendarListMonthOut.setWorkArr(workArr);
        //设置最近联系人顺序
        setUserOrder(calendarListIn.getUserId(), currentUserId);
        return calendarListMonthOut;
    }

    /**
     * 设置最近联系人顺序
     *
     * @param queryUserId   被查询人id
     * @param currentUserId 当前登录用户id
     */
    private void setUserOrder(Long queryUserId, Long currentUserId) {
        String userStr = jedisCluster.get(ISystem.IUSER.USER_RECENT + currentUserId);
        String[] userIdArr = userStr.split(",");
        if (ArrayUtils.isNotEmpty(userIdArr)) {
            //元素后移
            for (int i = userIdArr.length - 1; i > 0; i--) {
                userIdArr[i] = userIdArr[i - 1];
            }
            //先删除被查看人员
            userIdArr = (String[]) ArrayUtils.removeElement(userIdArr, queryUserId.toString());
        }
        //将被查看人员重新设置到第一位
        userIdArr[0] = queryUserId.toString();
        jedisCluster.set(ISystem.IUSER.USER_RECENT + currentUserId, ArrayUtils.toString(userIdArr));
    }


    public CalendarListDateOut listCalendarDate(CalendarListDateIn calendarListIn) {
        CalendarListDateOut calendarListDateOut = new CalendarListDateOut();
        WorkVo_O.Work work = null;
        List<WorkVo_O.Work> workArr = new ArrayList<>();
        calendarListDateOut.setExecuteDate(calendarListIn.getExecuteDate());
        calendarListDateOut.setUserId(calendarListIn.getUserId());
        Example example = new Example(TFrontPlan.class);
        example.createCriteria().andEqualTo("arrangeeId", calendarListIn.getUserId())
                .andEqualTo("plan_date", calendarListIn.getExecuteDate())
                .andEqualTo("isDel", false);
        example.setOrderByClause("plan_precise_time desc");
        List<TFrontPlan> list = tFrontPlanMapper.selectByExample(example);
        //设置巡店安排
        if (null != list && list.size() > 0) {
            for (TFrontPlan frontPlan : list) {
                work = getFrontPlanWork(frontPlan);
                workArr.add(work);
            }
            //设置 常规任务,指派任务，改善任务
            List<TLoopWork> loopWorkList = tLoopWorkMapper.calendarDateList(calendarListIn.getUserId(), calendarListIn.getExecuteDate());
            if (null != loopWorkList && loopWorkList.size() > 0) {
                for (TLoopWork tLoopWork : loopWorkList) {
                    work = getLoopWork(tLoopWork);
                    workArr.add(work);
                }
            }

        }
        calendarListDateOut.setWorkArr(workArr);
        return calendarListDateOut;
    }

    /**
     * 查询当前用户最近查看用户列表
     * @param currentUserId
     * @return
     */
    public List<SysUser> listLowerUser(Long currentUserId) {
        String userIds = sysUserMapper.findAllChildListByParentId(currentUserId);
        String[] userArr = userIds.split(",");
        List<SysUser> list = null;
        if (null != userArr && userArr.length > 2) {
            list = new ArrayList<>();
            ArrayUtils.remove(userArr, 0);
            ArrayUtils.remove(userArr, 1);
            for (int i = 0; i < userArr.length; i++) {
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.parseLong(userArr[i]));
                list.add(sysUser);
            }
        }
        //返回按姓名首字母排序的集合
        if (null != list && list.size() > 0)
            return orderUserList(list, currentUserId);
        else
            return null;
    }

    public void deleteCalendar(CalendarDelVO calendarDelVO, Long currentUserId) {
        Integer workType = calendarDelVO.getWorkType();
        Long id =Long.parseLong(calendarDelVO.getId());
        if(ConstantsUtil.LoopWork.TYPE_FOUR.equals(workType)){
            TFrontPlan tFrontPlan=new TFrontPlan();
            tFrontPlan.setId(id);
            tFrontPlan.setIsDel(true);
            tFrontPlan.setModifyUserId(currentUserId);
            tFrontPlan.setModifyTime(new Date());
            tFrontPlanMapper.updateByPrimaryKeySelective(tFrontPlan);
        }



    }

    //根据姓名首字母排序
    private List<SysUser> orderUserList(List<SysUser> list, Long currentUserId) {
        HashMap<String, SysUser> map = new HashMap<>();
        String[] nameArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            nameArr[i] = list.get(i).getRealName();
            map.put(nameArr[i], list.get(i));
        }
        Comparator<Object> comparator = Collator.getInstance(java.util.Locale.CHINA);
        Arrays.sort(nameArr, comparator);
        list.clear();
        String [] userArr=new String[list.size()];
        if (!jedisCluster.exists(ISystem.IUSER.USER_RECENT + currentUserId)) {
            for (int n = nameArr.length - 1; n >= 0; n--) {
                if (map.containsKey(nameArr[n])) {
                    userArr[n] = map.get(nameArr[n]).getId().toString();
                }
            }
            jedisCluster.set(ISystem.IUSER.USER_RECENT + currentUserId, ArrayUtils.toString(userArr));
        } else {
            String userStr = jedisCluster.get(ISystem.IUSER.USER_RECENT + currentUserId);
            String[] userIdArr = userStr.split(",");
            for (int n = 0; n < userIdArr.length; n++) {
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.parseLong(userIdArr[n]));
                list.add(sysUser);
            }
        }
        return list;
    }

    private WorkVo_O.Work getFrontPlanWork(TFrontPlan frontPlan) {
        WorkVo_O.Work work = new WorkVo_O.Work();
        work.setId(frontPlan.getId());
        work.setSubID(frontPlan.getSubPlanId().toString());
        work.setWorkID(frontPlan.getLoopPlanId().toString());
        work.setWorkType(ConstantsUtil.LoopWork.TYPE_FOUR);
        WorkVo_O.Work.Inspection inspection = work.getInspection();
        inspection.setInspStartTime(frontPlan.getPlanPreciseTime());
        inspection.setInspStatus(frontPlan.getStatus().intValue());
        //查询表单
        Example exampleForm = new Example(TFrontPlanForm.class);
        exampleForm.createCriteria().andEqualTo("frontPlanId", frontPlan.getId()).andEqualTo("isDel", false);
        List<TFrontPlanForm> formList = tFrontPlanFormMapper.selectByExample(exampleForm);
        if (null != formList && formList.size() > 0) {
            TShopInfo shopInfo = tShopInfoMapper.selectByPrimaryKey(frontPlan.getShopId());
            TFormMain tFormMain = tFormMainMapper.selectByPrimaryKey(formList.get(0).getFormMainId());
            inspection.setInspTitle(shopInfo.getShopName() + tFormMain.getName() + "检查");
        }
        work.setInspection(inspection);
        return work;
    }

    private WorkVo_O.Work getLoopWork(TLoopWork tLoopWork) {
        WorkVo_O.Work work = new WorkVo_O.Work();
        work.setId(tLoopWork.getId());
        work.setWorkID(tLoopWork.getWorkId());
        work.setWorkType(tLoopWork.getType());
        work.setSubID(tLoopWork.getSubWorkId());
        TTask tt = taskMapper.selectByPrimaryKey(tLoopWork.getTaskId());
        work.getTask().setTaskTitle(tt.getName());
        work.getTask().setTaskStatus(tLoopWork.getWorkStatus());
        work.getTask().setTaskResult(tLoopWork.getWorkResult());
        work.getTask().setTaskSourceType(0);
        work.getTask().setTaskSourceName("");
        work.getTask().setTaskDeadline(tLoopWork.getExecuteDeadline());
        SysUser u = sysUserMapper.selectByPrimaryKey(tLoopWork.getExcuteUserId());
        work.getTask().setTaskSubHeadUrl(u.getHeadImgUrl());
        work.getTask().setTaskSubName(u.getRealName());
        return work;
    }

}
