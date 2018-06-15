package com.emucoo.restApi.controller.calendar;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.calendar.*;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.calendar.CalendarService;
import com.emucoo.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * @author Jacy
 * 行事历
 */
@RestController
@Api(description = "行事历接口")
@RequestMapping("/api/calendar")
public class CalendarController extends AppBaseController {

    @Autowired
    private CalendarService calendarService;

    /**
     * 获取指定用户,指定月份行事历
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "查询用户某月行事历")
    @PostMapping(value = "/listMonth")
    public AppResult<CalendarListMonthOut> listMonth(@RequestBody ParamVo<CalendarListMonthIn> params) {
        CalendarListMonthIn calendarListIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        Long currentUserId = user.getId();
        if (null == calendarListIn.getUserId()) {
            calendarListIn.setUserId(currentUserId);
        }
        if (null == calendarListIn.getMonth()) {
            calendarListIn.setMonth(DateUtil.simple3(new Date()));
        }
        CalendarListMonthOut calendarListMonthOut = calendarService.listCalendarMonth(calendarListIn, currentUserId);
        return success(calendarListMonthOut);
    }

    /**
     * 查询用户某天行事历
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "查询用户某天行事历")
    @PostMapping(value = "/listDate")
    public AppResult<CalendarListDateOut> listDate(@RequestBody ParamVo<CalendarListDateIn> params) {
        CalendarListDateIn calendarListDateIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        Long currentUserId = user.getId();
        if (null == calendarListDateIn.getUserId()) {
            calendarListDateIn.setUserId(currentUserId);
        }
        if (null == calendarListDateIn.getExecuteDate()) {
            calendarListDateIn.setExecuteDate(DateUtil.getCurrentDate());
        }
        CalendarListDateOut calendarListDateOut = calendarService.listCalendarDate(calendarListDateIn);

        return success(calendarListDateOut);
    }

    /**
     * 查询最近联系人
     *
     * @return
     */
    @ApiOperation(value = "查询最近联系人")
    @PostMapping(value = "/listRecentUser")
    public AppResult<List<SysUser>> listRecentUser() {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        List<SysUser> list = calendarService.listLowerUser(user.getId());
        //返回按姓名首字母排序的集合;
        return success(list);
    }

    /**
     * 删除行事历
     */
    @ApiOperation(value = "删除行事历")
    @PostMapping(value = "/delete")
    public AppResult delete(@RequestBody ParamVo<CalendarDelVO> params) {
        CalendarDelVO calendarDelVO = params.getData();
        checkParam(calendarDelVO.getWorkID(), "workID不能为空!");
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        calendarService.deleteCalendar(calendarDelVO, user.getId());
        //返回按姓名首字母排序的集合;
        return success("success");
    }

}
