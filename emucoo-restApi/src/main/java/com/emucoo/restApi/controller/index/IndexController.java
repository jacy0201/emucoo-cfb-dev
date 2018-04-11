package com.emucoo.restApi.controller.index;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.emucoo.common.util.RegexMatcher;
import com.emucoo.model.*;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.shop.LoopPlanService;
import com.emucoo.service.sys.DeptService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emucoo.dto.base.IDept;
import com.emucoo.dto.base.ITask;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.dept.UserDeptVo;
import com.emucoo.dto.modules.index.LoginOutVo_I;
import com.emucoo.dto.modules.index.PendingWorkNumVo_O;
import com.emucoo.dto.modules.index.ReportItemVo;
import com.emucoo.dto.modules.index.ReportListVo_O;
import com.emucoo.dto.modules.index.ResetPwdVo_I;
import com.emucoo.dto.modules.index.SendSmsVo_I;
import com.emucoo.dto.modules.index.SystemTimeVo_O;
import com.emucoo.dto.modules.index.pending.pending_I;
import com.emucoo.dto.modules.task.WorkVo_O;
import com.emucoo.dto.modules.user.UserLoginInfo;
import com.emucoo.dto.modules.user.UserVo_I;
import com.emucoo.model.ReportItem;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.sms.SMSDao;
import com.emucoo.restApi.models.sms.SMSService;
import com.emucoo.service.index.IndexService;
import com.emucoo.service.sys.DeptRoleUnionService;
import com.emucoo.service.sys.UserService;
import com.emucoo.service.task.LoopWorkService;
import com.emucoo.utils.CheckoutUtil;
import com.emucoo.utils.DateUtil;
import com.google.common.collect.Maps;

@RestController
@RequestMapping("/api/index")
public class IndexController extends AppBaseController {

    @Resource
    private UserService userService;

    @Resource
    private LoopWorkService loopWorkService;

    @Resource
    private IndexService indexService;

    @Resource
    private SMSService smsService;

    @Resource
    private SMSDao smsDao;

    @Resource
    private DeptRoleUnionService deptRoleUnionService;

    @Autowired
    private DeptService deptService;

    @Resource
	private LoopPlanService loopPlanService;

    @PostMapping("/login")
    public AppResult<UserLoginInfo> login(@RequestBody ParamVo<UserVo_I> data) {
        String pushToken = data.getData().getPushToken();
        String mobile = data.getData().getMobile();
        String password = data.getData().getPassword();

        checkParam(mobile,"缺少 mobile");
        checkParam(password,"缺少 password");

        SysUser user = null;
        if(RegexMatcher.isPhoneNumber(mobile)) {
			user = userService.findUserByMobile(mobile);
		}
		if(user == null && RegexMatcher.isEmail(mobile)) {
        	user = userService.findByEmail(mobile);
		}
		if(user == null) {
            user = userService.findByUserName(mobile);
        }
        if(user == null)
            return AppResult.busErrorRes("用户名或者密码错误");

        if(!StringUtils.equalsIgnoreCase(password,user.getPassword())){
           return AppResult.busErrorRes("用户名或者密码错误");
        }
        //登录成功缓存用户 token
        String userToken = UserTokenManager.getInstance().saveUserToken(user.getId());
        //更新用户 push token
        user.setPushToken(pushToken);
        //如果用户没传token 需要设置为null
        userService.update(user);
//        if(!StringUtils.equals(userService.encryPasswor(password),user.getPassword())){
//            return AppResult.busErrorRes("用户名或者密码错误");
//        }

        UserLoginInfo loginInfo = new UserLoginInfo();
        List<UserDeptVo> userDeptVos = deptRoleUnionService.listUserDeptVo(user.getId());
        List<UserDeptVo> deptList = userDeptVos.stream().filter(t->t.getDeptType() !=null && t.getDeptType() == IDept.TYPE_OTHER)
                .collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(deptList)){
            UserDeptVo userDeptVo = deptList.get(0);
            loginInfo.setUserType(userDeptVo.getUserType());
            loginInfo.setDepartmentID(userDeptVo.getDeptId());
            loginInfo.setDepartmentName(userDeptVo.getDeptName());
            //黄文亮:userId =35 - 非店铺
			Integer deptType=userDeptVo.getDeptType();
            if(mobile.equals("13991123160")){
				deptType=2;
			}
            loginInfo.setDepartmentType(deptType);
        }

        List<UserLoginInfo.shop> shoptList = userDeptVos.stream().filter(t->t.getDeptType() !=null && t.getDeptType() == IDept.TYPE_SHOP)
                .map(t->{
                    UserLoginInfo.shop shop = new UserLoginInfo.shop();
                    shop.setShopName(t.getDeptName());
                    shop.setShopID(t.getDeptId());
                    return shop;
                }).collect(Collectors.toList());
        loginInfo.setShopNameArr(shoptList);

        loginInfo.setHeadImgUrl(user.getHeadImgUrl());
        loginInfo.setUserName(user.getRealName());
        loginInfo.setUserToken(userToken);
        loginInfo.setUserId(user.getId());
        AppResult<UserLoginInfo> result = new AppResult<UserLoginInfo>();
        result.setData(loginInfo);
//        return result;
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
        Long submitUserId =  UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(submitUserId, "没有此用户");
		List<TLoopWork> loopWorks = loopWorkService.list(submitUserId, needDate);
		List<TFrontPlan> frontPlans = loopPlanService.listFrontPlan(submitUserId, needDate);
		List<WorkVo_O.Work> inspections = frontPlans.stream().map(frontPlan -> {
		    WorkVo_O.Work work = new WorkVo_O.Work();
		    work.setId(frontPlan.getId());
		    work.setWorkID(Long.toString(frontPlan.getId()));
		    work.setWorkType(ITask.INSPECTION);
		    work.setSubID("");
//            work.getInspection().setInspEndTime(frontPlan);
//            work.getInspection().setInspStartTime(frontPlan.getPlanTime());
            work.getInspection().setInspStartTime(frontPlan.getPlanDate());
            work.getInspection().setInspStatus(frontPlan.getStatus()?1:0);
            TLoopPlan loopPlan = loopPlanService.findById(frontPlan.getLoopPlanId());
            if(loopPlan != null) {
                SysDept dept = deptService.findById(loopPlan.getDptId());
                work.getInspection().setInspTitle(dept == null?dept.getDptName():""+ "巡检安排");
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
//            work.getTask().setTaskSourceType(t.getTaskSourceType());
//            work.getTask().setTaskSourceName(t.getTaskSourceName());
            work.getTask().setTaskDeadline(t.getExecuteDeadline());
            SysUser u = userService.findById(t.getExcuteUserId());
            work.getTask().setTaskSubHeadUrl(u.getHeadImgUrl());
            work.getTask().setTaskSubName(u.getRealName());
            return work;
		}).collect(Collectors.toList());
		works.addAll(inspections);
		WorkVo_O workVo_o = new WorkVo_O();
		workVo_o.setBackTime(DateUtil.currentDate());
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
        Long auditUserId =  UserTokenManager.getInstance().getUserIdFromToken(userToken);
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
				work.getTask().setTaskDeadline(t.getAuditDeadline());
				SysUser u = userService.findById(t.getAuditUserId());
				work.getTask().setTaskSubHeadUrl(u.getHeadImgUrl());
				work.getTask().setTaskSubName(u.getRealName());
				return work;
			}
			if (ITask.INSPECTION == t.getType()) {
				work.getInspection().setInspEndTime(t.getExecuteBeginDate());
				work.getInspection().setInspStartTime(t.getExecuteEndDate());
				work.getInspection().setInspStatus(t.getWorkStatus());
				work.getInspection().setInspTitle(tt.getName());
				return work;
			}
			work.getMemo().setImportStatus(t.getWorkStatus());
			work.getMemo().setMemoContent("I don't konw where to get it");
			work.getMemo().setMemoEndTime(t.getExecuteBeginDate());
			work.getMemo().setMemoStartTime(t.getExecuteEndDate());
			return work;
		}).collect(Collectors.toList());
		WorkVo_O workVo_o = new WorkVo_O();
		workVo_o.setBackTime(DateUtil.currentDate());
		workVo_o.setWorkArr(works);
		workVo_o.setDate(DateUtil.YYYYMMDD.format(new Date()));
		return success(workVo_o);

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
		Map<String, Object> map = Maps.newHashMap();
		map.put("unreadRepNum", unreadRepNum);
		return success(map);
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
		pendingWorkNumVo.setPendingExecuteNum(exewks+inswks);
		pendingWorkNumVo.setPendingReviewNum(rvwwks);
		return success(pendingWorkNumVo);

	}

	@PostMapping("/reportList")
	public AppResult<ReportListVo_O> reportList(@RequestHeader("userToken") String userToken) {
        Long loginUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(loginUserId, "没有此用户。");
        // long submitUserId = user.getId();
		List<ReportItem> reports = indexService.fetchUnReadReports(loginUserId);
		List<ReportItemVo> vos = new ArrayList<ReportItemVo>();
		for (ReportItem it : reports) {
			ReportItemVo vo = new ReportItemVo();
			vo.setRead(it.isRead());
			vo.setReporterHeadUrl(it.getHeadImgUrl());
			vo.setReporterName(vo.getReporterName());
			vo.setReportID(it.getReportId());
			vo.setReportSourceName(it.getReportSourceName());
			vo.setReportSourceType(it.getReportSourceType());
			vo.setReportTime(it.getCreateTime().getTime());
			vo.setReportTitle(it.getTitle());
			vos.add(vo);
		}
		ReportListVo_O voo = new ReportListVo_O();
		voo.setUnreadReportArr(vos);
		return success(voo);
	}

}
