package com.emucoo.enums;

/**
 * Created by sj on 2018/4/17.
 */
public enum ShopArrangeStatus {
    NOT_ARRANGE(1, "未安排"),
    NOT_CHECK(2, "未巡店"),
    FINISH_CHECK(3, "已巡店")
    ;


    private final Integer code;
    private final String msg;


    private ShopArrangeStatus(Integer val, String info) {
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
