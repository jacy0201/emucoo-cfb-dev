package com.emucoo.enums;

/**
 * Created by sj on 2018/4/17.
 */
public enum ShopArrangeStatus {
    NOT_PLAN(0, "未计划"),
    NOT_ARRANGE(1, "未安排"),
    NOT_CHECK(2, "未巡店"),
    CHECKING(3, "巡店中"),
    FINISH_CHECK(4, "已巡店"),
    EXPIRED(5, "已过期")
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
