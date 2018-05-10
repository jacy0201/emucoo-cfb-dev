package com.emucoo.service.calendar;

import com.emucoo.dto.modules.calendar.CalendarListIn;
import com.emucoo.dto.modules.calendar.CalendarListOut;

/**
 * 行事历
 * @author Jacy
 *
 */
public interface CalendarService {

    /**
     * 获取行事历列表
     * @param calendarListIn
     * @return
     */
    CalendarListOut listCalendar(CalendarListIn calendarListIn);

}
