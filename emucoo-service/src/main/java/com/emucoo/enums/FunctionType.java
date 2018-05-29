package com.emucoo.enums;

/**
 * Created by sj on 2018/5/29.
 */
public enum FunctionType {
    CREATE_REMIND(1, "创建提醒"),

    EXECUTE_REMIND(2, "执行提醒"),

    AUDIT_REMIND(3,"审核提醒"),

    TIMEOUT_REMIND(4,"超时提醒"),

    AUDIT_FINISH_REMIND(5, "审核完成提醒"),

    COMMENT_REMIND(6, "评论提醒");

    private final Integer code;
    private final String msg;


    private FunctionType(Integer val, String info) {
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
