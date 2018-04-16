package com.emucoo.enums;

/**
 * Created by sj on 2018/4/16.
 */
public enum WorkStatus {
    START_USE(false, "启用"),
    STOP_USE(true, "停用");


    private final boolean code;
    private final String msg;


    private WorkStatus(boolean val, String info) {
        this.code = val;
        this.msg = info;

    }

    public boolean getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
