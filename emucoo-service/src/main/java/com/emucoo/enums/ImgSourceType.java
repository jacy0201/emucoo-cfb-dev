package com.emucoo.enums;

/**
 * Created by sj on 2018/5/23.
 */
public enum ImgSourceType {
    FROM_FRONT(1, "前端创建"),

    FROM_MANAGER(2, "后台创建");

    private final Integer code;
    private final String msg;


    private ImgSourceType(Integer val, String info) {
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
