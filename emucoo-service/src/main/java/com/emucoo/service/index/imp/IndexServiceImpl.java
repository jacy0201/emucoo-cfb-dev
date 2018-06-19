package com.emucoo.service.index.imp;

import com.alibaba.druid.util.StringUtils;
import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.common.exception.ApiException;
import com.emucoo.common.util.RegexMatcher;
import com.emucoo.dto.base.ITask;
import com.emucoo.dto.modules.index.ReportItemVo;
import com.emucoo.dto.modules.index.ResetPwdVo_I;
import com.emucoo.dto.modules.task.WorkVo_O;
import com.emucoo.dto.modules.user.UserLoginInfo;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.index.IndexService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("AliControlFlowStatementWithoutBraces")
@Transactional
@Service
public class IndexServiceImpl extends BaseServiceImpl<SysUser> implements IndexService {

    private Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private TShopInfoMapper shopInfoMapper;

    @Autowired
    private TReportMapper reportMapper;

    @Autowired
    private TRepairWorkMapper repairWorkMapper;

    @Autowired
    private TTaskMapper taskMapper;

    @Autowired
    private TLoopWorkMapper loopWorkMapper;

    @Autowired
    private TFrontPlanMapper frontPlanMapper;


    @Override
    public UserLoginInfo authenticate(String mobile, String password, String pushToken) {

        SysUser user = null;
        if (RegexMatcher.isPhoneNumber(mobile)) {
            user = userMapper.fetchOneByMobile(mobile);
        } else if (RegexMatcher.isEmail(mobile)) {
            user = userMapper.fetchOneByEmail(mobile);
        } else {
            user = userMapper.fetchOneByUsername(mobile);
        }

        if (user == null) {
            return null;
        }

        if (0 != user.getStatus()) {
            return null;
        }

        if (!StringUtils.equalsIgnoreCase(user.getPassword(), new Sha256Hash(password, user.getSalt()).toHex())) {
            return null;
        }

        //更新用户 push token
        user.setPushToken(pushToken);
        //如果用户没传token 需要设置为null
        userMapper.updateByPrimaryKeySelective(user);

        UserLoginInfo loginInfo = new UserLoginInfo();

	/*	SysDept d = new SysDept();
		d.setId(user.getDptId());*/
        SysDept dept = sysDeptMapper.selectByPrimaryKey(user.getDptId());
        if (dept != null) {
            loginInfo.setUserType("");
            loginInfo.setDepartmentID(dept.getId());
            loginInfo.setDepartmentName(dept.getDptName());
            loginInfo.setDepartmentType(2);
            loginInfo.setUsePlan(dept.getUsePlan());
        }

        List<TShopInfo> shopInfos = shopInfoMapper.fetchShopsOfUser(user.getId());
        List<UserLoginInfo.shop> shoptList = shopInfos.stream().map(shopInfo -> {
            UserLoginInfo.shop shop = new UserLoginInfo.shop();
            shop.setShopName(shopInfo.getShopName());
            shop.setShopID(shopInfo.getId());
            return shop;
        }).collect(Collectors.toList());

        loginInfo.setShopNameArr(shoptList);
        loginInfo.setHeadImgUrl(user.getHeadImgUrl());
        loginInfo.setUserName(user.getRealName());
        loginInfo.setUserId(user.getId());
        loginInfo.setIsShopManager(user.getIsShopManager());
        loginInfo.setIsRepairMan(user.getIsRepairer());

        return loginInfo;
    }

    @Override
    public void resetPwd(ResetPwdVo_I vo, String vcode) {
        // 短信验证
        if (vo.getSmsCode().equals(vcode)) {
            String salt = RandomStringUtils.randomAlphanumeric(20);
            //userMapper.resetPwd(vo.getMobile(), new Sha256Hash(vo.getPassword(),salt).toHex());
            Example example = new Example(SysUser.class);
            example.createCriteria().andEqualTo("mobile", vo.getMobile());
            SysUser sysUser = new SysUser();
            sysUser.setSalt(salt);
            sysUser.setModifyTime(new Date());
            sysUser.setPassword(new Sha256Hash(vo.getPassword(), salt).toHex());
            userMapper.updateByExampleSelective(sysUser, example);
        }
    }

    @Override
    public void loginOut(String pushToken) {
        if (pushToken != null && !"".equals(pushToken)) {
            userMapper.loginOut(pushToken);
        }
    }

    @Override
    public List<ReportItemVo> fetchUnReadReports(long currUserId) {
        try {
            List<ReportItemVo> reportItemVos = new ArrayList<>();
            List<TReport> reports = reportMapper.findReportByUser(currUserId);
            for (TReport report : reports) {
                ReportItemVo reportItemVo = new ReportItemVo();
                reportItemVo.setIsRead(report.getReportUser().getIsRead());
                reportItemVo.setReportTitle(report.getShopName() + "评估表");
                reportItemVo.setReporterName(report.getReporterName());
                if(null!=report.getFormType()) {
                    reportItemVo.setReportType(report.getFormType().intValue());
                }
                reportItemVo.setReportID(report.getId());
                reportItemVo.setReporterHeadUrl(report.getReporterHeadImgUrl());
                reportItemVo.setReportSourceName(report.getReporterDptName());
                reportItemVo.setReportTime(report.getCreateTime().getTime());
                reportItemVos.add(reportItemVo);
            }
            return reportItemVos;
        } catch (Exception e) {
            logger.error("读取报告列表有误！", e);
            throw new ApiException("读取报告列表有误！");
        }

        //return reportMapper.fetchUnReadReport(currUserId);
    }

    @Override
    public Integer reportNum(Long userId, Boolean isRead) {
        return reportMapper.countUnReadReport(userId);
    }

    @Override
    public List<TRepairWork> fetchPendingRepairWorks(Long loginUserId) {
        return repairWorkMapper.filterPendingRepairWorks(loginUserId);
    }

    @Override
    public WorkVo_O filterPendingReviewWorks(Long auditUserId) {
        Date today = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(new Date()));
        List<TLoopWork> loopWorks = loopWorkMapper.listPendingReview(auditUserId, today);
        List<WorkVo_O.Work> works = loopWorks.stream().filter(t -> t.getType() != null).map((TLoopWork t) -> {
            WorkVo_O.Work work = new WorkVo_O.Work();
            work.setId(t.getId());
            work.setWorkID(t.getWorkId());
            work.setSubID(t.getSubWorkId());
            work.setWorkType(t.getType());
            TTask tt = taskMapper.selectByPrimaryKey(t.getTaskId());
            if (t.getType() == ITask.MEMO) {
                work.getMemo().setMemoTitle(tt.getName());
                work.getMemo().setMemoContent(tt.getDescription());
                work.getMemo().setMemoEndTime(t.getExecuteBeginDate().getTime());
                work.getMemo().setMemoStartTime(t.getExecuteEndDate().getTime());
                work.getMemo().setIsSign(t.getIsSign());
                return work;

            } else {
                work.getTask().setTaskTitle(tt.getName());
                work.getTask().setTaskStatus(t.getWorkStatus());
                work.getTask().setTaskResult(t.getWorkResult());
                work.getTask().setTaskSourceType(0);
                work.getTask().setTaskSourceName("");
                work.getTask().setTaskDeadline(t.getAuditDeadline() == null ? 0L : t.getAuditDeadline().getTime());
                SysUser u = userMapper.selectByPrimaryKey(t.getAuditUserId());
                work.getTask().setTaskSubHeadUrl(u.getHeadImgUrl());
                work.getTask().setTaskSubName(u.getRealName());
                return work;
            }
        }).collect(Collectors.toList());
        List<TRepairWork> repairWorks = repairWorkMapper.filterReviewingRepairWorks(auditUserId);
        List<WorkVo_O.Work> rpWorks = repairWorks.stream().map((TRepairWork t) -> {
            WorkVo_O.Work work = new WorkVo_O.Work();
            work.setId(t.getId());
            work.setWorkID(t.getId().toString());
            work.setSubID(t.getId().toString());
            work.setWorkType(7);
            work.getTask().setTaskTitle(t.getShopName() + ":" + t.getDeviceTitle());
            work.getTask().setTaskStatus(t.getWorkStatus());
            work.getTask().setTaskResult(t.getReviewResult());
            work.getTask().setTaskSourceType(0);
            work.getTask().setTaskSourceName("");
            work.getTask().setTaskDeadline(t.getFinishDate().getTime());
            SysUser u = userMapper.selectByPrimaryKey(t.getReporterId());
            work.getTask().setTaskSubHeadUrl(u.getHeadImgUrl());
            work.getTask().setTaskSubName(u.getRealName());
            return work;
        }).collect(Collectors.toList());
        works.addAll(rpWorks);
        WorkVo_O workvoo = new WorkVo_O();
        workvoo.setBackTime(DateUtil.currentDate().getTime());
        workvoo.setWorkArr(works);
        workvoo.setDate(DateUtil.YYYYMMDD.format(new Date()));
        return workvoo;
    }

    @Override
    public int countPendingReviewWorks(Long loginUserId) {
        Date today = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(new Date()));
        int loopwks =  loopWorkMapper.countPendingReviewWorkNum(loginUserId, today);
        int rprwks = repairWorkMapper.countReviewingRepairWorks(loginUserId);
        return rprwks + loopwks;
    }

    @Override
    public WorkVo_O filterPendingExeWorks(Date needDate, Long submitUserId) {
        List<TLoopWork> loopWorks = loopWorkMapper.listPendingExecute(submitUserId, needDate);
        List<TFrontPlan> frontPlans = frontPlanMapper.listTodayPlanOfUser(submitUserId, needDate);
        List<WorkVo_O.Work> inspections = frontPlans.stream().map(frontPlan -> {
            WorkVo_O.Work work = new WorkVo_O.Work();
            work.setId(frontPlan.getId());
            work.setWorkID(Long.toString(frontPlan.getId()));
            work.setWorkType(ITask.INSPECTION);
            work.setSubID("");
            work.getInspection().setInspEndTime(frontPlan.getPlanDate().getTime());
            work.getInspection().setInspStartTime(frontPlan.getPlanDate().getTime());
            work.getInspection().setInspStatus(frontPlan.getStatus() == 1 ? 1 : 0);
            work.getInspection().setInspTitle(frontPlan.getTitle());
            return work;
        }).collect(Collectors.toList());


        List<WorkVo_O.Work> works = loopWorks.stream().filter(t -> t.getType() != null).map((TLoopWork t) -> {
            WorkVo_O.Work work = new WorkVo_O.Work();
            work.setId(t.getId());
            work.setWorkID(t.getWorkId());
            work.setWorkType(t.getType());
            work.setSubID(t.getSubWorkId());
            if (t.getType() == ITask.MEMO) {
                work.getMemo().setMemoStartTime(t.getExecuteBeginDate().getTime());
                work.getMemo().setMemoEndTime(t.getExecuteEndDate().getTime());
                work.getMemo().setMemoTitle(t.getTaskTitle());
                work.getMemo().setMemoContent(t.getDescription());
                work.getMemo().setIsSign(t.getIsSign());
                return work;
            } else {
                work.getTask().setTaskTitle(t.getTaskTitle());
                work.getTask().setTaskStatus(t.getWorkStatus());
                work.getTask().setTaskResult(t.getWorkResult());
                work.getTask().setTaskSourceType(0);
                work.getTask().setTaskSourceName("");
                work.getTask().setTaskDeadline(t.getExecuteDeadline().getTime());
                work.getTask().setTaskSubHeadUrl(t.getAvatar());
                work.getTask().setTaskSubName(t.getUserName());
                return work;
            }
        }).collect(Collectors.toList());
        works.addAll(inspections);
        WorkVo_O workvoo = new WorkVo_O();
        workvoo.setBackTime(DateUtil.currentDate().getTime());
        workvoo.setDate(DateUtil.YYYYMMDD.format(needDate));
        workvoo.setWorkArr(works);
        return workvoo;
    }

    @Override
    public int fetchPendingExeWorkNum(Long loginUserId) {
        Date today = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(new Date()));
        return loopWorkMapper.countPendingExecuteWorkNum(loginUserId, today);
    }

    @Override
    public int countFrontPlan(Long loginUserId) {
        Date today = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(new Date()));
        return frontPlanMapper.countTodayPlanOfUser(loginUserId, today);

    }
}
