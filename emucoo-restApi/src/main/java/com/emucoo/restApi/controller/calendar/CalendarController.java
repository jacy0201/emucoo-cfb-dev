package com.emucoo.restApi.controller.calendar;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.calendar.CalendarListIn;
import com.emucoo.dto.modules.calendar.CalendarListOut;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.calendar.CalendarService;
import com.emucoo.service.plan.PlanService;
import com.emucoo.utils.DateUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author Jacy
 * 行事历
 */
@RestController
@RequestMapping("/api/calendar")
public class CalendarController extends AppBaseController {

    @Resource
    private CalendarService calendarService;


    /**
     * 获取指定用户,指定月份行事历
     * @param params
     * @param request
     * @return
     */
    @PostMapping(value = "/listWork")
    public AppResult listWork(@RequestBody ParamVo<CalendarListIn> params, HttpServletRequest request) {
        CalendarListIn calendarListIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        if (null==calendarListIn.getUserId()){ calendarListIn.setUserId(user.getId()); }
        if (null==calendarListIn.getMonth()){ calendarListIn.setMonth(DateUtil.simple4(new Date())); }
        CalendarListOut calendarListOut=calendarService.listCalendar(calendarListIn);
        return success(calendarListOut);
    }




}
