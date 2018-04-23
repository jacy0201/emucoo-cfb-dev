package com.emucoo.utils;

import com.emucoo.enums.RemindType;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sj on 2018/4/23.
 */
public class TimeTaskUtil {

    public static Date calActualRemindTime(Date dueTime, int remindType) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dueTime);
        if(RemindType.SCHEDULE_TIME.getCode().equals(remindType)) {
            return dueTime;
        } else if(RemindType.AHEAD_FIFTEEN_MINS.getCode().equals(remindType)) {
            // 提前15分钟
            cal.add(Calendar.MINUTE, -15);
            return cal.getTime();
        } else if(RemindType.AHEAD_THIRTY_MINS.getCode().equals(remindType)) {
            // 提前30分钟
            cal.add(Calendar.MINUTE, -30);
            return cal.getTime();
        } else if(RemindType.AHEAD_ONE_HOUR.getCode().equals(remindType)) {
            // 提前一小时
            cal.add(Calendar.HOUR_OF_DAY, -1);
            return cal.getTime();
        } else if(RemindType.AHEAD_TWO_HOURS.getCode().equals(remindType)) {
            // 提前两小时
            cal.add(Calendar.HOUR_OF_DAY, -2);
            return cal.getTime();
        } else if(RemindType.AHEAD_ONE_DAY.getCode().equals(remindType)){
            // 提前一天
            cal.add(Calendar.DAY_OF_MONTH, -1);
            return cal.getTime();
        }
        return null;
    }
}
