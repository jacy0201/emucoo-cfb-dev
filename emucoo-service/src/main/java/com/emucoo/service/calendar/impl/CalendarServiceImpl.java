package com.emucoo.service.calendar.impl;

import com.emucoo.dto.base.ISystem;
import com.emucoo.dto.modules.calendar.*;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.calendar.CalendarService;
import com.emucoo.utils.ConstantsUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

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
        CalendarVO calendarVO = null;
        List<CalendarVO> workArr = new ArrayList<>();
        calendarListMonthOut.setMonth(calendarListIn.getMonth());
        calendarListMonthOut.setUserId(calendarListIn.getUserId());
        String yearStr = calendarListIn.getMonth().substring(0, 4);
        String monthStr = calendarListIn.getMonth().substring(4, 6);
        Example example = new Example(TFrontPlan.class);
        //设置每月的天数
        int dayOfMonth=31;
        if("02".equals(monthStr)) dayOfMonth=28;
        else if("04".equals(monthStr)||"06".equals(monthStr)||"09".equals(monthStr)||"11".equals(monthStr)) dayOfMonth=30;
        //循环获取每一天的 工作任务；
        String dt="";
        for (int i=1;i<=dayOfMonth;i++){
            if(i<10) dt="0"+i;
            else dt=i+"";
            calendarVO =new CalendarVO();
            calendarVO.setDate(yearStr+"-"+monthStr+"-"+dt);
            List<CalendarVO.Inspection> inspectionList=null;
            example.clear();
            example.createCriteria().andEqualTo("arrangeeId", calendarListIn.getUserId())
                    .andEqualTo("arrangeYear", yearStr).andEqualTo("arrangeMonth", monthStr)
                    .andEqualTo("isDel", 0).andEqualTo("planDate",calendarVO.getDate());
            example.setOrderByClause("plan_precise_time desc");
            List<TFrontPlan> list = tFrontPlanMapper.selectByExample(example);
            //设置巡店安排
            if (null != list && list.size() > 0) {
                inspectionList= calendarVO.getInspectionList();
                for (TFrontPlan frontPlan : list) {
                    inspectionList.add(getFrontPlanWork(frontPlan));
                }
                calendarVO.setInspectionList(inspectionList);
            }
            workArr.add(calendarVO);
        }

        calendarListMonthOut.setWorkArr(workArr);
        //设置最近联系人顺序
        if(!calendarListIn.getUserId().equals(currentUserId))
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
        if(StringUtil.isNotEmpty(userStr)) {
            String[] idArr = userStr.split(",");
            String[] userIdArr=new String[idArr.length+1];
            for (int n = 0; n < idArr.length; n++) {
                userIdArr[n]=idArr[n];
            }
            //元素后移
           for (int i = userIdArr.length - 1; i > 0; i--) {
                userIdArr[i] = userIdArr[i - 1];
            }
            //先删除被查看人员
            userIdArr = (String[]) ArrayUtils.removeElement(userIdArr, queryUserId.toString());
            //将被查看人员重新设置到第一位
            userIdArr[0] = queryUserId.toString();
            jedisCluster.set(ISystem.IUSER.USER_RECENT + currentUserId, StringUtils.join(userIdArr, ","));
        }

    }


    public CalendarListDateOut listCalendarDate(CalendarListDateIn calendarListIn) {
        CalendarListDateOut calendarListDateOut = new CalendarListDateOut();
        CalendarVO calendarVO = new CalendarVO();
        calendarListDateOut.setExecuteDate(calendarListIn.getExecuteDate());
        calendarListDateOut.setUserId(calendarListIn.getUserId());
        Example example = new Example(TFrontPlan.class);
        example.createCriteria().andEqualTo("arrangeeId", calendarListIn.getUserId())
                .andEqualTo("planDate", calendarListIn.getExecuteDate())
                .andEqualTo("isDel", false);
        example.setOrderByClause("plan_precise_time desc");
        List<TFrontPlan> list = tFrontPlanMapper.selectByExample(example);
        List<CalendarVO.Inspection> inspectionList=null;
        calendarVO.setDate(calendarListIn.getExecuteDate());
        //设置巡店安排
        if (null != list && list.size() > 0) {
            inspectionList = calendarVO.getInspectionList();
            for (TFrontPlan frontPlan : list) {
                inspectionList.add(getFrontPlanWork(frontPlan));
            }
            calendarVO.setInspectionList(inspectionList);
        }
        //设置 常规任务,指派任务，改善任务
        List<TLoopWork> loopWorkList = tLoopWorkMapper.calendarDateList(calendarListIn.getUserId(), calendarListIn.getExecuteDate());
        List<CalendarVO.Task> taskList=null;
        if (null != loopWorkList && loopWorkList.size() > 0) {
            taskList= calendarVO.getTaskList();
            for (TLoopWork tLoopWork : loopWorkList) {
                taskList.add(getLoopWork(tLoopWork));
            }
            calendarVO.setTaskList(taskList);
        }
        calendarListDateOut.setCalendarVO(calendarVO);
        return calendarListDateOut;
    }

    /**
     * 查询当前用户最近查看用户列表
     * @param currentUserId
     * @return
     */
    public List<SysUser> listLowerUser(Long currentUserId) {
        String userIds = sysUserMapper.findAllChildListByParentId(currentUserId);
        List<String> userArr = new ArrayList<>(Arrays.asList(userIds.split(",")));
        List<SysUser> list = null;
        if (null != userArr && userArr.size() > 2) {
            list = new ArrayList<>();
            for (int i = 2; i < userArr.size(); i++) {
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.parseLong(userArr.get(i)));
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
        String [] userArr=new String[list.size()];
        list.clear();
        if (!jedisCluster.exists(ISystem.IUSER.USER_RECENT + currentUserId)) {
            for (int n = nameArr.length - 1; n >= 0; n--) {
                if (map.containsKey(nameArr[n])) {
                    userArr[n] = map.get(nameArr[n]).getId().toString();
                    list.add(map.get(nameArr[n]));
                }
            }
            jedisCluster.set(ISystem.IUSER.USER_RECENT + currentUserId, StringUtils.join(userArr, ","));
        } else {
            String userStr = jedisCluster.get(ISystem.IUSER.USER_RECENT + currentUserId);
            String[] userIdArr = userStr.split(",");
            if(null!=userIdArr && userIdArr.length>0) {
                for (int n = 0; n < userIdArr.length; n++) {
                    SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.parseLong(userIdArr[n]));
                    list.add(sysUser);
                }
            }
        }
        return list;
    }

    private  CalendarVO.Inspection getFrontPlanWork(TFrontPlan frontPlan) {
        CalendarVO.Inspection inspection= new CalendarVO.Inspection();
        inspection.setWorkID(frontPlan.getId().toString());
        inspection.setWorkType(4);
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
        return inspection;
    }

    private CalendarVO.Task getLoopWork(TLoopWork tLoopWork) {
        CalendarVO.Task task=new CalendarVO.Task();
        task.setWorkID(tLoopWork.getWorkId());
        task.setWorkType(tLoopWork.getType());
        task.setSubID(tLoopWork.getSubWorkId());
        TTask tt = taskMapper.selectByPrimaryKey(tLoopWork.getTaskId());
        task.setTaskTitle(tt.getName());
        task.setTaskStatus(tLoopWork.getWorkStatus());
        task.setTaskResult(tLoopWork.getWorkResult());
        task.setTaskDeadline(tLoopWork.getExecuteDeadline());
        SysUser u = sysUserMapper.selectByPrimaryKey(tLoopWork.getExcuteUserId());
        task.setTaskSubHeadUrl(u.getHeadImgUrl());
        task.setTaskSubName(u.getRealName());
        return task;
    }

}
