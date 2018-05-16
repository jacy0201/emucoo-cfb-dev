package com.emucoo.restApi.controller.calendar;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.calendar.CalendarListDateIn;
import com.emucoo.dto.modules.calendar.CalendarListDateOut;
import com.emucoo.dto.modules.calendar.CalendarListMonthIn;
import com.emucoo.dto.modules.calendar.CalendarListMonthOut;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.calendar.CalendarService;
import com.emucoo.service.sys.SysUserService;
import com.emucoo.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * @author Jacy
 * 行事历
 */
@RestController
@Api(description="行事历接口")
@RequestMapping("/api/calendar")
public class CalendarController extends AppBaseController {

    @Resource
    private CalendarService calendarService;

    /**
     * 获取指定用户,指定月份行事历
     * @param params
     * @return
     */
    @ApiOperation(value="查询用户某月行事历")
    @PostMapping(value = "/listMonth")
    public AppResult listMonth(@RequestBody ParamVo<CalendarListMonthIn> params) {
        CalendarListMonthIn calendarListIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        if (null==calendarListIn.getUserId()){ calendarListIn.setUserId(user.getId()); }
        if (null==calendarListIn.getMonth()){ calendarListIn.setMonth(DateUtil.simple4(new Date())); }
        CalendarListMonthOut calendarListMonthOut =calendarService.listCalendarMonth(calendarListIn);
        return success(calendarListMonthOut);
    }

    /**
     * 查询用户某天行事历
     * @param params
     * @return
     */
    @ApiOperation(value="查询用户某天行事历")
    @PostMapping(value = "/listDate")
    public AppResult listDate(@RequestBody ParamVo<CalendarListDateIn> params) {
        CalendarListDateIn calendarListDateIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        if (null==calendarListDateIn.getUserId()){ calendarListDateIn.setUserId(user.getId()); }
        if (null==calendarListDateIn.getExecuteDate()) calendarListDateIn.setExecuteDate(DateUtil.getCurrentDate());
        CalendarListDateOut calendarListDateOut =calendarService.listCalendarDate(calendarListDateIn);
        return success(calendarListDateOut);
    }


    /**
     * 查询当前用户的下级用户
     * @return
     */
    @ApiOperation(value="查询当前用户的下级用户")
    @PostMapping(value = "/listLowerUser")
    public AppResult listLowerUser() {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        List<SysUser> list= calendarService.listLowerUser(user.getId());
        return success(list);
    }


}
