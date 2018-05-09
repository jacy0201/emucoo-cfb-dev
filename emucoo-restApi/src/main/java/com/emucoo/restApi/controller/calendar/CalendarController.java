package com.emucoo.restApi.controller.calendar;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.plan.*;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.plan.PlanService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author Jacy
 * 行事历
 */
@RestController
@RequestMapping("/api/calendar")
public class CalendarController extends AppBaseController {

    @Resource
    private PlanService planService;

    /**
     * 获取指定用户当月所有的工作计划
     * @param params
     * @param request
     * @return
     */
    @PostMapping(value = "listPlan")
    public AppResult listPlan(@RequestBody ParamVo<FindPlanListIn> params, HttpServletRequest request) {
        FindPlanListIn findPlanListIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        FindPlanListOut findPlanListOut = planService.listPlan(user, findPlanListIn);
        return success(findPlanListOut);
    }

}
