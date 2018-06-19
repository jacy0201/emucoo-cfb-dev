package com.emucoo.restApi.controller.index;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.index.*;
import com.emucoo.dto.modules.index.pending.pending_I;
import com.emucoo.dto.modules.task.WorkVo_O;
import com.emucoo.dto.modules.user.UserLoginInfo;
import com.emucoo.dto.modules.user.UserVo_I;
import com.emucoo.model.SysUser;
import com.emucoo.model.TRepairWork;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.models.sms.SMSDao;
import com.emucoo.restApi.models.sms.SMSService;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.index.IndexService;
import com.emucoo.service.sys.DeptService;
import com.emucoo.service.sys.UserService;
import com.emucoo.utils.CheckoutUtil;
import com.emucoo.utils.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/index")
public class IndexController extends AppBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private IndexService indexService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private SMSDao smsDao;

    @Autowired
    private DeptService deptService;


    @PostMapping("/login")
    public AppResult<UserLoginInfo> login(@RequestBody ParamVo<UserVo_I> data) {
        String pushToken = null!=data.getData().getPushToken()?data.getData().getPushToken():null;
        String mobile = data.getData().getMobile();
        String password = data.getData().getPassword();

        checkParam(mobile, "缺少账号");
        checkParam(password, "缺少密码");

        UserLoginInfo loginInfo = indexService.authenticate(mobile, password, pushToken);
        if(loginInfo == null) {
            return AppResult.busErrorRes("用户或密码错误！");
        }

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
        Long submitUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(submitUserId, "没有此用户");
        WorkVo_O works =  indexService.filterPendingExeWorks(needDate, submitUserId);
        return success(works);
    }


    /**
     * 待审核事务列表
     *
     * @return
     */
    @PostMapping("/pendingReview")
    public AppResult<WorkVo_O> pendingReview(@RequestHeader("userToken") String userToken) {
        Long auditUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(auditUserId, "没有此用户");
        WorkVo_O works = indexService.filterPendingReviewWorks(auditUserId);
        return success(works);
    }


    @PostMapping("/pendingWorkNum")
    public AppResult<PendingWorkNumVo_O> pendingWorkNum(@RequestHeader("userToken") String userToken) {
        Long loginUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(loginUserId, "没有此用户。");
        Date today = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(new Date()));
        int inswks = indexService.countFrontPlan(loginUserId);
        int exewks = indexService.fetchPendingExeWorkNum(loginUserId);
        int rvwwks = indexService.countPendingReviewWorks(loginUserId);
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

    @PostMapping("/pendingRepair")
    public AppResult<List<TRepairWork>> pendingRepair(@RequestHeader("userToken") String userToken) {
        Long loginUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(loginUserId, "没有此用户。");
        List<TRepairWork> works = indexService.fetchPendingRepairWorks(loginUserId);
        return success(works);
    }

    @PostMapping("/pendingRepairNum")
    public AppResult<Integer> pendingRepairNum(@RequestHeader("userToken") String userToken) {
        Long loginUserId = UserTokenManager.getInstance().getUserIdFromToken(userToken);
        checkParam(loginUserId, "没有此用户");
        Integer cnt = indexService.countPendingReviewWorks(loginUserId);
        return success(cnt);
    }

}
