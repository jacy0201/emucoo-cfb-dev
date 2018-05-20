package com.emucoo.service.calendar;

import com.emucoo.dto.modules.calendar.*;
import com.emucoo.model.SysUser;

import java.util.List;

/**
 * 行事历
 * @author Jacy
 *
 */
public interface CalendarService {

    /**
     * 按月份获取用户行事历列表
     * @param calendarListMonthIn
     * @return
     */
    CalendarListMonthOut listCalendarMonth(CalendarListMonthIn calendarListMonthIn,Long currentUserId);

    /**
     * 按日期获取用户行事历列表
     * @param calendarListDateIn
     * @return
     */
    CalendarListDateOut listCalendarDate(CalendarListDateIn calendarListDateIn);

    /**
     * 查询当前用户的下级用户
     * @return
     */
    List<SysUser> listLowerUser(Long currentUserId);


    /**
     * 删除行事历
     * @param id
     * @param currentUserId
     */
    void deleteCalendar(CalendarDelVO calendarDelVO, Long currentUserId);

}
