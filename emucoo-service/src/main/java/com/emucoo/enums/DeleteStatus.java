package com.emucoo.enums;

/**
 * Created by sj on 2018/4/16.
 */
public enum DeleteStatus {
    COMMON(false,"正常"),

    DELETED(true,"删除");


    private final boolean code;
    private final String msg;


    private DeleteStatus(boolean val, String info) {
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
