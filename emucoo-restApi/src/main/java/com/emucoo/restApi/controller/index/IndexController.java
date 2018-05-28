package com.emucoo.restApi.controller.index;

import com.emucoo.dto.base.ITask;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.index.*;
import com.emucoo.dto.modules.index.pending.pending_I;
import com.emucoo.dto.modules.task.WorkVo_O;
import com.emucoo.dto.modules.user.UserLoginInfo;
import com.emucoo.dto.modules.user.UserVo_I;
import com.emucoo.model.*;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.models.sms.SMSDao;
import com.emucoo.restApi.models.sms.SMSService;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.index.IndexService;
import com.emucoo.service.shop.LoopPlanService;
import com.emucoo.service.sys.DeptService;
import com.emucoo.service.sys.UserService;
import com.emucoo.service.task.LoopWorkService;
import com.emucoo.utils.CheckoutUtil;
import com.emucoo.utils.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/index")
public class IndexController extends AppBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoopWorkService loopWorkService;

    @Autowired
    private IndexService indexService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private SMSDao smsDao;

    @Autowired
    private DeptService deptService;

    @Autowired
    private LoopPlanService loopPlanService;

    @PostMapping("/login")
    public AppResult<UserLoginInfo> login(@RequestBody ParamVo<UserVo_I> data) {
        String pushToken = data.getData().getPushToken();
        String mobile = data.getData().getMobile();
        String password = data.getData().getPassword();

        checkParam(mobile, "缺少账号");
        checkParam(password, "缺少密码");

        UserLoginInfo loginInfo = indexService.authenticate(mobile, password, pushToken);
        if(loginInfo == null)
            return AppResult.busErrorRes("用户或密码错误！");

        String userToken = UserTokenManager.getInstance().saveUserToken(loginInfo.getUserId());
        loginInfo.setUserToken(userToken);
        return success(loginInfo);
    }

    /**
     * 发送短信验证码
     *
     * @param base
     */
    @PostMapping("/sendSms")
    public AppResult<String> sendSms(@RequestBody ParamVo<SendSmsVo_I> base) {
        SendSmsVo_I vo = base.getData();

        checkParam("mobile", vo.getMobile());
        if (!CheckoutUtil.isMobileNO(vo.getMobile())) {
            // BizException.throwException(9002,"手机号");
            return fail(AppExecStatus.INVALID_PARAM, "手机号不合法！");
        }
        SysUser user = userService.findUserByMobile(vo.getMobile());
        if (user != null) {
            // 生成验证码，保存到redis，发送短信
            smsService.sendResetpwdVcode(vo.getMobile().trim());
            return success("");
        } else {
            return fail(AppExecStatus.BIZ_ERROR, "手机号未注册！");
        }
    }

    /**
     * 密码重置
     *
     * @param base
     */
    @PostMapping("/resetPwd")
    public AppResult<String> resetPwd(@RequestBody ParamVo<ResetPwdVo_I> base) {
        ResetPwdVo_I vo = base.getData();
        // 获取redis中的短信验证码
        String vcode = smsDao.getVcode(vo.getMobile());
        indexService.resetPwd(vo, vcode);
        return success("");
    }

    @PostMapping("/loginOut")
    public AppResult<String> loginOut(@RequestBody ParamVo<LoginOutVo_I> base) {
        LoginOutVo_I vo = base.getData();
        // 清除token
        indexService.loginOut(vo.getPushToken());
        return success("");
    }

    /**
     * 系统时间
     *
     * @return
     */
    @PostMapping("/checkoutTime")
    public AppResult<SystemTimeVo_O> checkoutTime() {
        SystemTimeVo_O systemTime = new SystemTimeVo_O();
        systemTime.setBackTime(DateUtil.currentDate());
        return success(systemTime);
    }

    /**
     * workID 和 task模板的id 是 对应关系
     *
     * @param base
     * @return
     */
    @PostMapping("/pendingExecute")
    public AppResult<WorkVo_O> pendingExecute(@RequestHeader("userToken") String userToken, @RequestBody(required = false) ParamVo<pending_I> base) {

        Date needDate = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(new Date()));
        if (base != null && base.getData() != null && base.getData().getDate() != null) {
            String dateStr = (String) base.getData().getDate();
            Date date = DateUtil.strToSimpleYYMMDDDate(dateStr);
            if (date != null) {
                needDate = date;
            }
        }
        // 后面通过 userToken 获取
        Long submitUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(submitUserId, "没有此用户");
        List<TLoopWork> loopWorks = loopWorkService.listPendingExecute(submitUserId, needDate);
        List<TFrontPlan> frontPlans = loopPlanService.listFrontPlan(submitUserId, needDate);
        List<WorkVo_O.Work> inspections = frontPlans.stream().map(frontPlan -> {
            WorkVo_O.Work work = new WorkVo_O.Work();
            work.setId(frontPlan.getId());
            work.setWorkID(Long.toString(frontPlan.getId()));
            work.setWorkType(ITask.INSPECTION);
            work.setSubID("");
            work.getInspection().setInspEndTime(frontPlan.getPlanDate().getTime());
            work.getInspection().setInspStartTime(frontPlan.getPlanDate().getTime());
            work.getInspection().setInspStatus(frontPlan.getStatus() == 1 ? 1 : 0);
            TLoopPlan loopPlan = loopPlanService.findById(frontPlan.getLoopPlanId());
            if (loopPlan != null) {
                SysDept dept = deptService.findById(loopPlan.getDptId());
                work.getInspection().setInspTitle(dept == null ? dept.getDptName() : "" + "巡检安排");
            }
            return work;
        }).collect(Collectors.toList());

        List<WorkVo_O.Work> works = loopWorks.stream().filter(t -> t.getType() != null).map((TLoopWork t) -> {
            WorkVo_O.Work work = new WorkVo_O.Work();
            work.setId(t.getId());
            work.setWorkID(t.getWorkId());
            work.setWorkType(t.getType());
            work.setSubID(t.getSubWorkId());
            TTask tt = loopWorkService.fetchTaskById(t.getTaskId());

            work.getTask().setTaskTitle(tt.getName());
            work.getTask().setTaskStatus(t.getWorkStatus());
            work.getTask().setTaskResult(t.getWorkResult());
            work.getTask().setTaskSourceType(0);
            work.getTask().setTaskSourceName("");
            work.getTask().setTaskDeadline(t.getExecuteDeadline().getTime());
            SysUser u = userService.findById(t.getExcuteUserId());
            work.getTask().setTaskSubHeadUrl(u.getHeadImgUrl());
            work.getTask().setTaskSubName(u.getRealName());
            return work;
        }).collect(Collectors.toList());
        works.addAll(inspections);
        WorkVo_O workVo_o = new WorkVo_O();
        workVo_o.setBackTime(DateUtil.currentDate().getTime());
        workVo_o.setDate(DateUtil.YYYYMMDD.format(needDate));
        workVo_o.setWorkArr(works);
        return success(workVo_o);
    }

    /**
     * 待审核事务列表
     *
     * @return
     */
    @PostMapping("pendingReview")
    public AppResult<WorkVo_O> pendingReview(@RequestHeader("userToken") String userToken) {
        Long auditUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(auditUserId, "没有此用户");
        List<TLoopWork> loopWorks = loopWorkService.listPendingReview(auditUserId);
        List<WorkVo_O.Work> works = loopWorks.stream().filter(t -> t.getType() != null).map((TLoopWork t) -> {
            WorkVo_O.Work work = new WorkVo_O.Work();
            work.setId(t.getId());
            work.setWorkID(t.getWorkId());
            work.setSubID(t.getSubWorkId());
            work.setWorkType(t.getType());
            TTask tt = loopWorkService.fetchTaskById(t.getTaskId());
            if (ITask.COMMON == t.getType() || ITask.ASSIGN == t.getType() || ITask.IMPROVE == t.getType()) {

                work.getTask().setTaskTitle(tt.getName());
                work.getTask().setTaskStatus(t.getWorkStatus());
                work.getTask().setTaskResult(t.getWorkResult());
//				work.getTask().setTaskSourceType(t.getTaskSourceType());
//				work.getTask().setTaskSourceName(t.getTaskSourceName());
                work.getTask().setTaskDeadline(t.getAuditDeadline() == null ? 0L : t.getAuditDeadline().getTime());
                SysUser u = userService.findById(t.getAuditUserId());
                work.getTask().setTaskSubHeadUrl(u.getHeadImgUrl());
                work.getTask().setTaskSubName(u.getRealName());
                return work;
            }
            if (ITask.INSPECTION == t.getType()) {
                work.getInspection().setInspEndTime(t.getExecuteBeginDate().getTime());
                work.getInspection().setInspStartTime(t.getExecuteEndDate().getTime());
                work.getInspection().setInspStatus(t.getWorkStatus());
                work.getInspection().setInspTitle(tt.getName());
                return work;
            }
            work.getMemo().setImportStatus(t.getWorkStatus());
            work.getMemo().setMemoContent("I don't konw where to get it");
            work.getMemo().setMemoEndTime(t.getExecuteBeginDate().getTime());
            work.getMemo().setMemoStartTime(t.getExecuteEndDate().getTime());
            return work;
        }).collect(Collectors.toList());
        WorkVo_O workVo_o = new WorkVo_O();
        workVo_o.setBackTime(DateUtil.currentDate().getTime());
        workVo_o.setWorkArr(works);
        workVo_o.setDate(DateUtil.YYYYMMDD.format(new Date()));
        return success(workVo_o);

    }

    @PostMapping("/pendingWorkNum")
    public AppResult<PendingWorkNumVo_O> pendingWorkNum(@RequestHeader("userToken") String userToken) {
        Long loginUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(loginUserId, "没有此用户。");
//		long submitUserId = 1L;
        Date today = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(new Date()));
        int inswks = loopPlanService.countFrontPlan(loginUserId, today);
        int exewks = loopWorkService.fetchPendingExecuteWorkNum(loginUserId, today);
        int rvwwks = loopWorkService.fetchPendingReviewWorkNum(loginUserId);
        PendingWorkNumVo_O pendingWorkNumVo = new PendingWorkNumVo_O();
        pendingWorkNumVo.setPendingExecuteNum(exewks + inswks);
        pendingWorkNumVo.setPendingReviewNum(rvwwks);
        return success(pendingWorkNumVo);

    }

    /**
     * 获取未读报告类表数
     *
     * @return
     */
    @PostMapping("/reportNum")
    public AppResult<Map> reportNum(@RequestHeader("userToken") String userToken) {
        // 获取用户 Id from user token
        Long loginUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(loginUserId, "没有此用户");
        Integer unreadRepNum = indexService.reportNum(loginUserId, false);
        Map<String, Object> map = new HashedMap();
        map.put("unreadRepNum", unreadRepNum);
        return success(map);
    }

    @PostMapping("/reportList")
    public AppResult<ReportListVo_O> reportList(@RequestHeader("userToken") String userToken) {
        Long loginUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(loginUserId, "没有此用户。");
        List<ReportItemVo> vos = indexService.fetchUnReadReports(loginUserId);
        ReportListVo_O voo = new ReportListVo_O();
        voo.setUnreadReportArr(vos);
        return success(voo);
    }

}
