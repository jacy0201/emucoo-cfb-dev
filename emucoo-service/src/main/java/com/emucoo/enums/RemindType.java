package com.emucoo.enums;

/**
 * Created by sj on 2018/4/23.
 */
public enum RemindType {
    SCHEDULE_TIME(1,"日程开始时"),

    AHEAD_FIFTEEN_MINS(2,"提前15分钟"),

    AHEAD_THIRTY_MINS(3,"提前30分钟"),

    AHEAD_ONE_HOUR(4,"提前1小时"),

    AHEAD_TWO_HOURS(5,"提前2小时"),

    AHEAD_ONE_DAY(6,"提前1天")
    ;

    private final Integer code;
    private final String msg;


    private RemindType(Integer val, String info) {
        this.code = val;
        this.msg = info;

    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
